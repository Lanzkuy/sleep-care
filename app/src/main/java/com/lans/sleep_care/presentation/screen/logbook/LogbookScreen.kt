package com.lans.sleep_care.presentation.screen.logbook

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.button.OutlinedIconButton
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun LogbookScreen(
    viewModel: LogbookViewModel = hiltViewModel(),
    navigateToMyTherapy: () -> Unit,
    navigateToSleepDiary: () -> Unit,
    navigateToIdentifyValue: () -> Unit,
    navigateToThoughtRecord: () -> Unit,
    navigateToEmotionRecord: () -> Unit,
    navigateToCommitedAction: () -> Unit,
) {
    val buttonItems = listOf(
        Triple(R.drawable.ic_sleep, R.string.sleep_diary, navigateToSleepDiary),
        Triple(R.drawable.ic_star, R.string.identify_value, navigateToIdentifyValue),
        Triple(R.drawable.ic_mind, R.string.thought_record, navigateToThoughtRecord),
        Triple(R.drawable.ic_smile, R.string.emotion_record, navigateToEmotionRecord),
        Triple(R.drawable.ic_flag, R.string.commited_action, navigateToCommitedAction)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(Dimens.dp24),
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
                    navigateToMyTherapy.invoke()
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = stringResource(R.string.logbook),
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
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.dp8)
        ) {
            items(buttonItems) { (icon, name, action) ->
                OutlinedIconButton(
                    modifier = Modifier.fillMaxWidth(),
                    icon = painterResource(icon),
                    name = stringResource(name),
                    onClick = { action.invoke() }
                )
            }
        }
    }
}