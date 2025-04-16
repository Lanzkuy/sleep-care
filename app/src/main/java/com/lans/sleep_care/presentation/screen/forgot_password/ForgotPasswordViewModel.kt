package com.lans.sleep_care.presentation.screen.forgot_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.domain.usecase.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ForgotPasswordUIState())
    val state: State<ForgotPasswordUIState> get() = _state

    fun onEvent(event: ForgotPasswordUIEvent) {
        when (event) {
            is ForgotPasswordUIEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(
                        value = event.email
                    )
                )
            }

            is ForgotPasswordUIEvent.VerificationCodeChanged -> {
                _state.value = _state.value.copy(
                    verificationCode = _state.value.verificationCode.copy(
                        value = event.verificationCode
                    )
                )
            }

            is ForgotPasswordUIEvent.NewPasswordChanged -> {
                _state.value = _state.value.copy(
                    newPassword = _state.value.newPassword.copy(
                        value = event.newPassword
                    )
                )
            }

            is ForgotPasswordUIEvent.ChangePasswordButtonClicked -> {
                changePassword()
            }
        }
    }

    private fun changePassword() {
        val stateValue = _state.value

        val emailResult = validatorUseCase.email.execute(stateValue.email.value)
        val verificationCodeResult =
            validatorUseCase.verificationCode.execute(stateValue.verificationCode.value)
        val passwordResult = validatorUseCase.password.execute(stateValue.newPassword.value)

        val hasErrors = listOf(
            emailResult,
            passwordResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                email = stateValue.email.copy(
                    error = emailResult.errorMessage
                ),
                verificationCode = stateValue.verificationCode.copy(
                    error = verificationCodeResult.errorMessage
                ),
                newPassword = stateValue.newPassword.copy(
                    error = passwordResult.errorMessage
                )
            )
            return
        }

        viewModelScope.launch {

        }
    }
}