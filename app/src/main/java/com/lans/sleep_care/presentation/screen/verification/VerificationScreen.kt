package com.lans.sleep_care.presentation.screen.verification

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
    val state by viewModel.state

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
        ValidableTextField(
            modifier = Modifier
                .fillMaxWidth(),
            input = state.verificationCode,
            label = stringResource(R.string.verification_code),
            onValueChange = {
                viewModel.onEvent(VerificationUIEvent.VerificationCodeChanged(it))
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
                text = stringResource(R.string.not_received_code),
                style = Typography.bodyMedium
            )
            Spacer(
                modifier = Modifier
                    .width(Dimens.dp4)
            )
            Text(
                modifier = Modifier
                    .clickable {

                    },
                text = stringResource(R.string.resend),
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
                .fillMaxWidth(),
            text = stringResource(R.string.confirm),
            shape = Rounded,
            isLoading = state.isLoading,
            onClick = {
                viewModel.onEvent(VerificationUIEvent.ConfirmButtonClicked)
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