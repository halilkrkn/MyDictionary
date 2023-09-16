package com.halilkrkn.mydictionary.di

import android.app.Activity
import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.signin.internal.SignInClientImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.TwitterAuthProvider
import com.google.firebase.ktx.Firebase
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClient
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClientImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideSignInClient(@ApplicationContext context: Context) =
        Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun provideGoogleAuthUiClient(
        @ApplicationContext context: Context,
        signInClient: SignInClient,
        firebaseAuth: FirebaseAuth,
    ): GoogleAuthUiClient {
        return GoogleAuthUiClientImpl(context, signInClient, firebaseAuth)
    }


}