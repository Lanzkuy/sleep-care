package com.lans.sleep_care.presentation.screen.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.payment.CheckPaymentUseCase
import com.lans.sleep_care.domain.usecase.payment.UpdatePaymentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val getCheckPaymentUseCase: CheckPaymentUseCase,
    private val updatePaymentUseCase: UpdatePaymentUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PaymentUIState())
    val state: StateFlow<PaymentUIState> get() = _state

    private var hasStartedPolling = false
    private var pollingJob: Job? = null

    fun startPollingTransaction(orderId: String) {
        if (hasStartedPolling) return
        hasStartedPolling = true

        pollingJob?.cancel()
        pollingJob = viewModelScope.launch {
            var isCompleted = false

            while (!isCompleted) {
                getCheckPaymentUseCase.execute(orderId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            val status = response.data
                            _state.update {
                                it.copy(paymentStatus = status, isLoading = false)
                            }

                            if (status == "settlement" || status == "cancel") {
                                updatePayment(orderId)
                                isCompleted = true
                            }
                        }

                        is Resource.Error -> {
                            _state.update {
                                it.copy(error = response.message, isLoading = false)
                            }
                            isCompleted = !response.message.contains("404")
                        }

                        is Resource.Loading -> {
                            _state.update {
                                it.copy(isLoading = true)
                            }
                        }
                    }
                }

                delay(3000)
            }
        }
    }

    fun stopPollingTransaction() {
        hasStartedPolling = false
        pollingJob?.cancel()
    }

    private fun updatePayment(orderId: String) {
        viewModelScope.launch {
            updatePaymentUseCase.execute(
                orderId = orderId
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isUpdated = response.data,
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
                }
            }
        }
    }
}