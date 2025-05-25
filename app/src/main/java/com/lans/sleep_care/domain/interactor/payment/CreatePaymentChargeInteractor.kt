package com.lans.sleep_care.domain.interactor.payment

import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.SafeApiCall
import com.lans.sleep_care.data.source.network.dto.request.payment.CustomerDetailRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.ItemDetailRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.PaymentDetailRequest
import com.lans.sleep_care.data.source.network.dto.request.payment.PaymentRequest
import com.lans.sleep_care.domain.model.auth.User
import com.lans.sleep_care.domain.repository.IPaymentRepository
import com.lans.sleep_care.domain.usecase.payment.CreatePaymentChargeUseCase
import com.lans.sleep_care.utils.splitName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreatePaymentChargeInteractor @Inject constructor(
    private val repository: IPaymentRepository
) : CreatePaymentChargeUseCase, SafeApiCall {
    override suspend fun execute(
        orderId: String,
        user: User
    ): Flow<Resource<Pair<String, String>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                safeCall {
                    val (firstName, lastName) = splitName(user.name)
                    val psychologistFee = 350000
                    val applicationFee = 20000

                    val response = repository.createPaymentCharge(
                        PaymentRequest(
                            transactionDetails = PaymentDetailRequest(
                                orderId = orderId,
                                grossAmount = (psychologistFee + applicationFee)
                            ),
                            itemDetails = listOf(
                                ItemDetailRequest(
                                    price = psychologistFee,
                                    quantity = 1,
                                    name = "Biaya Jasa Psikolog"
                                ),
                                ItemDetailRequest(
                                    price = applicationFee,
                                    quantity = 1,
                                    name = "Biaya Jasa Aplikasi"
                                )
                            ),
                            customerDetails = CustomerDetailRequest(
                                email = user.email,
                                firstName = firstName,
                                lastName = lastName
                            )
                        )
                    )

                    Pair(response.token, response.redirectUrl)
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}