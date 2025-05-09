package com.lans.sleep_care.presentation.screen.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.User
import com.lans.sleep_care.domain.usecase.auth.RegisterUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import com.lans.sleep_care.utils.capitalize
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

            is RegisterUIEvent.PasswordConfirmationChanged -> {
                _state.value = _state.value.copy(
                    passwordConfirmation = _state.value.passwordConfirmation.copy(
                        value = event.passwordConfirmation
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

            is RegisterUIEvent.GenderSelected -> {
                _state.value = _state.value.copy(
                    gender = event.gender
                )
            }

            is RegisterUIEvent.ToggleProblem -> {
                val updatedProblems = _state.value.problems.toMutableList()
                if (updatedProblems.contains(event.problem)) {
                    updatedProblems.remove(event.problem)
                } else {
                    updatedProblems.add(event.problem)
                }
                _state.value = _state.value.copy(problems = updatedProblems)
            }

            is RegisterUIEvent.NextButtonClicked -> {
                val stateValue = _state.value

                val isValid = validatePageOne(stateValue)

                if (isValid) {
                    _state.value = stateValue.copy(currentPage = 1)
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

    fun initializeData(
        availableProblems: List<String>
    ) {
        _state.value.availableProblems.addAll(availableProblems)
    }

    private fun validatePageOne(stateValue: RegisterUIState): Boolean {
        val emailResult = validatorUseCase.email.execute(stateValue.email.value)
        val nameResult = validatorUseCase.name.execute(stateValue.name.value)
        val passwordResult = validatorUseCase.password.execute(stateValue.password.value)
        val passwordConfirmationResult =
            validatorUseCase.passwordConfirmation.execute(
                password = stateValue.password.value,
                passwordConfirmation = stateValue.passwordConfirmation.value
            )

        val hasErrors = listOf(
            emailResult,
            nameResult,
            passwordResult,
            passwordConfirmationResult
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
                passwordConfirmation = stateValue.passwordConfirmation.copy(
                    error = passwordConfirmationResult.errorMessage
                )
            )
        }

        return !hasErrors
    }

    private fun validatePageTwo(stateValue: RegisterUIState): Boolean {
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
        val stateValue = _state.value

        val isValid = validatePageTwo(stateValue)
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            registerUseCase.execute(
                user = User(
                    name = stateValue.name.value,
                    email = stateValue.email.value,
                    age = stateValue.age.value.toInt(),
                    gender = stateValue.gender.lowercase(),
                    problems = stateValue.problems.map { it.lowercase() }
                ),
                password = stateValue.password.value,
                passwordConfirmation = stateValue.passwordConfirmation.value,
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

    fun resetResult() {
        _state.value = _state.value.copy(
            isRegistered = false
        )
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
            passwordConfirmation = stateValue.passwordConfirmation.copy(
                error = null
            ),
            age = stateValue.age.copy(
                error = null
            )
        )
    }
}