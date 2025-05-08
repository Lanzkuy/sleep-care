package com.lans.sleep_care.presentation.screen.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(

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

            is ProfileUIEvent.EmailChanged -> {
                _state.value = _state.value.copy(
                    email = _state.value.email.copy(
                        value = event.email
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

            is ProfileUIEvent.GenderSelected -> {
                _state.value = _state.value.copy(
                    gender = event.gender
                )
            }

            is ProfileUIEvent.ProblemChange -> {
                _state.value = _state.value.copy(
                    problem = event.problem
                )
            }

            is ProfileUIEvent.AddProblemButtonClicked -> {
                val problem = _state.value.problem.lowercase()
                if (problem.isNotBlank()) {
                    val updatedList = _state.value.problems.toMutableList().apply {
                        if (!_state.value.problems.contains(problem)) {
                            add(problem.lowercase())
                        }
                    }
                    _state.value = _state.value.copy(
                        problems = updatedList,
                        problem = ""
                    )
                }
            }

            is ProfileUIEvent.SaveButtonClicked -> {
                updateProfile()
            }
        }
    }

    private fun updateProfile() {
        viewModelFactory {

        }
    }
}
