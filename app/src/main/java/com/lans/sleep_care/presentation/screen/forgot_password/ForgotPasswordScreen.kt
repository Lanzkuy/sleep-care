package com.lans.sleep_care.presentation.screen.forgot_password

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.form.ValidableTextField
import com.lans.sleep_care.presentation.component.form.ValidableTextFieldWithButton
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.Typography
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }
    var resendCountdown by remember { mutableIntStateOf(300) }
    var canResend by remember { mutableStateOf(false) }

    LaunchedEffect(
        key1 = state.forgotPasswordResponse,
        key2 = state.resetPasswordResponse,
        key3 = state.error
    ) {
        val forgotPasswordResponse = state.forgotPasswordResponse
        val resetPasswordResponse = state.resetPasswordResponse
        val error = state.error

        if (forgotPasswordResponse) {
            Toast.makeText(context, "Kode verifikasi berhasil dikirim", Toast.LENGTH_SHORT).show()
            state.forgotPasswordResponse = false
        }

        if (resetPasswordResponse) {
            Toast.makeText(context, "Password berhasil direset", Toast.LENGTH_SHORT).show()
            navigateToLogin.invoke()
        }

        if (error.isNotBlank()) {
            showAlert = Pair(true, error)
            state.error = ""
        }
    }

    LaunchedEffect(state.isCountdown) {
        resendCountdown = 300
        canResend = false
        while (resendCountdown > 0) {
            delay(1000)
            resendCountdown--
        }
        canResend = true
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
            text = stringResource(R.string.app_name),
            fontWeight = FontWeight.Bold,
            style = Typography.headlineLarge
        )
        Text(
            text = stringResource(R.string.forgot_password_description),
            style = Typography.bodyLarge
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp24)
        )
        if (state.currentPage == 0) {
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                input = state.email,
                label = stringResource(R.string.email),
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordUIEvent.EmailChanged(it))
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp8)
            )
            LoadingButton(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.send).uppercase(),
                shape = Rounded,
                isLoading = state.isForgotPasswordLoading,
                onClick = {
                    viewModel.onEvent(ForgotPasswordUIEvent.ForgotPasswordButtonClicked)
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
                    navigateToLogin.invoke()
                }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = stringResource(R.string.back).uppercase()
                )
            }
        } else if (state.currentPage == 1) {
            ValidableTextFieldWithButton(
                modifier = Modifier
                    .padding(top = Dimens.dp8),
                input = state.verificationCode,
                placeholder = stringResource(R.string.verification_code),
                buttonText = if (canResend) {
                    stringResource(R.string.send)
                } else {
                    val minutes = resendCountdown / 60
                    val seconds = resendCountdown % 60
                    String.format(Locale.ROOT, "%02d:%02d", minutes, seconds)
                },
                isLoading = state.isForgotPasswordLoading,
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordUIEvent.VerificationCodeChanged(it))
                },
                onSendClick = {
                    if (canResend) {
                        viewModel.onEvent(ForgotPasswordUIEvent.ForgotPasswordButtonClicked)
                    }
                }
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                input = state.newPassword,
                label = stringResource(R.string.new_password),
                isPassword = true,
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordUIEvent.NewPasswordChanged(it))
                }
            )
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                input = state.newPasswordConfirmation,
                label = stringResource(R.string.new_password_confirmation),
                isPassword = true,
                onValueChange = {
                    viewModel.onEvent(ForgotPasswordUIEvent.NewPasswordConfirmationChanged(it))
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
                text = stringResource(R.string.change_password),
                shape = Rounded,
                isLoading = state.isResetPasswordLoading,
                onClick = {
                    viewModel.onEvent(ForgotPasswordUIEvent.ChangePasswordButtonClicked)
                }
            )
            Spacer(modifier = Modifier.height(Dimens.dp8))
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
                    viewModel.onEvent(ForgotPasswordUIEvent.BackButtonClicked)
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
}




