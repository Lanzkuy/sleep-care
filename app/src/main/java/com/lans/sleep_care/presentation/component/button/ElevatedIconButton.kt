package com.lans.sleep_care.presentation.component.button

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.Dimens

@Composable
fun ElevatedIconButton(
    modifier: Modifier,
    color: Color,
    tint: Color,
    icon: ImageVector,
    shape: Shape,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = shape,
        shadowElevation = Dimens.dp16
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.dp16)
                .clip(CircleShape)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimens.dp24),
                    color = Color.White
                )
            } else {
                Icon(
                    imageVector = icon,
                    tint = tint,
                    contentDescription = stringResource(R.string.icon),
                )
            }
        }
    }
}

@Composable
fun ElevatedIconButton(
    modifier: Modifier,
    color: Color,
    tint: Color,
    icon: Painter,
    shape: Shape,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = shape,
        shadowElevation = Dimens.dp16
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.dp16)
                .clip(CircleShape)
                .clickable { onClick.invoke() },
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimens.dp24),
                    color = Color.White
                )
            } else {
                Icon(
                    painter = icon,
                    tint = tint,
                    contentDescription = stringResource(R.string.icon),
                )
            }
        }
    }
}