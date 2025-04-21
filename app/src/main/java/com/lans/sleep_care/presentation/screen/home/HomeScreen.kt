package com.lans.sleep_care.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.ElevatedIconButton
import com.lans.sleep_care.presentation.component.MenuItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.DarkGray
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToLogin: () -> Unit,
    navigateToTherapist: () -> Unit,
    navigateToMyTherapy: () -> Unit,
    navigateToChatbot: () -> Unit,
    navigateToHistory: () -> Unit
) {
    val buttonItems = listOf(
        Triple(R.drawable.image_placeholder, R.string.psychologist, navigateToTherapist),
        Triple(R.drawable.image_placeholder, R.string.mytherapy, navigateToMyTherapy),
        Triple(R.drawable.image_placeholder, R.string.chatbot, navigateToChatbot),
        Triple(R.drawable.image_placeholder, R.string.history, navigateToHistory)
    )

    Column(
        modifier = Modifier
            .background(White)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(Dimens.dp24)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement
                .spacedBy(Dimens.dp12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(Dimens.dp50)
                    .padding()
                    .border(
                        width = Dimens.dp1,
                        color = DarkGray,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                model = null,
                placeholder = painterResource(R.drawable.ic_person),
                error = painterResource(R.drawable.ic_person),
                contentDescription = stringResource(R.string.image),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.hello),
                    fontSize = Dimens.sp20,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = Dimens.sp24
                )
                Text(
                    text = stringResource(R.string.user),
                    fontSize = Dimens.sp24,
                    fontWeight = FontWeight.Bold,
                    lineHeight = Dimens.sp28
                )
            }
            ElevatedIconButton(
                modifier = Modifier
                    .size(Dimens.dp50),
                color = White,
                tint = Black,
                icon = Icons.AutoMirrored.Default.Logout,
                shape = Rounded,
                onClick = {
                    viewModel.onEvent(HomeUIEvent.LogoutButtonClicked)
                    navigateToLogin.invoke()
                }
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.dp8)
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth(),
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(Dimens.dp12),
            horizontalArrangement = Arrangement.spacedBy(Dimens.dp16),
            verticalArrangement = Arrangement.spacedBy(Dimens.dp16)
        ) {
            items(buttonItems) { (image, name, action) ->
                MenuItem(
                    image = painterResource(image),
                    text = stringResource(name),
                    onClick = action
                )
            }
        }
    }
}