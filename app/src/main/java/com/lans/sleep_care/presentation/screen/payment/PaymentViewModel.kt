package com.lans.sleep_care.presentation.screen.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetTransactionStatusUseCase
import com.lans.sleep_care.domain.usecase.user.GetUserProfileUseCase
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
    private val getTransactionStatusUseCase: GetTransactionStatusUseCase,
    private val getPsychologistUseCase: GetPsychologistUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PaymentUIState())
    val state: StateFlow<PaymentUIState> get() = _state

    private var pollingJob: Job? = null

    fun startPollingTransaction(orderId: String) {
        pollingJob?.cancel()

        pollingJob = viewModelScope.launch {
            var isCompleted = false

            while (!isCompleted) {
                getTransactionStatusUseCase.execute(orderId).collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            val status = response.data
                            _state.update {
                                it.copy(paymentStatus = status, isLoading = false)
                            }
                            isCompleted = true
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
        pollingJob?.cancel()
    }

    fun loadPsychologist(id: Int) {
        viewModelScope.launch {
            getPsychologistUseCase.execute(id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                psychologist = response.data,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = response.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            getUserProfileUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                user = response.data,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                error = response.message,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}