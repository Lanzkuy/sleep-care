package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.RoundedSmall

@Composable
fun NumberDropDown(
    isExpanded: Boolean,
    selectedPosition: Int,
    numbers: List<Int>,
    onExpandToggle: () -> Unit,
    onNumberSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .border(
                width = Dimens.dp1,
                color = DarkGray,
                shape = RoundedSmall
            )
            .padding(
                horizontal = Dimens.dp6,
                vertical = Dimens.dp2
            )
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onExpandToggle.invoke()
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = numbers[selectedPosition].toString())
            Image(
                painter = painterResource(id = R.drawable.ic_dropdown),
                contentDescription = stringResource(R.string.icon)
            )
        }
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                onExpandToggle.invoke()
            }) {
            numbers.forEachIndexed { index, number ->
                DropdownMenuItem(text = {
                    Text(text = number.toString())
                },
                    onClick = {
                        onNumberSelected.invoke(index)
                        onExpandToggle.invoke()
                    })
            }
        }
    }
}