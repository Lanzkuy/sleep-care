package com.lans.sleep_care.presentation.screen.chat_room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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
import com.lans.sleep_care.presentation.component.misc.ChatBubble
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.form.ValidableTextField
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Primary
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun ChatRoomScreen(
    viewModel: ChatRoomViewModel = hiltViewModel(),
    navigateToMyTheraphy: () -> Unit
) {
    val state by viewModel.state

    Box(
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
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                        navigateToMyTheraphy.invoke()
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = Dimens.dp16),
                verticalArrangement = Arrangement.spacedBy(Dimens.dp4)
            ) {
                item {
                    ChatBubble(
                        message = "Halo",
                        isUser = true
                    )
                    ChatBubble(
                        message = "Halo selamat siang, ada yang bisa saya bantu?",
                        isUser = false
                    )
                    ChatBubble(
                        message = "Apa itu insomnia?",
                        isUser = true
                    )
                    ChatBubble(
                        message = "Insomnia adalah gangguan tidur yang ditandai dengan kesulitan untuk memulai atau mempertahankan tidur.",
                        isUser = false
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .background(White)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
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
                    viewModel.onEvent(ChatRoomUIEvent.MessageChanged(it))
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
                    viewModel.onEvent(ChatRoomUIEvent.SendButtonClicked)
                }
            )
        }
    }
}