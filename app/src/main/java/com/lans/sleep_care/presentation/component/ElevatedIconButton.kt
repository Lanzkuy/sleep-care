package com.lans.sleep_care.presentation.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = shape,
        shadowElevation = Dimens.dp16
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                tint = tint,
                contentDescription = stringResource(R.string.icon),
            )
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
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = color,
        shape = shape,
        shadowElevation = Dimens.dp16
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = icon,
                tint = tint,
                contentDescription = stringResource(R.string.icon),
            )
        }
    }
}