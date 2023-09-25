package com.halilkrkn.mydictionary.presantation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel(){

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        viewModelScope.launch {
            delay(500)
            _isLoading.value = true
        }
    }

}

//private val _isLoading = mutableStateOf<Boolean>(false)
//val isLoading: State<Boolean> = _isLoading
//
//init {
//    viewModelScope.launch {
//        delay(1000)
//        _isLoading.value = true
//    }
//}