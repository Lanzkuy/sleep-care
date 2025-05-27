package com.lans.sleep_care.presentation.screen.psychologist_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.payment.CancelPaymentUseCase
import com.lans.sleep_care.domain.usecase.payment.CreatePaymentUseCase
import com.lans.sleep_care.domain.usecase.psychologist.GetPsychologistUseCase
import com.lans.sleep_care.domain.usecase.therapy.CreateOrderTherapyUseCase
import com.lans.sleep_care.domain.usecase.therapy.GetOrderTherapyStatusUseCase
import com.lans.sleep_care.domain.usecase.user.GetUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PsychologistDetailViewModel @Inject constructor(
    private val getPsychologistUseCase: GetPsychologistUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getOrderTherapyStatusUseCase: GetOrderTherapyStatusUseCase,
    private val createOrderTherapyUseCase: CreateOrderTherapyUseCase,
    private val createPaymentUseCase: CreatePaymentUseCase,
    private val cancelPaymentUseCase: CancelPaymentUseCase
) : ViewModel() {
    private val _state = mutableStateOf(PsychologistDetailUIState())
    val state: State<PsychologistDetailUIState> get() = _state

    fun onEvent(event: PsychologistDetailUIEvent) {
        if (event is PsychologistDetailUIEvent.OrderButtonClicked) {
            with(_state.value) {
                if (order.paymentStatus == "pending" && order.therapy.doctorId == psychologist.id) {
                    if (order.paymentId.isEmpty()) {
                        createPayment()
                    } else {
                        _state.value = _state.value.copy(
                            paymentToken = order.paymentId
                        )
                    }
                } else {
                    if (order.therapy.doctorId != 0 && order.therapy.doctorId != psychologist.id) {
                        cancelPayment()
                    }
                    createOrderTherapy()
                }
            }
        }
    }

    fun loadPsychologist(id: Int) {
        viewModelScope.launch {
            getPsychologistUseCase.execute(id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            psychologist = response.data,
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

    fun loadUser() {
        viewModelScope.launch {
            getUserProfileUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            user = response.data,
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

    fun getOrderTherapyStatus() {
        viewModelScope.launch {
            getOrderTherapyStatusUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            order = response.data,
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message.takeUnless { it == "Order tidak ditemukan" }
                                ?: "",
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

    private fun createOrderTherapy() {
        viewModelScope.launch {
            createOrderTherapyUseCase.execute(_state.value.psychologist.id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            order = response.data
                        )
                        createPayment()
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isButtonLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isButtonLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun createPayment() {
        viewModelScope.launch {
            createPaymentUseCase.execute(
                orderId = _state.value.order.id,
                user = _state.value.user
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            paymentToken = response.data.first,
                            isButtonLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isButtonLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isButtonLoading = true
                        )
                    }
                }
            }
        }
    }

    private fun cancelPayment() {
        viewModelScope.launch {
            cancelPaymentUseCase.execute(
                orderId = _state.value.order.id
            ).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            error = response.message,
                            isButtonLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isButtonLoading = true
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}
