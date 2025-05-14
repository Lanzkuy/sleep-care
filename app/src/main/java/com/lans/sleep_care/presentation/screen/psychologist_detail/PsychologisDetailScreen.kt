package com.lans.sleep_care.presentation.screen.psychologist_detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White
import java.util.Calendar

@Composable
fun PsychologistDetailScreen(
    viewModel: PsychologistDetailViewModel = hiltViewModel(),
    id: Int,
    navigateToPsychologist: () -> Unit,
    navigateToPayment: () -> Unit
) {
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    LaunchedEffect(Unit) {
        viewModel.loadPsychologist(id)
    }

    if (showAlert.first) {
        ValidationAlert(
            title = stringResource(R.string.alert_error_title),
            message = showAlert.second,
            onDismiss = {
                showAlert = showAlert.copy(first = false)
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .imePadding()
            .padding(Dimens.dp24)
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
                    navigateToPsychologist.invoke()
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.psychologist_detail),
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
                .height(Dimens.dp24)
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(Dimens.dp32)
                        .align(Alignment.Center),
                    color = Primary
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(Dimens.dp150)
                        .align(Alignment.Center)
                        .padding()
                        .clip(CircleShape),
                    model = state.psychologist.user.avatar,
                    placeholder = painterResource(R.drawable.img_user_placeholder),
                    error = painterResource(R.drawable.img_user_placeholder),
                    contentDescription = stringResource(R.string.image),
                    contentScale = ContentScale.Crop,
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp16)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = state.psychologist.user.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = state.psychologist.user.email,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp16)
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth(),
                border = BorderStroke(Dimens.dp1, Gray),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = White
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.dp80)
                        .padding(vertical = Dimens.dp8),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.dp4),
                            text = stringResource(R.string.age),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.dp4),
                            text = state.psychologist.user.age.toString(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Gray)
                            .width(Dimens.dp1)
                            .fillMaxHeight()
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.dp4),
                            text = stringResource(R.string.experience),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.dp4),
                            text = (currentYear - state.psychologist.registeredYear).toString(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Gray)
                            .width(Dimens.dp1)
                            .fillMaxHeight()
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.dp4),
                            text = stringResource(R.string.rating),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = Dimens.dp4),
                            text = "${state.psychologist.avgRating}(${state.psychologist.totalRating})",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp16)
            )
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                border = BorderStroke(Dimens.dp1, Gray),
                colors = CardDefaults.outlinedCardColors(
                    containerColor = White
                )
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.dp16,
                            top = Dimens.dp16,
                            end = Dimens.dp16,
                            bottom = Dimens.dp4
                        ),
                    text = stringResource(R.string.about_psychologist),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = Dimens.dp16,
                            end = Dimens.dp16,
                            bottom = Dimens.dp16
                        ),
                    text = state.psychologist.about.ifBlank { "-" },
                    textAlign = TextAlign.Justify,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp16)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp48),
                shape = Rounded,
                onClick = {

                }
            ) {
                Text(
                    text = stringResource(R.string.message).uppercase(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
