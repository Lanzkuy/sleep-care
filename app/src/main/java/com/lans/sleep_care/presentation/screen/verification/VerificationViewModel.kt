package com.lans.sleep_care.presentation.screen.verification

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase
) : ViewModel() {
    private val _state = mutableStateOf(VerificationUIState())
    val state: State<VerificationUIState> get() = _state

    fun onEvent(event: VerificationUIEvent) {
        when (event) {
            is VerificationUIEvent.VerificationCodeChanged -> {
                _state.value = _state.value.copy(
                    verificationCode = _state.value.verificationCode.copy(
                        value = event.verificationCode
                    )
                )
            }

            is VerificationUIEvent.ConfirmButtonClicked -> {
                verify()
            }
        }
    }

    private fun verify() {
        val stateValue = _state.value

        val verificationCodeResult =
            validatorUseCase.verificationCode.execute(stateValue.verificationCode.value)

        if (!verificationCodeResult.isSuccess) {
            _state.value = stateValue.copy(
                verificationCode = stateValue.verificationCode.copy(
                    error = verificationCodeResult.errorMessage
                )
            )
            return
        }

        viewModelScope.launch {

        }
    }
}
