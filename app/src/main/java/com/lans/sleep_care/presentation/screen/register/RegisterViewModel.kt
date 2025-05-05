package com.lans.sleep_care.presentation.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.data.source.network.dto.request.RegisterRequest
import com.lans.sleep_care.domain.usecase.RegisterUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _state = mutableStateOf(RegisterUIState())
    val state: State<RegisterUIState> get() = _state

    fun onEvent(event: RegisterUIEvent) {
        when (event) {
            is RegisterUIEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(
                        value = event.email
                    )
                )
            }

            is RegisterUIEvent.NameChanged -> {
                _state.value = _state.value.copy(
                    name = _state.value.name.copy(
                        value = event.name
                    )
                )
            }

            is RegisterUIEvent.PasswordChanged -> {
                _state.value = _state.value.copy(
                    password = _state.value.password.copy(
                        value = event.password
                    )
                )
            }

            is RegisterUIEvent.ConfirmPasswordChanged -> {
                _state.value = _state.value.copy(
                    confirmPassword = _state.value.confirmPassword.copy(
                        value = event.confirmPassword
                    )
                )
            }

            is RegisterUIEvent.AgeChanged -> {
                if (event.age.all { it.isDigit() }) {
                    _state.value = _state.value.copy(
                        age = _state.value.age.copy(
                            value = event.age
                        )
                    )
                }
            }

            is RegisterUIEvent.ProblemChange -> {
                _state.value = _state.value.copy(
                    problem = event.problem
                )
            }

            is RegisterUIEvent.AddProblemButtonClicked -> {
                if (_state.value.problem.isNotBlank()) {
                    val updatedList = _state.value.problems.toMutableList().apply {
                        add(_state.value.problem.lowercase())
                    }
                    _state.value = _state.value.copy(
                        problems = updatedList,
                        problem = ""
                    )
                }
            }

            is RegisterUIEvent.NextButtonClicked -> {
                val isValid = validatePageOne()
                if (isValid) {
                    _state.value = _state.value.copy(currentPage = 1)
                    clearError()
                }
            }

            is RegisterUIEvent.PreviousButtonClicked -> {
                _state.value = _state.value.copy(currentPage = 0)
            }

            is RegisterUIEvent.RegisterButtonClicked -> {
                register()
            }
        }
    }

    private fun validatePageOne(): Boolean {
        val stateValue = _state.value
        val emailResult = validatorUseCase.email.execute(stateValue.email.value)
        val nameResult = validatorUseCase.name.execute(stateValue.name.value)
        val passwordResult = validatorUseCase.password.execute(stateValue.password.value)
        val confirmPasswordResult =
            validatorUseCase.confirmPassword.execute(
                password = stateValue.password.value,
                confirmPassword = stateValue.confirmPassword.value
            )

        val hasErrors = listOf(
            emailResult,
            nameResult,
            passwordResult,
            confirmPasswordResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                email = stateValue.email.copy(
                    error = emailResult.errorMessage
                ),
                name = stateValue.name.copy(
                    error = nameResult.errorMessage
                ),
                password = stateValue.password.copy(
                    error = passwordResult.errorMessage
                ),
                confirmPassword = stateValue.confirmPassword.copy(
                    error = confirmPasswordResult.errorMessage
                )
            )
        }

        return !hasErrors
    }

    private fun validatePageTwo(): Boolean {
        val stateValue = _state.value
        val ageResult = validatorUseCase.age.execute(stateValue.age.value)

        val hasErrors = listOf(
            ageResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                age = stateValue.age.copy(
                    error = ageResult.errorMessage
                )
            )
        }

        return !hasErrors
    }

    private fun register() {
        val isValid = validatePageTwo()
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            registerUseCase.execute(
                RegisterRequest(
                    name = _state.value.name.value,
                    email = _state.value.email.value,
                    password = _state.value.password.value,
                    passwordConfirmation = _state.value.confirmPassword.value,
                    age = _state.value.age.value.toInt(),
                    gender = _state.value.gender.lowercase(),
                    problems = _state.value.problems
                )
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isRegistered = response.data.email.isNotBlank(),
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

    private fun clearError() {
        val stateValue = _state.value
        _state.value = stateValue.copy(
            email = stateValue.email.copy(
                error = null
            ),
            name = stateValue.name.copy(
                error = null
            ),
            password = stateValue.password.copy(
                error = null
            ),
            confirmPassword = stateValue.confirmPassword.copy(
                error = null
            ),
            age = stateValue.age.copy(
                error = null
            )
        )
    }
}