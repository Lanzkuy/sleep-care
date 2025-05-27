package com.lans.sleep_care.presentation.screen.payment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lans.sleep_care.common.Constant.MIDTRANS_CLIENT_KEY
import com.lans.sleep_care.common.Constant.MIDTRANS_LANGUAGE
import com.lans.sleep_care.common.Constant.MIDTRANS_MERCHANT_BASE_URL
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentActivity : ComponentActivity(), TransactionFinishedCallback {
    private val viewModel: PaymentViewModel by viewModels()
    private var isTransactionStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SdkUIFlowBuilder.init()
            .setContext(this)
            .setTransactionFinishedCallback(this)
            .setClientKey(MIDTRANS_CLIENT_KEY)
            .setMerchantBaseUrl(MIDTRANS_MERCHANT_BASE_URL)
            .setLanguage(MIDTRANS_LANGUAGE)
            .enableLog(true)
            .buildSDK()

        val orderId = intent.getStringExtra("orderId")
        val token = intent.getStringExtra("token")

        if (!token.isNullOrEmpty() && !orderId.isNullOrEmpty() && !isTransactionStarted) {
            isTransactionStarted = true
            startTransaction(orderId, token)
        }

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                if (state.error.isNotEmpty()) {
                    Toast.makeText(this@PaymentActivity, "Error: ${state.error}", Toast.LENGTH_LONG)
                        .show()
                }

                if (state.paymentStatus == "settlement") {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Pembayaran berhasil",
                        Toast.LENGTH_LONG
                    ).show()

                    setResult(RESULT_OK)
                    finish()
                }
            }
        }
    }

    override fun onTransactionFinished(result: TransactionResult) {
        if (result.isTransactionCanceled) {
            Toast.makeText(this, "Pembayaran dibatalkan", Toast.LENGTH_LONG).show()

            viewModel.stopPollingTransaction()
            finish()
        }

        result.response?.let {
            when (result.status) {
                TransactionResult.STATUS_INVALID -> {
                    Toast.makeText(this, "Pembayaran tidak valid", Toast.LENGTH_LONG).show()
                }

                TransactionResult.STATUS_PENDING -> {
                    if (viewModel.state.value.paymentStatus != "settlement") {
                        Toast.makeText(
                            this,
                            "Pembayaran belum selesai. Silakan lanjutkan pembayaran",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                TransactionResult.STATUS_SUCCESS -> {
                    Toast.makeText(this, "Pembayaran berhasil2", Toast.LENGTH_LONG).show()
                    setResult(RESULT_OK)
                }

                TransactionResult.STATUS_FAILED -> {
                    Toast.makeText(
                        this,
                        "Terjadi kesalahan saat memproses pembayaran",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            viewModel.stopPollingTransaction()
            finish()
        }
    }

    private fun startTransaction(orderId: String, token: String) {
        MidtransSDK.getInstance().startPaymentUiFlow(this, token)
        viewModel.startPollingTransaction(orderId)
    }
}