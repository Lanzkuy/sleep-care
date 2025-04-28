package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.domain.model.EmotionRecord
import com.lans.sleep_care.presentation.theme.Dimens

@Composable
fun EmotionRecordDialog(
    onDismiss: () -> Unit,
    onSave: (EmotionRecord) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var selectedEmotion by remember { mutableStateOf("Senang") }
    var intensity by remember { mutableFloatStateOf(5f) }
    var situation by remember { mutableStateOf("") }
    var thoughts by remember { mutableStateOf("") }
    var coping by remember { mutableStateOf("") }
    var emotionAfter by remember { mutableStateOf("Netral") }
    var intensityAfter by remember { mutableFloatStateOf(5f) }
    var impactAfter by remember { mutableStateOf("") }

    val emotionOptions = listOf("Senang", "Sedih", "Marah", "Takut", "Cemas", "Netral")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Catatan Emosi") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Tanggal & Waktu") },
                    placeholder = { Text(text = "Contoh: 2025-04-07 08:00") },
                    value = date,
                    onValueChange = { date = it }
                )
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp12)
                )
                Text(
                    text = "Emosi yang Dirasakan",
                    fontWeight = FontWeight.SemiBold
                )
                EmotionDropdown(
                    selected = selectedEmotion,
                    onSelect = { selectedEmotion = it },
                    options = emotionOptions
                )
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.dp8),
                    text = "Intensitas: ${intensity.toInt()}",
                )
                Slider(
                    value = intensity,
                    valueRange = 0f..10f,
                    onValueChange = { intensity = it }
                )
                OutlinedTextField(
                    value = situation,
                    onValueChange = { situation = it },
                    label = { Text(text = "Apa yang terjadi?") },
                    placeholder = { Text(text = "Situasi yang memicu emosi") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = thoughts,
                    onValueChange = { thoughts = it },
                    label = { Text(text = "Apa yang terlintas di pikiran?") },
                    placeholder = { Text(text = "Isi pikiran saat merasakan emosi ini") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = coping,
                    onValueChange = { coping = it },
                    label = { Text(text = "Apa yang kamu lakukan?") },
                    placeholder = { Text(text = "Cara kamu mengelola emosi") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp12)
                )
                Text(
                    text = "Setelah Melakukan Tindakan",
                    fontWeight = FontWeight.SemiBold
                )
                EmotionDropdown(
                    selected = emotionAfter,
                    onSelect = { emotionAfter = it },
                    options = emotionOptions
                )
                Text(
                    text = "Intensitas Setelahnya: ${intensityAfter.toInt()}",
                    modifier = Modifier.padding(top = Dimens.dp8)
                )
                Slider(
                    value = intensityAfter,
                    valueRange = 0f..10f,
                    onValueChange = { intensityAfter = it }
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Dampak setelahnya") },
                    placeholder = { Text(text = "Apa efeknya setelah kamu mengelola emosi?") },
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
                Text(text = "Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Batal")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmotionDropdown(
    selected: String,
    onSelect: (String) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            label = { Text(text = "Pilih Emosi") },
            value = selected,
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            onValueChange = {}
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    }
                )
            }
        }
    }
}