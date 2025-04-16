package com.lans.sleep_care.presentation.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.domain.usecase.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase
) : ViewModel() {
    private val _state = mutableStateOf(LoginUIState())
    val state: State<LoginUIState> get() = _state

    fun onEvent(event: LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(
                        value = event.email
                    )
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(
                        value = event.password
                    )
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
                login()
            }
        }
    }

    private fun login() {
        val stateValue = _state.value

        val emailResult = validatorUseCase.email.execute(stateValue.email.value)
        val passwordResult = validatorUseCase.password.execute(stateValue.password.value)

        val hasErrors = listOf(
            emailResult,
            passwordResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                email = stateValue.email.copy(
                    error = emailResult.errorMessage
                ),
                password = stateValue.password.copy(
                    error = passwordResult.errorMessage
                )
            )
            return
        }

        viewModelScope.launch {

        }
    }
}