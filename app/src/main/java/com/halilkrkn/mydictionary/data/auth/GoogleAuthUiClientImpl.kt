package com.halilkrkn.mydictionary.data.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Parcel
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthCredential
import com.google.firebase.auth.OAuthProvider
import com.google.firebase.auth.PlayGamesAuthCredential
import com.google.firebase.auth.TwitterAuthCredential
import com.google.firebase.auth.TwitterAuthProvider
import com.halilkrkn.mydictionary.R
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.atanh


class GoogleAuthUiClientImpl @Inject constructor(
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth,
): GoogleAuthUiClient {
//    var oAuthCredential: OAuthCredential? = null
    // Kullanıcıyı Google hesabı üzerinden oturum açma işlemine yönlendiren fonksiyon. Bu işlem, tek dokunuşla oturum açma istemcisi aracılığıyla gerçekleştirilir.
    override suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }


    //Google hesabı üzerinden gelen oturum açma isteğini işleyen fonksiyon.
    override suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        email = email,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    override suspend fun signInWithIntentFacebook(loginResult: LoginResult): SignInResult {
        val token = loginResult.accessToken.token
        val credential = FacebookAuthProvider.getCredential(token)
        val authResult = auth.signInWithCredential(credential).await()
        return try {
            val user = authResult.user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        email = email,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    override suspend fun signInWithIntentTwitterX(): SignInResult {

        val token = context.getString(R.string.twitter_id_key)
        val secret = context.getString(R.string.twitter_secret_token)
        val credential = TwitterAuthProvider.getCredential(token,secret)
        val authResult = auth.signInWithCredential(credential).await()
        return try {
            val user = authResult.user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        email = email,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    // Kullanıcıyı oturumdan çıkış yapmaya yönlendiren fonksiyon.
    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    // Mevcut oturum açmış kullanıcının bilgilerini döndüren fonksiyon.
    override fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            email = email,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    // Tek dokunuşla oturum açma isteği için yapılandırmayı oluşturan fonksiyon.
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun  googleBuildSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

    }


    // OTHER GOOGLE SIGN IN TECHNIC

    suspend fun signInWithIntent2(intent: Intent): SignInResult {
        val credential = GoogleSignIn.getSignedInAccountFromIntent(intent)
        val googleIdToken = credential.getResult(ApiException::class.java)?.idToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        email = email,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    fun googleSingInClient(): Intent {
        val gso = googleBuildSignInOptions()
        return GoogleSignIn.getClient(context,gso).signInIntent
    }


}