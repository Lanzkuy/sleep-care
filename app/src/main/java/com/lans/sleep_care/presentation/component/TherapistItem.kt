package com.lans.sleep_care.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SuggestionChip
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
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun TherapistItem(
    image: String? = null,
    name: String,
    experience: Int = 1,
    like: Int = 0,
    onClick: () -> Unit,
    onMessageClick: () -> Unit
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
                .fillMaxSize()
                .padding(Dimens.dp16),
            horizontalArrangement = Arrangement.spacedBy(Dimens.dp8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(Dimens.dp56)
                    .padding()
                    .border(
                        width = Dimens.dp1,
                        color = DarkGray,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                model = image,
                placeholder = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person),
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(Dimens.dp4)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Row {
                    SuggestionChip(
                        modifier = Modifier
                            .height(Dimens.dp32),
                        onClick = { },
                        label = {
                            Text(
                                text = stringResource(R.string.year, experience),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(Dimens.dp16),
                                painter = painterResource(R.drawable.ic_work),
                                contentDescription = stringResource(R.string.icon)
                            )
                        }
                    )
                    SuggestionChip(
                        modifier = Modifier
                            .height(Dimens.dp32)
                            .padding(horizontal = Dimens.dp4),
                        onClick = { },
                        label = {
                            Text(
                                text = stringResource(R.string.percent, like),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(Dimens.dp16),
                                painter = painterResource(R.drawable.ic_like),
                                contentDescription = stringResource(R.string.icon)
                            )
                        }
                    )
                }
            }
            ElevatedIconButton(
                modifier = Modifier
                    .size(Dimens.dp40),
                color = White,
                tint = DarkGray,
                icon = painterResource(R.drawable.ic_message),
                shape = Rounded,
                onClick = onMessageClick
            )
        }
    }
}