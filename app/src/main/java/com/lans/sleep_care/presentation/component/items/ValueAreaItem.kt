package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.ValueArea
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.White

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

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(
            width = Dimens.dp1,
            color = Gray
        ),
        colors = CardDefaults.outlinedCardColors(containerColor = White),
        shape = RoundedLarge
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.dp16)
        ) {
            Text(
                text = areaName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp12)
            )
            Text(
                text = "${stringResource(R.string.priority_scale)}: $priority",
                style = MaterialTheme.typography.bodyLarge,
            )
            Slider(
                value = priority,
                valueRange = 1f..10f,
                steps = 8,
                onValueChange = {
                    priority = it
                }
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp8)
            )
            Text(
                text = "${stringResource(R.string.match_scale)}: $match",
                style = MaterialTheme.typography.bodyLarge,
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
                        text = stringResource(R.string.area_desire),
                        style = MaterialTheme.typography.bodyLarge,
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