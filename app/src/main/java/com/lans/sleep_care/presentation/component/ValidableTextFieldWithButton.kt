package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.White

@Composable
fun ValidableTextFieldWithButton(
    modifier: Modifier,
    input: InputWrapper,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        ValidableTextField(
            modifier = Modifier
                .weight(0.8f),
            input = input,
            placeholder = stringResource(R.string.verification_code),
            shape = RoundedCornerShape(
                topStart = Dimens.dp4,
                bottomStart = Dimens.dp4
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChange
        )
        Button(
            modifier = Modifier
                .height(Dimens.dp56)
                .weight(0.2f),
            colors = ButtonDefaults.buttonColors(containerColor = Primary),
            contentPadding = PaddingValues(Dimens.dp0),
            shape = RoundedCornerShape(
                topEnd = Dimens.dp4,
                bottomEnd = Dimens.dp4
            ),
            onClick = onSendClick,
        ) {
            Text(
                text = stringResource(R.string.send).uppercase(),
                color = White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
