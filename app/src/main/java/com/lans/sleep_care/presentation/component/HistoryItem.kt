package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.White

@Composable
fun HistoryItem(
    name: String,
    date: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(Dimens.dp1, DarkGray),
        colors = CardDefaults.outlinedCardColors(
            containerColor = White
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.dp16),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = Dimens.sp18,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp8)
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Text(
                text = "${stringResource(R.string.detail)} >>",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}