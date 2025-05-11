package com.lans.sleep_care.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lans.sleep_care.data.Resource
import com.lans.sleep_care.domain.usecase.auth.CheckAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkAuthStatusUseCase: CheckAuthStatusUseCase
) : ViewModel() {
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: Flow<Boolean?> get() = _isAuthenticated
    var splashState by mutableStateOf(true)

    init {
        viewModelScope.launch {
            checkAuthStatusUseCase.execute().collect { response ->
                when (response) {
                    is Resource.Success -> {
                        _isAuthenticated.value = response.data
                    }

                    is Resource.Error -> {
                        _isAuthenticated.value = false
                    }

                    else -> Unit
                }
                splashState = false
            }
        }
    }
}