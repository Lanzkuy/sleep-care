package com.lans.sleep_care.presentation.screen.payment

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.lans.sleep_care.common.Constant.MIDTRANS_CLIENT_KEY
import com.lans.sleep_care.common.Constant.MIDTRANS_LANGUAGE
import com.lans.sleep_care.common.Constant.MIDTRANS_MERCHANT_BASE_URL
import com.lans.sleep_care.domain.model.Psychologist
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.utils.splitName
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ItemDetails
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentActivity : ComponentActivity(), TransactionFinishedCallback {
    private val viewModel: PaymentViewModel by viewModels()
    private lateinit var orderId: String

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

        val psychologistId = intent.getIntExtra("psychologistId", 1)
        orderId = "ORDER-${System.currentTimeMillis()}"

        viewModel.loadPsychologist(psychologistId)
        viewModel.getUser()

        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                if (state.error.isNotEmpty()) {
                    Toast.makeText(this@PaymentActivity, "Error: ${state.error}", Toast.LENGTH_LONG)
                        .show()
                }

                val user = state.user
                val psychologist = state.psychologist

                if (user != null && psychologist != null && !isTransactionStarted) {
                    isTransactionStarted = true
                    startTransaction(user, psychologist)
                }

                if (state.paymentStatus.isNotEmpty()) {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Status: ${state.paymentStatus}",
                        Toast.LENGTH_LONG
                    ).show()
                    finish()
                }
            }
        }
    }

    private fun startTransaction(user: User, psychologist: Psychologist) {
        val (firstName, lastName) = splitName(user.name)
        val price = 350000.0

        val transactionRequest = TransactionRequest(orderId, price).apply {
            customerDetails = CustomerDetails().apply {
                customerIdentifier = user.email
                this.firstName = firstName
                this.lastName = lastName
                this.email = user.email
            }

            itemDetails = arrayListOf(
                ItemDetails(
                    "ITEM-${psychologist.id}",
                    price,
                    1,
                    "THERAPY-${psychologist.id}-${psychologist.userId}"
                )
            )
        }

        MidtransSDK.getInstance().transactionRequest = transactionRequest
        MidtransSDK.getInstance().startPaymentUiFlow(this)
    }

    override fun onTransactionFinished(result: TransactionResult) {
        if (result.response != null) {
            viewModel.startPollingTransaction(orderId)
        } else if (result.isTransactionCanceled) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show()
            finish()
        } else {
            if (result.status.equals(TransactionResult.STATUS_INVALID, true)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show()
            }
            finish()
        }
    }
}