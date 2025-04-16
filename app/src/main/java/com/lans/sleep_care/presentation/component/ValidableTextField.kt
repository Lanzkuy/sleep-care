package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.lans.instagram_clone.domain.model.InputWrapper
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedSmall

@Composable
fun ValidableTextField(
    modifier: Modifier,
    input: InputWrapper,
    isPassword: Boolean = false,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = RoundedSmall,
    keyboardOptions: KeyboardOptions = remember {
        KeyboardOptions.Default
    },
    onValueChange: (value: String) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(true) }

    OutlinedTextField(
        modifier = modifier
            .padding(bottom = Dimens.dp8),
        value = input.value,
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let {
            {
                Text(
                    text = it,
                    color = if (input.error != null) {
                        MaterialTheme.colorScheme.error
                    } else Color.Unspecified
                )
            }
        },
        supportingText = input.error?.let { { Text(it) } },
        isError = input.error != null,
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = if (trailingIcon == null && isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = if (passwordVisible) {
                            stringResource(R.string.hide_password)
                        } else stringResource(
                            R.string.show_password
                        )
                    )
                }
            }
        } else trailingIcon,
        shape = shape,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (passwordVisible && isPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        onValueChange = onValueChange
    )
}