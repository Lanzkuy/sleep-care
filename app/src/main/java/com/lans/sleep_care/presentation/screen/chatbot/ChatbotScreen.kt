package com.lans.sleep_care.presentation.screen.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.form.ValidableTextField
import com.lans.sleep_care.presentation.component.misc.ChatBubble
import com.lans.sleep_care.presentation.component.misc.TypingIndicator
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun ChatbotScreen(
    viewModel: ChatbotViewModel = hiltViewModel(),
    email: String,
    name: String,
    navigateToHome: () -> Unit
) {
    val state by viewModel.state
    val listState = rememberLazyListState()
    var showAlert by remember { mutableStateOf(Pair(false, "")) }

    LaunchedEffect(Unit) {
        state.email = email
        state.name = name
        viewModel.getChatHistory()
    }

    LaunchedEffect(key1 = state.chatHistory.size, key2 = state.error) {
        val error = state.error

        if (state.chatHistory.isNotEmpty()) {
            listState.animateScrollToItem(state.chatHistory.lastIndex)
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
            .imePadding()
            .padding(
                start = Dimens.dp24,
                top = Dimens.dp24,
                end = Dimens.dp24
            )
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
                text = stringResource(R.string.chatbot),
                textAlign = TextAlign.Center,
                fontSize = Dimens.sp24,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .size(Dimens.dp50)
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = Dimens.dp16),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(Dimens.dp4)
        ) {
            items(state.chatHistory) { chat ->
                val isUser = chat.sender != "bot"
                ChatBubble(
                    message = chat.message,
                    isUser = isUser
                )
            }
            if (state.isBotLoading) {
                item {
                    TypingIndicator()
                }
            }
        }
        Row(
            modifier = Modifier
                .background(White)
                .fillMaxWidth()
                .padding(top = Dimens.dp8),
            horizontalArrangement = Arrangement.spacedBy(Dimens.dp8),
            verticalAlignment = Alignment.Top
        ) {
            ValidableTextField(
                modifier = Modifier
                    .weight(1f),
                input = state.message,
                placeholder = stringResource(R.string.message),
                onValueChange = {
                    viewModel.onEvent(ChatbotUIEvent.MessageChanged(it))
                }
            )
            ElevatedIconButton(
                modifier = Modifier
                    .size(Dimens.dp56),
                color = Primary,
                tint = White,
                icon = Icons.AutoMirrored.Default.Send,
                shape = Rounded,
                onClick = {
                    viewModel.onEvent(
                        ChatbotUIEvent.SendButtonClicked
                    )
                }
            )
        }
    }
}