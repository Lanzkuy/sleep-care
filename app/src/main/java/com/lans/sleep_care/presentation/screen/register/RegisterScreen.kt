package com.lans.sleep_care.presentation.screen.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.LoadingButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.form.GenericDropDown
import com.lans.sleep_care.presentation.component.form.ValidableTextField
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.Typography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToVerification: (email: String) -> Unit
) {
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.loadProblems()
    }

    LaunchedEffect(key1 = state.isRegistered, key2 = state.error) {
        val response = state.isRegistered
        val error = state.error

        if (response) {
            navigateToVerification.invoke(state.email.value)
            viewModel.resetResult()
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
            text = stringResource(R.string.register_description),
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
                input = state.passwordConfirmation,
                label = stringResource(R.string.confirm_password),
                isPassword = true,
                onValueChange = {
                    viewModel.onEvent(RegisterUIEvent.PasswordConfirmationChanged(it))
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
                    color = Primary,
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
                onSelect = {
                    viewModel.onEvent(RegisterUIEvent.GenderSelected(it))
                },
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
            Text(
                modifier = Modifier
                    .padding(bottom = Dimens.dp4),
                text = stringResource(R.string.problem),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.dp6)
            ) {
                state.availableProblems.forEach { option ->
                    val isSelected = option in state.problems

                    FilterChip(
                        label = {
                            Text(
                                text = option,
                                fontSize = Dimens.sp16
                            )
                        },
                        selected = isSelected,
                        leadingIcon = if (isSelected) {
                            {
                                Icon(
                                    modifier = Modifier.size(Dimens.dp18),
                                    imageVector = Icons.Default.Check,
                                    contentDescription = stringResource(R.string.icon)
                                )
                            }
                        } else null,
                        border = BorderStroke(
                            width = Dimens.dp1,
                            color = DarkGray
                        ),
                        onClick = {
                            viewModel.onEvent(RegisterUIEvent.ToggleProblem(option))
                        }
                    )
                }
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