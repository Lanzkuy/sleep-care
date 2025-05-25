package com.lans.sleep_care.presentation.component.form

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.lans.sleep_care.domain.model.validation.InputWrapper
import com.lans.sleep_care.presentation.component.button.LoadingButton
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary

@Composable
fun ValidableTextFieldWithButton(
    modifier: Modifier,
    input: InputWrapper,
    placeholder: String,
    buttonText: String,
    isLoading: Boolean,
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
            placeholder = placeholder,
            shape = RoundedCornerShape(
                topStart = Dimens.dp4,
                bottomStart = Dimens.dp4
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = onValueChange
        )
        LoadingButton(
            modifier = Modifier
                .height(Dimens.dp56)
                .weight(0.2f),
            text = buttonText,
            shape = RoundedCornerShape(
                topEnd = Dimens.dp4,
                bottomEnd = Dimens.dp4
            ),
            containerColor = Primary,
            contentPadding = PaddingValues(Dimens.dp0),
            isLoading = isLoading,
            onClick = onSendClick
        )
    }
}
