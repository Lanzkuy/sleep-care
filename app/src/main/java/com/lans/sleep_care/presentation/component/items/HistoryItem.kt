package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.presentation.theme.Success
import com.lans.sleep_care.presentation.theme.White
import com.lans.sleep_care.utils.formatToRupiah

@Composable
fun HistoryItem(
    name: String,
    price: Int,
    date: String,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(
            width = Dimens.dp1,
            color = Gray
        ),
        colors = CardDefaults.outlinedCardColors(
            containerColor = White
        )
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = Dimens.sp18
                        )
                    )
                    Text(
                        text = formatToRupiah(price),
                        color = Success,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp8)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Dimens.dp24)
                            .padding(end = Dimens.dp8),
                        imageVector = Icons.Default.CalendarToday,
                        tint = DarkGray,
                        contentDescription = stringResource(R.string.icon)
                    )
                    Text(
                        text = date,
                        color = DarkGray,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = Dimens.dp16),
                    color = Gray
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.dp36),
                    shape = RoundedLarge,
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor = Secondary,
                            contentColor = Primary
                        ),
                    onClick = onClick
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(Dimens.dp24)
                                .padding(end = Dimens.dp8),
                            imageVector = Icons.Default.RemoveRedEye,
                            tint = Primary,
                            contentDescription = stringResource(R.string.icon)
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterVertically),
                            text = stringResource(R.string.view_detail)
                        )
                    }
                }
            }
        }
    }
}