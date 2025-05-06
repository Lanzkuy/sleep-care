package com.lans.sleep_care.presentation.screen.verification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import com.lans.sleep_care.data.source.network.dto.request.VerifyOtpRequest
import com.lans.sleep_care.domain.usecase.auth.OtpRequestUseCase
import com.lans.sleep_care.domain.usecase.auth.VerifyOtpUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase,
    private val otpRequestUseCase: OtpRequestUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase
) : ViewModel() {
    private val _state = mutableStateOf(VerificationUIState())
    val state: State<VerificationUIState> get() = _state

    fun onEvent(event: VerificationUIEvent) {
        when (event) {
            is VerificationUIEvent.VerificationCodeChanged -> {
                _state.value = _state.value.copy(
                    otpCode = _state.value.otpCode.copy(
                        value = event.verificationCode
                    )
                )
            }

            is VerificationUIEvent.ConfirmButtonClicked -> {
                verifyOtp(event.email)
            }
        }
    }

    fun requestOtp(email: String) {
        viewModelScope.launch {
            otpRequestUseCase.execute(
                OtpRequest(email = email)
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            requestOtpResponse = response.data,
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

    private fun verifyOtp(email: String) {
        val stateValue = _state.value

        val verificationCodeResult =
            validatorUseCase.verificationCode.execute(stateValue.otpCode.value)

        if (!verificationCodeResult.isSuccess) {
            _state.value = stateValue.copy(
                otpCode = stateValue.otpCode.copy(
                    error = verificationCodeResult.errorMessage
                )
            )
            return
        }

        viewModelScope.launch {
            verifyOtpUseCase.execute(
                VerifyOtpRequest(
                    otp = _state.value.otpCode.value,
                    email = email
                )
            ).collect{ response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            verificationResponse = response.data,
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
