package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White

@Composable
fun NoteDialog(
    title: String,
    note: String,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = Black.copy(alpha = 0.5f)
                )
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = White,
                shape = RoundedLarge,
                shadowElevation = Dimens.dp8
            ) {
                Column(
                    modifier = Modifier
                        .width(Dimens.dp300)
                        .padding(Dimens.dp16)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Dimens.dp12),
                        contentAlignment = Alignment
                            .Center
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontSize = Dimens.sp18
                            )
                        )
                        IconButton(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .border(
                                    width = Dimens.dp1,
                                    color = DarkGray,
                                    shape = Rounded
                                )
                                .size(Dimens.dp24),
                            onClick = onClose
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(Dimens.dp20),
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.icon)
                            )
                        }
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.dp8)
                    )
                    Box(
                        modifier = Modifier
                            .border(
                                width = Dimens.dp1,
                                color = DarkGray,
                                shape = RoundedLarge
                            )
                            .fillMaxWidth()
                            .padding(Dimens.dp16)
                    ) {
                        Text(
                            text = note,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.dp16)
                    )
                }
            }
        }
    }
}