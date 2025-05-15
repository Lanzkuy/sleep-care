package com.lans.sleep_care.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.DpOffset
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.lans.sleep_care.R
import com.lans.sleep_care.presentation.component.button.ElevatedIconButton
import com.lans.sleep_care.presentation.component.dialog.ValidationAlert
import com.lans.sleep_care.presentation.component.items.MenuItem
import com.lans.sleep_care.presentation.theme.Black
import com.lans.sleep_care.presentation.theme.Dimens
import com.lans.sleep_care.presentation.theme.Rounded
import com.lans.sleep_care.presentation.theme.White

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToTherapist: () -> Unit,
    navigateToMyTherapy: (id: String, isTherapyInProgress: Boolean) -> Unit,
    navigateToChatbot: (name: String, email: String) -> Unit,
    navigateToHistory: () -> Unit,
    navigateToProfile: (
        id: String,
        name: String,
        age: String,
        gender: String,
        problemList: List<String>?
    ) -> Unit,
    navigateToChangePassword: () -> Unit
) {
    val state by viewModel.state
    var showAlert by remember { mutableStateOf(Pair(false, "")) }
    var showOverflowMenu by remember { mutableStateOf(false) }
    var showLogoutConfirmation by remember { mutableStateOf(Pair(false, "")) }
    val buttonItems = listOf(
        Triple(R.drawable.img_illustration_doctor, R.string.psychologist, navigateToTherapist),
        Triple(R.drawable.img_illustration_therapy, R.string.mytherapy) {
            navigateToMyTherapy(state.user.id.toString(), state.user.isTherapyInProgress)
        },
        Triple(R.drawable.img_illustration_chatbot, R.string.chatbot) {
            navigateToChatbot(state.user.email, state.user.name)
        },
        Triple(R.drawable.img_illustration_document, R.string.history, navigateToHistory)
    )

    LaunchedEffect(Unit) {
        viewModel.getMe()
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

    if (showLogoutConfirmation.first) {
        ValidationAlert(
            title = stringResource(R.string.alert_warning_title),
            message = showLogoutConfirmation.second,
            confirmButtonText = stringResource(R.string.yes),
            onDismiss = {
                showLogoutConfirmation = showAlert.copy(first = false)
            },
            onConfirm = {
                viewModel.onEvent(HomeUIEvent.LogoutButtonClicked)
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
            horizontalArrangement = Arrangement
                .spacedBy(Dimens.dp12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                Surface(
                    modifier = Modifier
                        .size(Dimens.dp50),
                    color = White,
                    shape = CircleShape,
                    shadowElevation = Dimens.dp16
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .clickable { showOverflowMenu = true },
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxSize(),
                            model = null,
                            placeholder = painterResource(R.drawable.img_user_placeholder),
                            error = painterResource(R.drawable.img_user_placeholder),
                            contentDescription = stringResource(R.string.image),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
                DropdownMenu(
                    offset = DpOffset(x = Dimens.dp24, y = Dimens.dp4),
                    expanded = showOverflowMenu,
                    onDismissRequest = { showOverflowMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.edit_profile)) },
                        onClick = {
                            showOverflowMenu = false
                            navigateToProfile.invoke(
                                state.user.id.toString(),
                                state.user.name,
                                state.user.age.toString(),
                                state.user.gender,
                                state.user.problems
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.change_password)) },
                        onClick = {
                            showOverflowMenu = false
                            navigateToChangePassword.invoke()
                        }
                    )
                }
            }
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
                    text = state.user.name
                        .split(" ")[0]
                            .ifEmpty { stringResource(R.string.user) },
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
                    showLogoutConfirmation = Pair(true, "Apakah anda ingin logout?")
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