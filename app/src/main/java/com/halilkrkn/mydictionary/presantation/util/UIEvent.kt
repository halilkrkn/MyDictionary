package com.halilkrkn.mydictionary.presantation.util

sealed class UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent()
}
