package com.lans.sleep_care.presentation.screen.change_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(

) : ViewModel() {
    private val _state = mutableStateOf(ChangePasswordUIState())
    val state: State<ChangePasswordUIState> get() = _state

    fun onEvent(event: ChangePasswordUIEvent) {
        when (event) {
            is ChangePasswordUIEvent.CurrentPasswordChanged -> {
                _state.value = _state.value.copy(
                    currentPassword = _state.value.currentPassword.copy(
                        value = event.currentPassword
                    )
                )
            }

            is ChangePasswordUIEvent.NewPasswordChanged -> {
                _state.value = _state.value.copy(
                    newPassword = _state.value.newPassword.copy(
                        value = event.newPassword
                    )
                )
            }

            is ChangePasswordUIEvent.NewPasswordConfirmationChanged -> {
                _state.value = _state.value.copy(
                    newPasswordConfirmation = _state.value.newPasswordConfirmation.copy(
                        value = event.newPasswordConfirmation
                    )
                )
            }

            is ChangePasswordUIEvent.UpdateButtonClicked -> {
                changePassword()
            }
        }
    }

    private fun changePassword() {
        viewModelFactory {

        }
    }
}
