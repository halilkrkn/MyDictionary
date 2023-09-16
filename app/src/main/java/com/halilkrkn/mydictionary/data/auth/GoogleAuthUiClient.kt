package com.halilkrkn.mydictionary.data.auth

import android.content.Intent
import android.content.IntentSender
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.TwitterAuthCredential

interface GoogleAuthUiClient {
    suspend fun signIn(): IntentSender?
    suspend fun signInWithIntent(intent: Intent): SignInResult
    suspend fun signInWithIntentFacebook(loginResult: LoginResult): SignInResult
    suspend fun signInWithIntentTwitterX(): SignInResult
    suspend fun signOut()
    fun getSignedInUser(): UserData?

}