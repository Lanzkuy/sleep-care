package com.lans.sleep_care.presentation.screen.change_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.user.ChangePasswordUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase
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

    private fun validate(stateValue: ChangePasswordUIState): Boolean {
        val currentPasswordResult =
            validatorUseCase.currentPassword.execute(stateValue.currentPassword.value)
        val newPasswordResult = validatorUseCase.password.execute(stateValue.newPassword.value)
        val newPasswordConfirmationResult =
            validatorUseCase.passwordConfirmation.execute(
                password = stateValue.newPassword.value,
                passwordConfirmation = stateValue.newPasswordConfirmation.value
            )

        val hasErrors = listOf(
            currentPasswordResult,
            newPasswordResult,
            newPasswordConfirmationResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                currentPassword = stateValue.currentPassword.copy(
                    error = currentPasswordResult.errorMessage
                ),
                newPassword = stateValue.newPassword.copy(
                    error = newPasswordResult.errorMessage
                ),
                newPasswordConfirmation = stateValue.newPasswordConfirmation.copy(
                    error = newPasswordConfirmationResult.errorMessage
                )
            )
        }

        return !hasErrors
    }

    private fun changePassword() {
        val stateValue = _state.value

        val isValid = validate(stateValue)
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            changePasswordUseCase.execute(
                currentPassword = stateValue.currentPassword.value,
                newPassword = stateValue.newPassword.value,
                newPasswordConfirmation = stateValue.newPasswordConfirmation.value
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isPasswordChanged = response.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}
