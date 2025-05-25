package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.CommittedAction
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedLarge

@Composable
fun CommittedActionDialog(
    onDismiss: () -> Unit,
    onSave: (CommittedAction) -> Unit
) {
    var area by remember { mutableStateOf("") }
    var goal by remember { mutableStateOf("") }
    var plan by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var obstacle by remember { mutableStateOf("") }
    var solution by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.add_commited_action))
        },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.area)) },
                    value = area,
                    onValueChange = { area = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.goal)) },
                    value = goal,
                    onValueChange = { goal = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.plan)) },
                    value = plan,
                    onValueChange = { plan = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.time)) },
                    value = time,
                    onValueChange = { time = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.status)) },
                    value = status,
                    onValueChange = { status = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.obstacle)) },
                    value = obstacle,
                    onValueChange = { obstacle = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.how_to_solve)) },
                    value = solution,
                    onValueChange = { solution = it }
                )
            }
        },
        confirmButton = {
            Button(
                shape = RoundedLarge,
                onClick = {
                    if (area.isNotBlank() && goal.isNotBlank() && plan.isNotBlank()) {
                        onSave(
                            CommittedAction(
                                area = area,
                                goal = goal,
                                plan = plan,
                                time = time,
                                status = status,
                                obstacle = obstacle,
                                solution = solution
                            )
                        )
                    }
                }
            ) {
                Text(text = stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}