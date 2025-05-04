package com.lans.sleep_care.presentation.screen.register

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.GenericDropDown
import com.lans.sleep_care.presentation.component.LoadingButton
import com.lans.sleep_care.presentation.component.ValidableTextField
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.Typography
import com.lans.sleep_care.presentation.theme.White

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state

    LaunchedEffect(key1 = state.isRegistered, key2 = state.error) {
        val response = state.isRegistered
        val error = state.error

        if (response) {
            Toast.makeText(context, "Account created! Check your email for the verification code", Toast.LENGTH_LONG).show()
            navigateToHome.invoke()
        }

        if (error.isNotBlank()) {
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
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
        if (state.currentPage == 0) {
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
                input = state.email,
                label = stringResource(R.string.email),
                onValueChange = {
                    viewModel.onEvent(RegisterUIEvent.EmailChanged(it))
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
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp48),
                shape = Rounded,
                onClick = {
                    viewModel.onEvent(RegisterUIEvent.NextButtonClicked)
                }
            ) {
                Text(
                    text = stringResource(R.string.next).uppercase(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
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
        } else if (state.currentPage == 1) {
            ValidableTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                input = state.age,
                label = stringResource(R.string.age),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                onValueChange = {
                    viewModel.onEvent(RegisterUIEvent.AgeChanged(it))
                }
            )
            GenericDropDown(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(R.string.gender),
                selected = state.gender,
                onSelect = { state.gender = it },
                options = listOf(
                    stringResource(R.string.man),
                    stringResource(R.string.woman)
                )
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp8)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.dp8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GenericDropDown(
                    modifier = Modifier
                        .weight(1f),
                    label = stringResource(R.string.gender),
                    selected = state.problem,
                    onSelect = { viewModel.onEvent(RegisterUIEvent.ProblemChange(it)) },
                    options = listOf(
                        "Stres", "Adiksi", "Depresi", "Trauma"
                    )
                )
                IconButton(
                    modifier = Modifier
                        .size(Dimens.dp32),
                    onClick = {
                        viewModel.onEvent(RegisterUIEvent.AddProblemButtonClicked)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(R.string.icon)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp8)
            )
            state.problems.forEach {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "• $it",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium
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
                text = stringResource(R.string.register),
                shape = Rounded,
                isLoading = state.isLoading,
                onClick = {
                    viewModel.onEvent(RegisterUIEvent.RegisterButtonClicked)
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
                    viewModel.onEvent(RegisterUIEvent.PreviousButtonClicked)
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