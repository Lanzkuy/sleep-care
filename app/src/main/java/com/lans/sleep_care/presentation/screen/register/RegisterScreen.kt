package com.lans.sleep_care.presentation.screen.register

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
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val state by viewModel.state

    LaunchedEffect(key1 = state.registerResponse, key2 = state.error) {
        val response = state.registerResponse
        val error = state.error

        if (response) {
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
                viewModel.onEvent(RegisterUIEvent.EmailChanged(it))
            }
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.name,
            label = stringResource(R.string.name),
            onValueChange = {
                viewModel.onEvent(RegisterUIEvent.NameChanged(it))
            }
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.password,
            label = stringResource(R.string.password),
            isPassword = true,
            onValueChange = {
                viewModel.onEvent(RegisterUIEvent.PasswordChanged(it))
            }
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.confirmPassword,
            label = stringResource(R.string.confirm_password),
            isPassword = true,
            onValueChange = {
                viewModel.onEvent(RegisterUIEvent.ConfirmPasswordChanged(it))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp8)
        )
        LoadingButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp48),
            text = stringResource(R.string.register),
            shape = Rounded,
            isLoading = state.isLoading,
            onClick = {
                viewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
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
                text = stringResource(R.string.have_an_account_already),
                style = Typography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .width(Dimens.dp4)
            )
            Text(
                modifier = Modifier
                    .clickable {
                        navigateToLogin.invoke()
                    },
                text = stringResource(R.string.login),
                style = Typography.labelLarge
            )
        }
    }
}