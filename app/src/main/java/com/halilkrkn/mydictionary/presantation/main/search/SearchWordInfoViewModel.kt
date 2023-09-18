package com.halilkrkn.mydictionary.presantation.main.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.halilkrkn.mydictionary.core.util.Resource
import com.halilkrkn.mydictionary.domain.usecase.GetWordInfoUseCase
import com.halilkrkn.mydictionary.presantation.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchWordInfoViewModel @Inject constructor(
    private val getWordInfoUseCase: GetWordInfoUseCase,
) : ViewModel() {

    private val _searchQuery = mutableStateOf<String>("")
    val searchQuery: State<String> = _searchQuery

    private val _state = MutableStateFlow<SearchWordInfoState>(SearchWordInfoState())
    val state: StateFlow<SearchWordInfoState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null
    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfoUseCase(word = query)
                .onEach { result ->
                    when (result) {
                        is Resource.Success -> {
                           _state.update {  searchWordInfoState ->
                               searchWordInfoState.copy(
                                   wordInfoItems = result.data,
                                   isLoading = false
                               )
                           }
                        }

                        is Resource.Error -> {
                            _state.update { searchWordInfoState ->
                                searchWordInfoState.copy(
                                    wordInfoItems = result.data,
                                    isLoading = false
                                )
                            }
                            _eventFlow.emit(UIEvent.ShowSnackbar(
                                result.message ?: "Unknown Error"
                            ))
                            // Yukarıdaki ile aynı
/*                            _state.value = state.value.copy(
                                result.data,
                                isLoading = false
                            )*/
                        }

                        is Resource.Loading -> {
                            _state.update { searchWordInfoState ->
                                searchWordInfoState.copy(
                                    wordInfoItems = result.data,
                                    isLoading = true
                                )
                            }
                        }
                    }
                }.launchIn(this)

        }
    }
}