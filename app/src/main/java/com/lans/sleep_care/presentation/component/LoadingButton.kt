package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.lans.sleep_care.presentation.theme.Dimens

@Composable
fun LoadingButton(
    modifier: Modifier,
    text: String,
    shape: Shape,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .height(Dimens.dp48),
        shape = shape,
        colors = ButtonDefaults
            .buttonColors(
                containerColor = containerColor,
                contentColor = contentColor
            ),
        contentPadding = contentPadding,
        onClick = onClick,
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(Dimens.dp24),
                color = Color.White
            )
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = text.uppercase()
            )
        }
    }
}