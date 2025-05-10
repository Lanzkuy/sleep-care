package com.lans.sleep_care.presentation.screen.forgot_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.auth.ForgotPasswordUseCase
import com.lans.sleep_care.domain.usecase.auth.ResetPasswordUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
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

            is ForgotPasswordUIEvent.NewPasswordConfirmationChanged -> {
                _state.value = _state.value.copy(
                    newPasswordConfirmation = _state.value.newPasswordConfirmation.copy(
                        value = event.newPasswordConfirmation
                    )
                )
            }

            is ForgotPasswordUIEvent.ForgotPasswordButtonClicked -> {
                forgotPassword()
            }

            is ForgotPasswordUIEvent.BackButtonClicked -> {
                _state.value = _state.value.copy(currentPage = 0)
            }

            is ForgotPasswordUIEvent.ChangePasswordButtonClicked -> {
                resetPassword()
            }
        }
    }

    private fun validatePageOne(stateValue: ForgotPasswordUIState): Boolean {
        val emailResult = validatorUseCase.email.execute(stateValue.email.value)

        if (!emailResult.isSuccess) {
            _state.value = stateValue.copy(
                email = stateValue.email.copy(
                    error = emailResult.errorMessage
                )
            )
        }

        return emailResult.isSuccess
    }

    private fun validatePageTwo(stateValue: ForgotPasswordUIState): Boolean {
        val verificationCodeResult =
            validatorUseCase.verificationCode.execute(stateValue.verificationCode.value)
        val passwordResult = validatorUseCase.password.execute(stateValue.newPassword.value)
        val passwordConfirmationResult = validatorUseCase.passwordConfirmation.execute(
            password = stateValue.newPassword.value,
            passwordConfirmation = stateValue.newPasswordConfirmation.value
        )

        val hasErrors = listOf(
            passwordResult,
            passwordConfirmationResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                verificationCode = stateValue.verificationCode.copy(
                    error = verificationCodeResult.errorMessage
                ),
                newPassword = stateValue.newPassword.copy(
                    error = passwordResult.errorMessage
                ),
                newPasswordConfirmation = stateValue.newPassword.copy(
                    error = passwordResult.errorMessage
                )
            )
        }

        return !hasErrors
    }

    private fun forgotPassword() {
        val stateValue = _state.value

        val isValid = validatePageOne(stateValue)
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            forgotPasswordUseCase.execute(stateValue.email.value).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            forgotPasswordResponse = response.data,
                            currentPage = 1,
                            isCountdown = System.currentTimeMillis(),
                            isForgotPasswordLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            currentPage = if (response.message.contains("sudah dikirim")) 1 else 0,
                            isForgotPasswordLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isForgotPasswordLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun resetPassword() {
        val stateValue = _state.value

        val isValid = validatePageTwo(stateValue)
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            resetPasswordUseCase.execute(
                email = stateValue.email.value,
                token = stateValue.verificationCode.value.toInt(),
                password = stateValue.newPassword.value,
                passwordConfirmation = stateValue.newPasswordConfirmation.value
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            resetPasswordResponse = response.data,
                            isResetPasswordLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isResetPasswordLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isResetPasswordLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}