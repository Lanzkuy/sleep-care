package com.lans.sleep_care.presentation.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.EmotionRecord
import com.lans.sleep_care.presentation.component.form.GenericDropDown
import com.lans.sleep_care.presentation.theme.Dimens

@Composable
fun EmotionRecordDialog(
    onDismiss: () -> Unit,
    onSave: (EmotionRecord) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var selectedEmotion by remember { mutableStateOf("Senang") }
    var intensity by remember { mutableFloatStateOf(0f) }
    var situation by remember { mutableStateOf("") }
    var thoughts by remember { mutableStateOf("") }
    var coping by remember { mutableStateOf("") }
    var emotionAfter by remember { mutableStateOf("Netral") }
    var intensityAfter by remember { mutableFloatStateOf(0f) }
    var impactAfter by remember { mutableStateOf("") }

    val emotionOptions = listOf("Senang", "Sedih", "Marah", "Takut", "Cemas", "Netral")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.add_emotion_record)) },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.date)) },
                    value = date,
                    onValueChange = { date = it }
                )
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp12)
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = Dimens.dp4),
                    text = stringResource(R.string.emotion_felt),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                GenericDropDown(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(R.string.choose_emotion),
                    selected = selectedEmotion,
                    onSelect = { selectedEmotion = it },
                    options = emotionOptions
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.dp8),
                    text = "${stringResource(R.string.intensity)}: $intensity",
                    style = MaterialTheme.typography.bodyLarge
                )
                Slider(
                    value = intensity,
                    valueRange = 0f..10f,
                    steps = 8,
                    onValueChange = { intensity = it }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.what_happened)) },
                    value = situation,
                    onValueChange = { situation = it }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.what_comes_to_mind)) },
                    value = thoughts,
                    onValueChange = { thoughts = it }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.what_do_you_do)) },
                    value = coping,
                    onValueChange = { coping = it }
                )
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp12)
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = Dimens.dp4),
                    text = stringResource(R.string.after_action),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                GenericDropDown(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = stringResource(R.string.choose_emotion),
                    selected = emotionAfter,
                    onSelect = { emotionAfter = it },
                    options = emotionOptions
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.dp8),
                    text = "${stringResource(R.string.intensity_after)}: $intensityAfter",
                    style = MaterialTheme.typography.bodyLarge
                )
                Slider(
                    value = intensityAfter,
                    valueRange = 0f..10f,
                    steps = 8,
                    onValueChange = { intensityAfter = it }
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(R.string.impact_after)) },
                    placeholder = { Text(text = stringResource(R.string.effect_after)) },
                    value = impactAfter,
                    onValueChange = { impactAfter = it }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    EmotionRecord(
                        date = date,
                        emotion = selectedEmotion,
                        intensity = intensity.toInt(),
                        situation = situation,
                        thoughts = thoughts,
                        copingStrategy = coping,
                        emotionAfter = emotionAfter,
                        intensityAfter = intensityAfter.toInt()
                    )
                )
                onDismiss()
            }) {
                Text(text = stringResource(R.string.save))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}