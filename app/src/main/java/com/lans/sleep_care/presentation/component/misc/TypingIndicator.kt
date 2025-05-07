package com.lans.sleep_care.presentation.component.misc

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.presentation.theme.White

@Composable
fun TypingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "typing-dots")

    val dot1Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(0)
        ),
        label = "dot1"
    )

    val dot2Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(150)
        ),
        label = "dot2"
    )

    val dot3Alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(300)
        ),
        label = "dot3"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.dp8, vertical = Dimens.dp4),
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = Secondary,
                    shape = RoundedCornerShape(
                        topStart = Dimens.dp24,
                        topEnd = Dimens.dp50,
                        bottomEnd = Dimens.dp50
                    )
                )
                .padding(
                    start = Dimens.dp20,
                    top = Dimens.dp12,
                    end = Dimens.dp16,
                    bottom = Dimens.dp12
                )
                .widthIn(max = Dimens.dp260),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf(dot1Alpha, dot2Alpha, dot3Alpha).forEachIndexed { index, alpha ->
                Box(
                    modifier = Modifier
                        .background(
                            color = White.copy(alpha = alpha),
                            shape = Rounded
                        )
                        .size(Dimens.dp8)
                )
                if (index < 2) Spacer(modifier = Modifier.width(Dimens.dp6))
            }
        }
    }
}