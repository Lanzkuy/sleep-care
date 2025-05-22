package com.lans.sleep_care.presentation.screen.psychologist_detail

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.button.LoadingButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.screen.payment.PaymentActivity
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
    navigateToPsychologist: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)

    LaunchedEffect(Unit) {
        viewModel.loadPsychologist(id)
        viewModel.loadUser()
        viewModel.getOrderTherapyStatus()
    }

    LaunchedEffect(key1 = state.order) {
        val order = state.order

        if (order.id.isNotEmpty() && order.paymentStatus == "pending") {
            viewModel.getPaymentSession()
        }
    }

    LaunchedEffect(key1 = state.paymentSession) {
        val paymentSession = state.paymentSession

        if (paymentSession.first.isNotEmpty() &&
            paymentSession.second == state.order.id &&
            paymentSession.third == state.psychologist.id
        ) {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra("token", paymentSession.first)
                putExtra("orderId", paymentSession.second)
                putExtra("psychologistId", paymentSession.third)
            }
            context.startActivity(intent)
        }
    }

    LaunchedEffect(key1 = state.paymentToken) {
        val paymentToken = state.paymentToken

        if (paymentToken.isNotEmpty()) {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra("token", paymentToken)
                putExtra("orderId", state.order.id)
                putExtra("psychologistId", state.psychologist.id)
            }
            context.startActivity(intent)
        }
    }

    LaunchedEffect(key1 = state.error) {
        val error = state.error

        if (error.isNotBlank()) {
            showAlert = Pair(true, error)
            state.error = ""
        }
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
            LoadingButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp48),
                text = stringResource(R.string.message),
                shape = Rounded,
                isLoading = state.isButtonLoading,
                onClick = {
                    viewModel.onEvent(PsychologistDetailUIEvent.OrderButtonClicked)
                }
            )
        }
    }
}
