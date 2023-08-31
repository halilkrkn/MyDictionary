package com.halilkrkn.mydictionary.common

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClient

sealed class GoogleAuthUiClients {
    class GoogleAuthUiClient(private val context: Context): GoogleAuthUiClients() {
        val googleAuthUiClient by lazy {
            GoogleAuthUiClient(
                context = context,
                oneTapClient = Identity.getSignInClient(context),
            )
        }
    }

}

