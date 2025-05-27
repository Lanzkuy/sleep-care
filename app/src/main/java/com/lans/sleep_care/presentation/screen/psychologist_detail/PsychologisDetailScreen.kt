package com.lans.sleep_care.presentation.screen.psychologist_detail

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Info
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.PrimaryVariant
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.RoundedLarge
import com.lans.sleep_care.presentation.theme.Secondary
import com.lans.sleep_care.presentation.theme.Warning
import com.lans.sleep_care.presentation.theme.White

@Composable
fun PsychologistDetailScreen(
    viewModel: PsychologistDetailViewModel = hiltViewModel(),
    id: Int,
    navigateToPsychologist: () -> Unit,
    navigateToHome: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    val paymentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            navigateToHome.invoke()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadPsychologist(id)
        viewModel.loadUser()
        viewModel.getOrderTherapyStatus()
    }

    LaunchedEffect(key1 = state.paymentToken, key2 = state.error) {
        val paymentToken = state.paymentToken
        val error = state.error

        if (paymentToken.isNotEmpty()) {
            val intent = Intent(context, PaymentActivity::class.java).apply {
                putExtra("orderId", state.order.id)
                putExtra("token", paymentToken)
            }
            paymentLauncher.launch(intent)
        }

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
            OutlinedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                border = BorderStroke(width = Dimens.dp1, color = Gray),
                colors = CardDefaults.outlinedCardColors(containerColor = White),
                shape = RoundedLarge
            ) {
                Column(
                    modifier = Modifier
                        .padding(Dimens.dp24),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(Dimens.dp150)
                            .padding()
                            .clip(CircleShape),
                        model = state.psychologist.user.avatar,
                        placeholder = painterResource(R.drawable.img_user_placeholder),
                        error = painterResource(R.drawable.img_user_placeholder),
                        contentDescription = stringResource(R.string.image),
                        contentScale = ContentScale.Crop,
                    )
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
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.dp8)
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
                            text = state.psychologist.avgRating.toString(),
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = " • ${state.psychologist.totalRating} reviews",
                            color = DarkGray,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.dp8)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(Dimens.dp24),
                            imageVector = Icons.Default.Email,
                            tint = PrimaryVariant,
                            contentDescription = stringResource(R.string.icon),
                        )
                        Spacer(modifier = Modifier.width(Dimens.dp4))
                        Text(
                            text = state.psychologist.user.email,
                            color = DarkGray,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.dp20)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.about_psychologist),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(Dimens.dp6))
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
                                .padding(
                                    start = Dimens.dp8,
                                    top = Dimens.dp8,
                                    end = Dimens.dp8
                                ),
                            text = state.psychologist.about.ifEmpty {
                                stringResource(R.string.no_psychologist_about)
                            },
                            textAlign = TextAlign.Justify,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.dp20)
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
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.dp12)
            )
        }
    }
}
