package com.lans.sleep_care.presentation.screen.profile

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.button.LoadingButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.form.GenericDropDown
import com.lans.sleep_care.presentation.component.form.ValidableTextField
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    id: String,
    name: String,
    age: String,
    gender: String,
    problemList: List<String>,
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.initializeData(
            id = id.toInt(),
            name = name,
            age = age,
            gender = gender,
            problems = problemList,
            availableProblems = listOf(
                context.getString(R.string.stress),
                context.getString(R.string.adiction),
                context.getString(R.string.depresion),
                context.getString(R.string.trauma)
            )
        )
    }

    LaunchedEffect(key1 = state.isProfileUpdated, key2 = state.error) {
        val response = state.isProfileUpdated
        val error = state.error

        if (response) {
            Toast.makeText(context, "Profil berhasil diperbaharui", Toast.LENGTH_SHORT).show()
            navigateToHome.invoke()
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimens.dp24),
            horizontalArrangement = Arrangement.spacedBy(Dimens.dp12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ElevatedIconButton(
                modifier = Modifier
                    .size(Dimens.dp50),
                color = White,
                tint = Black,
                icon = Icons.AutoMirrored.Default.ArrowBack,
                shape = Rounded,
                onClick = {
                    navigateToHome.invoke()
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.profile),
                textAlign = TextAlign.Center,
                fontSize = Dimens.sp24,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .size(Dimens.dp50)
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp16)
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.name,
            label = stringResource(R.string.name),
            onValueChange = {
                viewModel.onEvent(ProfileUIEvent.NameChanged(it))
            }
        )
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.age,
            label = stringResource(R.string.age),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            onValueChange = {
                viewModel.onEvent(ProfileUIEvent.AgeChanged(it))
            }
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
                        viewModel.onEvent(ProfileUIEvent.ToggleProblem(option))
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
            text = stringResource(R.string.change_profile),
            shape = Rounded,
            isLoading = state.isLoading,
            onClick = {
                viewModel.onEvent(ProfileUIEvent.SaveButtonClicked)
            }
        )
    }
}