package com.lans.sleep_care.presentation.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.auth.LoginUseCase
import com.lans.sleep_care.domain.usecase.auth.SaveSessionUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase,
    private val loginUseCase: LoginUseCase,
    private val saveSessionUseCase: SaveSessionUseCase
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

    private fun validate(stateValue: LoginUIState): Boolean {
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
        }

        return !hasErrors
    }

    private fun login() {
        val stateValue = _state.value

        val isValid = validate(stateValue)
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            loginUseCase.execute(
                email = stateValue.email.value,
                password = stateValue.password.value
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value =_state.value.copy(
                            isLoggedIn = response.data.user.isActive,
                            isLoading = false
                        )
                        saveSessionUseCase.invoke(
                            accessToken = response.data.accessToken
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
                }
            }
        }
    }
}