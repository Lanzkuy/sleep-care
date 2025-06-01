package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.lans.sleep_care.R
import com.lans.sleep_care.domain.model.logbook.LogbookQuestionAnswer
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.presentation.theme.White
import com.lans.sleep_care.utils.toList

@Composable
fun ThoughtRecordItem(
    answers: List<LogbookQuestionAnswer>,
    onClick: () -> Unit
) {
    val date = answers[0].answer.answer
    val time = answers[1].answer.answer
    val situation = answers[2].answer.answer
    val thoughts = answers[3].answer.answer.toList()

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
        border = BorderStroke(
            width = Dimens.dp1,
            color = Gray
        ),
        colors = CardDefaults.cardColors(containerColor = White),
        shape = RoundedLarge
    ) {
        Column(
            modifier = Modifier
                .padding(Dimens.dp16)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.dp8),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Primary, shape = Rounded)
                        .size(Dimens.dp24),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Dimens.dp12),
                        imageVector = Icons.Default.CalendarToday,
                        tint = White,
                        contentDescription = stringResource(R.string.date)
                    )
                }
                Text(
                    text = "$date $time",
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp12)
            )
            Text(
                text = situation,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp16)
            )
            Text(
                text = "${stringResource(R.string.appear_thought)} :",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Black,
                    fontWeight = FontWeight.Medium
                )
            )
            Spacer(
                modifier = Modifier
                    .height(Dimens.dp8)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
            ) {
                thoughts.forEach { answer ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Secondary,
                        shape = RoundedLarge,
                        tonalElevation = Dimens.dp2
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    horizontal = Dimens.dp12,
                                    vertical = Dimens.dp8
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(Dimens.dp16),
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                tint = Black,
                                contentDescription = null,
                            )
                            Spacer(
                                modifier = Modifier
                                    .width(Dimens.dp6)
                            )
                            Text(
                                text = answer,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Black
                            )
                        }
                    }
                }
            }
        }
    }
}