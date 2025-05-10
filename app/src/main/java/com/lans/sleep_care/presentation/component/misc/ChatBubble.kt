package com.lans.sleep_care.presentation.component.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.PrimaryVariant
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.presentation.theme.SecondaryVariant
import com.lans.sleep_care.presentation.theme.White

@Composable
fun ChatBubble(
    message: String,
    isUser: Boolean
) {
    val arrangement = if (isUser) Arrangement.End else Arrangement.Start
    val bubbleColor = if (isUser) Primary else Secondary
    val textColor = if (isUser) White else Black
    val shape = if (isUser) {
        RoundedCornerShape(
            topStart = Dimens.dp50,
            bottomStart = Dimens.dp50,
            topEnd = Dimens.dp24
        )
    } else {
        RoundedCornerShape(
            topStart = Dimens.dp24,
            topEnd = Dimens.dp50,
            bottomEnd = Dimens.dp50
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = Dimens.dp8,
                vertical = Dimens.dp4
            ),
        horizontalArrangement = arrangement
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = bubbleColor,
                    shape = shape
                )
                .padding(
                    start = if (isUser) Dimens.dp16 else Dimens.dp20,
                    top = Dimens.dp12,
                    end = if (isUser) Dimens.dp16 else Dimens.dp16,
                    bottom = Dimens.dp12
                )
                .widthIn(max = Dimens.dp260)
        ) {
            Text(
                text = message,
                color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}