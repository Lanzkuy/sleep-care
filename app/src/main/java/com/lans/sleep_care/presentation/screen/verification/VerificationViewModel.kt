package com.lans.sleep_care.presentation.screen.verification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.dto.request.OtpRequest
import com.lans.sleep_care.data.source.network.dto.request.VerifyOtpRequest
import com.lans.sleep_care.domain.usecase.auth.RequestOtpUseCase
import com.lans.sleep_care.domain.usecase.auth.VerifyOtpUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase,
    private val requestOtpUseCase: RequestOtpUseCase,
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

            is VerificationUIEvent.SendVerificationCodeButtonClicked -> {
                requestOtp(event.email)
            }

            is VerificationUIEvent.ConfirmButtonClicked -> {
                verifyOtp(event.email)
            }
        }
    }

    private fun validate(stateValue: VerificationUIState): Boolean {
        val verificationCodeResult =
            validatorUseCase.verificationCode.execute(stateValue.otpCode.value)

        if (!verificationCodeResult.isSuccess) {
            _state.value = stateValue.copy(
                otpCode = stateValue.otpCode.copy(
                    error = verificationCodeResult.errorMessage
                )
            )
        }

        return verificationCodeResult.isSuccess
    }

    fun requestOtp(email: String) {
        viewModelScope.launch {
            requestOtpUseCase.execute(
                OtpRequest(email = email)
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            requestOtpResponse = response.data,
                            isRequestOtpLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isRequestOtpLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isRequestOtpLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun verifyOtp(email: String) {
        val stateValue = _state.value

        val isValid = validate(stateValue)
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            verifyOtpUseCase.execute(
                VerifyOtpRequest(
                    otp = stateValue.otpCode.value,
                    email = email
                )
            ).collect{ response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            verificationResponse = response.data,
                            isVerificationLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isVerificationLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isVerificationLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}
