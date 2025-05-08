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

    private fun validatePageOne(): Boolean {
        val stateValue = _state.value
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

    private fun validatePageTwo(): Boolean {
        val stateValue = _state.value
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
        val isValid = validatePageOne()
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            viewModelScope.launch {
                forgotPasswordUseCase.execute(_state.value.email.value).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                forgotPasswordResponse = response.data,
                                currentPage = 1,
                                isLoading = false
                            )
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                error = response.message,
                                currentPage = if (response.message.contains("Duplicate")) 1 else 0,
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

    private fun resetPassword() {
        val isValid = validatePageTwo()
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            resetPasswordUseCase.execute(
                email = _state.value.email.value,
                token = _state.value.verificationCode.value.toInt(),
                password = _state.value.newPassword.value,
                passwordConfirmation = _state.value.newPasswordConfirmation.value
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            resetPasswordResponse = response.data,
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