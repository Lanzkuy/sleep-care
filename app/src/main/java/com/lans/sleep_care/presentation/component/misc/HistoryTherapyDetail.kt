package com.lans.sleep_care.presentation.component.misc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.therapy.Psychologist
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.MediumGray
import com.lans.sleep_care.presentation.theme.Success
import com.lans.sleep_care.presentation.theme.Warning
import com.lans.sleep_care.utils.formatToRupiah

@Composable
fun HistoryTherapyDetail(
    modifier: Modifier,
    period: String,
    totalPrice: String,
    psychologist: Psychologist
) {
    Column(
        modifier = modifier
    ) {
        Spacer(
            modifier = Modifier
                .height(Dimens.dp16)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.therapy_period),
                color = MediumGray,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                text = stringResource(R.string.total_price),
                color = MediumGray,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = period,
                color = DarkGray,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = formatToRupiah(totalPrice.toInt()),
                color = Success,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(
            modifier = Modifier
                .height(Dimens.dp24)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(Dimens.dp48)
                    .padding()
                    .clip(CircleShape),
                model = psychologist.user.avatar,
                placeholder = painterResource(R.drawable.img_user_placeholder),
                error = painterResource(R.drawable.img_user_placeholder),
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .padding(start = Dimens.dp8),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = Dimens.dp6),
                    text = psychologist.user.name,
                    color = DarkGray,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = Dimens.sp18,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(
                    modifier = Modifier
                        .height(Dimens.dp4)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Dimens.dp24),
                        imageVector = Icons.Default.Star,
                        tint = Warning,
                        contentDescription = stringResource(R.string.icon),
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = Dimens.dp4),
                        text = psychologist.avgRating.toString(),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(Dimens.dp24)
        )
    }
}