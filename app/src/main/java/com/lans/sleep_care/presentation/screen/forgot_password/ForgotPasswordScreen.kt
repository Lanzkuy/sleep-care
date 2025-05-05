package com.lans.sleep_care.presentation.screen.forgot_password

import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.LoadingButton
import com.lans.sleep_care.presentation.component.ValidableTextField
import com.lans.sleep_care.presentation.component.ValidableTextFieldWithButton
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.Typography
import com.lans.sleep_care.presentation.theme.White

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit
) {
    val state by viewModel.state

    LaunchedEffect(key1 = state.forgotPasswordResponse, key2 = state.error) {
        val response = state.forgotPasswordResponse
        val error = state.error

        if (response) {
            // Do something
        }

        if (error.isNotBlank()) {
            state.error = ""
        }
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
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.email,
            label = stringResource(R.string.email),
            onValueChange = {
                viewModel.onEvent(ForgotPasswordUIEvent.EmailChanged(it))
            }
        )
        ValidableTextFieldWithButton(
            modifier = Modifier
                .padding(top = Dimens.dp8),
            input = state.verificationCode,
            onValueChange = {
                viewModel.onEvent(ForgotPasswordUIEvent.VerificationCodeChanged(it))
            },
            onSendClick = {
                viewModel.onEvent(ForgotPasswordUIEvent.ChangePasswordButtonClicked)
            }
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.newPassword,
            label = stringResource(R.string.new_password),
            onValueChange = {
                viewModel.onEvent(ForgotPasswordUIEvent.NewPasswordChanged(it))
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
            text = stringResource(R.string.ubah_password),
            shape = Rounded,
            isLoading = state.isLoading,
            onClick = {
                viewModel.onEvent(ForgotPasswordUIEvent.ChangePasswordButtonClicked)
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
    }
}




