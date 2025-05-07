package com.lans.sleep_care.presentation.screen.thought_record

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.data.DATA
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.ThoughtRecordDialog
import com.lans.sleep_care.presentation.component.items.ThoughtRecordItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun ThoughtRecordScreen(
    viewModel: ThoughtRecordViewModel = hiltViewModel(),
    navigateToMyTherapy: () -> Unit
) {
    val localSavedThoughtRecord = DATA.savedThoughtRecord.toMutableList()
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(Dimens.dp24)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.dp12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ElevatedIconButton(
                    modifier = Modifier
                        .size(Dimens.dp50),
                    color = White,
                    tint = Black,
                    icon = Icons.AutoMirrored.Default.ArrowBack,
                    shape = Rounded,
                    onClick = {
                        navigateToMyTherapy.invoke()
                    }
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = stringResource(R.string.thought_record),
                    textAlign = TextAlign.Center,
                    fontSize = Dimens.sp24,
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier
                        .size(Dimens.dp50)
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp16)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimens.dp16)
            ) {
                items(localSavedThoughtRecord) { record ->
                    ThoughtRecordItem(record = record)
                }
            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            containerColor = Primary,
            contentColor = White,
            shape = Rounded,
            onClick = {
                showDialog = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.icon)
            )
        }
        if (showDialog) {
            ThoughtRecordDialog (
                onDismiss = { showDialog = false },
                onSave = { newRecord ->
                    viewModel.saveThoughtRecord(newRecord)
                    showDialog = false
                }
            )
        }
    }
}
