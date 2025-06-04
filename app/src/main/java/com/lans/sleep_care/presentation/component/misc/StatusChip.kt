package com.lans.sleep_care.presentation.component.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedLarge

@Composable
fun StatusChip(
    modifier: Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Unspecified,
    color: Color
) {
    Box(
        modifier = modifier
            .background(
                color = color.copy(alpha = 0.15f),
                shape = RoundedLarge
            )
            .padding(
                horizontal = Dimens.dp4,
                vertical = Dimens.dp2
            )
    ) {
        Text(
            modifier = textModifier,
            text = text,
            textAlign = textAlign,
            color = color,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}