package com.lans.sleep_care.presentation.screen.login

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.LoadingButton
import com.lans.sleep_care.presentation.component.ValidableTextField
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.Typography
import com.lans.sleep_care.presentation.theme.White

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToRegister: () -> Unit,
    navigateToForgotPassword: () -> Unit,
    navigateToHome: () -> Unit
) {
    val state by viewModel.state

    LaunchedEffect(key1 = state.loginResponse, key2 = state.error) {
        val response = state.loginResponse
        val error = state.error

        if (response != null) {
            navigateToHome.invoke()
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
            .padding(horizontal = Dimens.dp24),
        horizontalAlignment = Alignment.CenterHorizontally
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
                viewModel.onEvent(LoginUIEvent.EmailChanged(it))
            }
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.password,
            label = stringResource(R.string.password),
            onValueChange = {
                viewModel.onEvent(LoginUIEvent.PasswordChanged(it))
            }
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        navigateToForgotPassword.invoke()
                    },
                text = stringResource(R.string.forgot_password),
                style = Typography.labelLarge
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp16)
        )
        LoadingButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp48),
            text = stringResource(R.string.login),
            shape = Rounded,
            isLoading = state.isLoading,
            onClick = {
                navigateToHome.invoke()
//                viewModel.onEvent(LoginUIEvent.LoginButtonClicked)
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp8)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.dont_have_an_account),
                style = Typography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .width(Dimens.dp4)
            )
            Text(
                modifier = Modifier
                    .clickable {
                        navigateToRegister.invoke()
                    },
                text = stringResource(R.string.register),
                style = Typography.labelLarge,
            )
        }
    }
}




