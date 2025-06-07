package com.lans.sleep_care.presentation.screen.psychologist

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.form.SearchField
import com.lans.sleep_care.presentation.component.items.PsychologistItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Gray
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White
import kotlinx.coroutines.flow.distinctUntilChanged

@Composable
fun PsychologistScreen(
    viewModel: PsychologistViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToPsychologistDetail: (id: String) -> Unit
) {
    val state by viewModel.state
    val listState = rememberLazyListState()
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        viewModel.loadAllPsychologist()
    }

    LaunchedEffect(listState) {
        snapshotFlow {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisible
        }.collect { lastVisibleIndex ->
            if (lastVisibleIndex >= state.filteredPsychologists.size - 3 && !state.isPaginating) {
                viewModel.loadAllPsychologist()
            }
        }
    }

    LaunchedEffect(key1 = state.psychologists, key2 = state.error) {
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
                    navigateToHome.invoke()
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.psychologist),
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
                .height(Dimens.dp16)
        )
        SearchField(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = Dimens.dp1,
                    color = Gray,
                    shape = Rounded
                ),
            input = state.search,
            placeholder = stringResource(R.string.search_psychologist),
            shape = Rounded,
            onValueChange = {
                viewModel.onEvent(PsychologistUIEvent.SearchChanged(it))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp16)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(Dimens.dp32)
                            .align(Alignment.Center),
                        color = Primary
                    )
                }

                state.psychologists.isEmpty() -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier
                                .size(Dimens.dp160),
                            painter = painterResource(R.drawable.img_illustration_nothing),
                            contentDescription = stringResource(R.string.image)
                        )
                        Text(
                            text = stringResource(R.string.nothing_here),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = listState,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
                    ) {
                        items(state.filteredPsychologists) { psychologist ->
                            PsychologistItem(
                                name = psychologist.user.name,
                                image = psychologist.user.avatar,
                                rating = psychologist.avgRating,
                                totalReview = psychologist.totalRating,
                                onClick = {
                                    navigateToPsychologistDetail(psychologist.id.toString())
                                }
                            )
                        }

                        if (state.isPaginating) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(Dimens.dp16)
                                        .size(Dimens.dp32),
                                    color = Primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
