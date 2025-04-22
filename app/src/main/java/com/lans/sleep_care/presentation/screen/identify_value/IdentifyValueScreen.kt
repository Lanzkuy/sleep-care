package com.lans.sleep_care.presentation.screen.identify_value

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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.lans.sleep_care.domain.model.ValueArea
import com.lans.sleep_care.presentation.component.ElevatedIconButton
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White

@Composable
fun IdentifyValueScreen(
    viewModel: IdentifyValueViewModel = hiltViewModel(),
    navigateToMyTherapy: () -> Unit
) {
    val areas = listOf(
        "Keluarga", "Pernikahan/Relasi Romantis", "Pertemanan", "Pekerjaan",
        "Pendidikan & Pengembangan Diri", "Rekreasi", "Spiritualitas",
        "Komunitas", "Lingkungan", "Kesehatan Tubuh"
    )
    val localSavedValueArea = DATA.savedValueArea
    val tempValueArea = remember { mutableStateMapOf<String, ValueArea>() }

    LaunchedEffect(Unit) {
        tempValueArea.putAll(localSavedValueArea)
    }

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
                    text = stringResource(R.string.identify_value),
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
                    .fillMaxWidth()
            ) {
                items(areas) { area ->
                    val currentData = DATA.savedValueArea[area] ?: ValueArea()
                    ValueAreaItem(
                        areaName = area,
                        valueArea = currentData,
                        onDataChange = { valueArea ->
                            tempValueArea[area] = valueArea
                        }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(Dimens.dp16)
                    )
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
                viewModel.saveValueArea(tempValueArea)
                tempValueArea.clear()
            }
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = stringResource(R.string.icon)
            )
        }
    }
}

@Composable
fun ValueAreaItem(
    areaName: String,
    valueArea: ValueArea,
    onDataChange: (valueArea: ValueArea) -> Unit
) {
    var priority by remember { mutableFloatStateOf(valueArea.priority) }
    var match by remember { mutableFloatStateOf(valueArea.match) }
    var desire by remember { mutableStateOf(valueArea.desire) }

    LaunchedEffect(priority, match, desire) {
        onDataChange(ValueArea(priority, match, desire))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Gray),
        elevation = CardDefaults.cardElevation(Dimens.dp6),
        shape = RoundedLarge
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.dp16)
        ) {
            Text(
                text = areaName,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                modifier = Modifier.height(Dimens.dp8)
            )
            Text(
                text = stringResource(R.string.priority_scale, priority.toInt())
            )
            Slider(
                value = priority,
                onValueChange = {
                    priority = it
                },
                valueRange = 1f..10f,
                steps = 8
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp8)
            )
            Text(
                text = stringResource(R.string.match_scale, match.toInt())
            )
            Slider(
                value = match,
                valueRange = 1f..10f,
                steps = 8,
                onValueChange = {
                    match = it
                }
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp8)
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(R.string.area_desire)
                    )
                },
                value = desire,
                onValueChange = {
                    desire = it
                }
            )
        }
    }
}