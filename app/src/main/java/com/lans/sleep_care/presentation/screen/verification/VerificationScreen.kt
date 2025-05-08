package com.lans.sleep_care.presentation.screen.verification

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.LoadingButton
import com.lans.sleep_care.presentation.component.form.ValidableTextField
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.form.ValidableTextFieldWithButton
import com.lans.sleep_care.presentation.screen.forgot_password.ForgotPasswordUIEvent
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.Typography
import com.lans.sleep_care.presentation.theme.White

@Composable
fun VerificationScreen(
    viewModel: VerificationViewModel = hiltViewModel(),
    email: String,
    navigateBack: () -> Unit,
    navigateNext: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.requestOtp(email)
    }

    LaunchedEffect(
        key1 = state.requestOtpResponse,
        key2 = state.verificationResponse,
        key3 = state.error
    ) {
        val requestOtpResponse = state.requestOtpResponse
        val verificationResponse = state.verificationResponse
        val error = state.error

        if (requestOtpResponse) {
            Toast.makeText(context, "Kode OTP berhasil dikirim", Toast.LENGTH_SHORT).show()
            state.requestOtpResponse = false
        }

        if (verificationResponse) {
            Toast.makeText(context, "Kode OTP berhasil diverifikasi", Toast.LENGTH_SHORT).show()
            navigateNext.invoke()
        }

        if (error.isNotBlank()) {
            showAlert = Pair(true, error)
            state.error = ""
        }
    }

    if (showAlert.first) {
        ValidationAlert(
            title = stringResource(R.string.alert_error_title),
            message = showAlert.second,
            onDismiss = {
                showAlert = showAlert.copy(first = false)
            }
        )
    }

    Column(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(horizontal = Dimens.dp24)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.1f)
        )
        Text(
            text = stringResource(R.string.verification),
            fontWeight = FontWeight.Bold,
            style = Typography.headlineLarge
        )
        Text(
            text = "${stringResource(R.string.verification_description)} $email",
            style = Typography.bodyLarge
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp16)
        )
        ValidableTextFieldWithButton(
            modifier = Modifier
                .padding(top = Dimens.dp8),
            input = state.otpCode,
            onValueChange = {
                viewModel.onEvent(VerificationUIEvent.VerificationCodeChanged(it))
            },
            onSendClick = {
                viewModel.onEvent(VerificationUIEvent.SendVerificationCodeButtonClicked(email))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp16)
        )
        LoadingButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(R.string.confirm),
            shape = Rounded,
            isLoading = state.isLoading,
            onClick = {
                viewModel.onEvent(VerificationUIEvent.ConfirmButtonClicked(email))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp8)
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp48),
            shape = Rounded,
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Primary
                ),
            onClick = {
                navigateBack.invoke()
            }
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.back).uppercase()
            )
        }
    }
}