package com.lans.sleep_care.presentation.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.model.auth.User
import com.lans.sleep_care.domain.usecase.logbook.GetProblemsUseCase
import com.lans.sleep_care.domain.usecase.user.UpdateProfileUseCase
import com.lans.sleep_care.domain.usecase.validator.ValidatorUseCase
import com.lans.sleep_care.utils.capitalize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val validatorUseCase: ValidatorUseCase,
    private val getProblemsUseCase: GetProblemsUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val _state = mutableStateOf(ProfileUIState())
    val state: State<ProfileUIState> get() = _state

    fun onEvent(event: ProfileUIEvent) {
        when (event) {
            is ProfileUIEvent.NameChanged -> {
                _state.value = _state.value.copy(
                    name = _state.value.name.copy(
                        value = event.name
                    )
                )
            }

            is ProfileUIEvent.AgeChanged -> {
                if (event.age.all { it.isDigit() }) {
                    _state.value = _state.value.copy(
                        age = _state.value.age.copy(
                            value = event.age
                        )
                    )
                }
            }

            is ProfileUIEvent.ToggleProblem -> {
                val updatedProblems = _state.value.problems.toMutableList()
                if (updatedProblems.contains(event.problem)) {
                    updatedProblems.remove(event.problem)
                } else {
                    updatedProblems.add(event.problem)
                }
                _state.value = _state.value.copy(problems = updatedProblems)
            }

            is ProfileUIEvent.SaveButtonClicked -> {
                updateProfile()
            }
        }
    }

    fun initializeData(
        id: Int,
        name: String,
        age: String,
        problems: List<String>
    ) {
        _state.value = _state.value.copy(
            id = id,
            name = _state.value.name.copy(value = name),
            age = _state.value.age.copy(value = age)
        )
        loadProblems()
        _state.value.problems.addAll(problems.map {
            it.replace("_", " ").capitalize()
        })
    }

    private fun loadProblems() {
        viewModelScope.launch {
            getProblemsUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            availableProblems = response.data.map {
                                it.replace("_", " ").capitalize()
                            }.toMutableList(),
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
                        _state.value = _state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun validate(stateValue: ProfileUIState): Boolean {
        val nameResult = validatorUseCase.name.execute(stateValue.name.value)
        val ageResult = validatorUseCase.age.execute(stateValue.age.value)

        val hasErrors = listOf(
            nameResult,
            ageResult
        ).any { !it.isSuccess }

        if (hasErrors) {
            _state.value = stateValue.copy(
                name = stateValue.name.copy(
                    error = nameResult.errorMessage
                ),
                age = stateValue.age.copy(
                    error = ageResult.errorMessage
                )
            )
        }

        return !hasErrors
    }

    private fun updateProfile() {
        val stateValue = _state.value

        val isValid = validate(stateValue)
        if (!isValid) {
            return
        }

        viewModelScope.launch {
            updateProfileUseCase.execute(
                User(
                    id = stateValue.id,
                    name = stateValue.name.value.trim(),
                    age = stateValue.age.value.toInt(),
                    problems = stateValue.problems.map {
                        it.replace(" ", "_").lowercase()
                    }
                )
            ).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isProfileUpdated = response.data,
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
