package com.halilkrkn.mydictionary.common

import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClientImpl

sealed class GoogleAuthUiClients {
    class GoogleAuthUiClient(private val context: Context): GoogleAuthUiClients() {
        val googleAuthUiClient by lazy {
            GoogleAuthUiClientImpl(
                context = context,
                oneTapClient = Identity.getSignInClient(context),
                auth = FirebaseAuth.getInstance()
            )
        }
    }

}

