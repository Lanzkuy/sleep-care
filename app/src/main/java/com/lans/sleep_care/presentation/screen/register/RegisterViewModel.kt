package com.lans.sleep_care.presentation.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase
) : ViewModel() {
    private val _state = mutableStateOf(RegisterUIState())
    val state: State<RegisterUIState> get() = _state

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(
                        value = event.email
                    )
                )
            }

            is RegisterUIEvent.NameChanged -> {
                _state.value = _state.value.copy(
                    name = _state.value.name.copy(
                        value = event.name
                    )
                )
            }

            is RegisterUIEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(
                        value = event.password
                    )
                )
            }

            is RegisterUIEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(
                    confirmPassword = _state.value.confirmPassword.copy(
                        value = event.confirmPassword
                    )
                )
            }

            is RegisterUIEvent.RegisterButtonClicked -> {
                register()
            }
        }
    }

    private fun register() {
        val stateValue = _state.value

        val emailResult = validatorUseCase.email.execute(stateValue.email.value)
        val nameResult = validatorUseCase.name.execute(stateValue.name.value)
        val passwordResult = validatorUseCase.password.execute(stateValue.password.value)
        val confirmPasswordResult = validatorUseCase.password.execute(stateValue.confirmPassword.value)

        val hasErrors = listOf(
            emailResult,
            nameResult,
            passwordResult,
            confirmPasswordResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                email = stateValue.email.copy(
                    error = emailResult.errorMessage
                ),
                name = stateValue.name.copy(
                    error = nameResult.errorMessage
                ),
                password = stateValue.password.copy(
                    error = passwordResult.errorMessage
                ),
                confirmPassword = stateValue.confirmPassword.copy(
                    error = confirmPasswordResult.errorMessage
                )
            )
            return
        }

        viewModelScope.launch {

        }
    }
}