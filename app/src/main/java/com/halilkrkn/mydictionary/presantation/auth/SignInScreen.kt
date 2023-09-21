package com.halilkrkn.mydictionary.presantation.auth

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.halilkrkn.mydictionary.R
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClientImpl
import com.halilkrkn.mydictionary.navigation.screens.BottomBarScreen
import com.halilkrkn.mydictionary.navigation.util.Graphs.HOME
import com.halilkrkn.mydictionary.ui.theme.FacebookColor
import com.halilkrkn.mydictionary.ui.theme.GoogleGreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val context = LocalContext.current.applicationContext
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val loginManager = LoginManager.getInstance()
    val callbackManager = remember { CallbackManager.Factory.create() }
    val state = viewModel.state.collectAsStateWithLifecycle()
    val firebaseAuth = FirebaseAuth.getInstance()

    val googleAuthUiClient by lazy {
        GoogleAuthUiClientImpl(
            context = context,
            oneTapClient = Identity.getSignInClient(context),
            auth = firebaseAuth
        )
    }

    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
//            navController.popBackStack()
            navController.navigate(HOME)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent2(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )
    val launcherFacebook = rememberLauncherForActivityResult(
        contract = loginManager.createLogInActivityResultContract(callbackManager, null)
    ) {
        // nothing to do. handled in FacebookCallback
    }


    DisposableEffect(Unit) {
        loginManager.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {
                // do nothing
                Toast.makeText(context, "Login canceled!", Toast.LENGTH_LONG).show()
            }

            override fun onError(error: FacebookException) {
                Log.e("Login", error.message ?: "Unknown error")
                Toast.makeText(
                    context,
                    "Login failed with errors!",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onSuccess(result: LoginResult) {
                lifecycleScope.launch {
                    val result = googleAuthUiClient.signInWithIntentFacebook(result)
                    viewModel.onSignInResult(result)
                }
            }
        })
        onDispose {
            loginManager.unregisterCallback(callbackManager)
        }
    }

    LaunchedEffect(key1 = state.value.isSingInSuccessful) {
        if (state.value.isSingInSuccessful) {
            Toast.makeText(
                context,
                "Sign In Successful",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
            navController.navigate(HOME)
            viewModel.resetSignInState()
        }
    }

    SignInButton(
        state = state.value,
        onSignInClick = {
            lifecycleScope.launch {
                withContext(Dispatchers.Default) {
                    val signInIntentSender = googleAuthUiClient.googleSingInClient()
                    launcher.launch(
                        signInIntentSender
                    )
                }
            }
        },
        onSignInClickFB = {
            lifecycleScope.launch {
                withContext(Dispatchers.Default) {
                    launcherFacebook.launch(listOf("email", "public_profile"))
                }
            }
        },
        onSignInClickX = {
            lifecycleScope.launch {
                withContext(Dispatchers.Default) {
                    val result = googleAuthUiClient.signInWithIntentTwitterX()
                    viewModel.onSignInResult(result = result)
                }
            }
            Toast.makeText(
                context,
                "Sign In with X",
                Toast.LENGTH_SHORT
            ).show()
        }
    )
}

@Composable
fun SignInButton(
    state: SignInState,
    onSignInClick: () -> Unit,
    onSignInClickFB: () -> Unit,
    onSignInClickX: () -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error ?: "Error",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = painterResource(id = R.drawable.dictionary_logo),
            contentDescription = "Google Icon",
            contentScale = ContentScale.FillWidth
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            CustomButton(
                onClick = { onSignInClick() },
                text = "Sign In with Google",
                loadingText = "Signing Up...",
                iconContentDescription = "Google Icon",
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = GoogleGreen,
                    contentColor = Color.White
                ),
                icon = painterResource(id = R.drawable.icons8_google_logo_48),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                onClick = { onSignInClickFB() },
                text = "Sign In with Facebook",
                loadingText = "Signing Up...",
                iconContentDescription = "Facebook Icon",
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = FacebookColor,
                    contentColor = Color.White
                ),
                icon = painterResource(id = R.drawable.icons8_facebook_48),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                onClick = { onSignInClickX() },
                text = "Sign In with X",
                loadingText = "Signing Up...",
                iconContentDescription = "X Icon",
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                icon = painterResource(id = R.drawable.twitter_x_logo_48),
                clickProgressBarColor = Color.Black
            )
        }
    }
}


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    loadingText: String,
    iconContentDescription: String,
    shape: Shape = CircleShape,
    borderColor: Color = Color.Green,
    clickBorderColor: Color = Color.Red,
    clickProgressBarColor: Color = Color.Yellow,
    buttonColors: ButtonColors,
    icon: Painter,
) {

    var clicked by remember { mutableStateOf(false) }

    // Google ile giriş yapma düğmesi
    Button(
        onClick = {
            // Google ile giriş işlemleri yapılabilir.
            clicked = !clicked
            onClick()
        },
        shape = shape,
        border = BorderStroke(
            width = 2.dp,
            color = if (clicked) borderColor else clickBorderColor,
        ),
        colors = buttonColors
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = FastOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = icon,
                contentDescription = iconContentDescription,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(text = if (clicked) loadingText else text)
            Spacer(modifier = Modifier.width(8.dp))
            if (clicked) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(24.dp)
                        .width(24.dp),
                    strokeWidth = 2.dp,
                    color = clickProgressBarColor
                )
            }
        }
    }
}
