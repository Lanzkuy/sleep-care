package com.lans.sleep_care.presentation.screen.psychologist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.form.SearchField
import com.lans.sleep_care.presentation.component.items.PsychologistItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun PsychologistScreen(
    viewModel: PsychologistViewModel = hiltViewModel(),
    navigateToHome: () -> Unit
) {
    val therapists = listOf("Test1", "Test2", "Test3")
    val state by viewModel.state

    Column(
        modifier = Modifier
            .background(White)
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
                    color = DarkGray,
                    shape = Rounded
                ),
            input = state.search,
            placeholder = stringResource(R.string.search),
            onValueChange = {
                viewModel.onEvent(PsychologistUIEvent.SearchChanged(it))
            }
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp16)
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
        ) {
            items(therapists) { name ->
                PsychologistItem(
                    name = name,
                    experience = 9,
                    like = 85,
                    onClick = { },
                    onMessageClick = { }
                )
            }
        }
    }
}
