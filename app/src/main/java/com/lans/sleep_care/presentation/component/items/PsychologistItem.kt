package com.lans.sleep_care.presentation.component.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
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
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Warning
import com.lans.sleep_care.presentation.theme.White

@Composable
fun PsychologistItem(
    image: String? = null,
    name: String,
    rating: Double = 0.0,
    totalReview: Int = 0,
    onClick: () -> Unit
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(Dimens.dp1, Gray),
        colors = CardDefaults.outlinedCardColors(
            containerColor = White
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.dp16),
            horizontalArrangement = Arrangement.spacedBy(Dimens.dp12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(Dimens.dp48)
                    .padding()
                    .clip(CircleShape),
                model = image,
                placeholder = painterResource(R.drawable.img_user_placeholder),
                error = painterResource(R.drawable.img_user_placeholder),
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .height(Dimens.dp48)
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = Dimens.sp18,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.dp4),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(Dimens.dp24),
                        painter = painterResource(R.drawable.ic_star_filled),
                        tint = Warning,
                        contentDescription = stringResource(R.string.icon)
                    )
                    Text(
                        text = rating.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = Dimens.sp18,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
//                    Text(
//                        text = "($totalReview)",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
                }
            }
        }
    }
}