package com.lans.sleep_care.presentation.component.misc

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.lans.sleep_care.domain.model.therapy.Therapy
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.MediumGray
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Success
import com.lans.sleep_care.presentation.theme.Warning
import com.lans.sleep_care.utils.formatToRupiah

@Composable
fun HistoryTherapyDetail(
    modifier: Modifier,
    period: String,
    totalPrice: String,
    therapy: Therapy,
    psychologist: Psychologist,
    onPostClick: (Int, String) -> Unit
) {
    val isRated = therapy.comment.isNotEmpty()
    var rating by remember { mutableIntStateOf(therapy.rating) }
    var comment by remember { mutableStateOf(therapy.comment) }

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
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(Dimens.dp48)
                    .clip(CircleShape),
                model = psychologist.user.avatar,
                placeholder = painterResource(R.drawable.img_user_placeholder),
                error = painterResource(R.drawable.img_user_placeholder),
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(start = Dimens.dp8),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(start = Dimens.dp6),
                    text = psychologist.user.name,
                    color = DarkGray,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = Dimens.sp18,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(Dimens.dp4))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { index ->
                        Icon(
                            modifier = Modifier
                                .size(Dimens.dp24)
                                .clickable {
                                    if (!isRated) {
                                        rating = index
                                    }
                                },
                            imageVector = Icons.Default.Star,
                            tint = if (index <= rating) Warning else Gray,
                            contentDescription = stringResource(R.string.icon),
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .height(Dimens.dp16)
        )
        Text(
            text = stringResource(R.string.your_comment),
            color = MediumGray,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Spacer(
            modifier = Modifier
                .height(Dimens.dp4)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = comment,
            placeholder = {
                Text(text = "Add a comment about your therapy experience...")
            },
            readOnly = isRated,
            shape = RoundedLarge,
            onValueChange = {
                comment = it
            },
        )
        Spacer(
            modifier = Modifier
                .height(Dimens.dp8)
        )
        if (!isRated) {
            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = {
                    onPostClick.invoke(rating, comment)
                }
            ) {
                Text(text = "Post")
            }
        }
        Spacer(
            modifier = Modifier
                .height(Dimens.dp24)
        )
    }
}