package com.halilkrkn.mydictionary.presantation.signInScreen

import android.app.Activity.RESULT_OK
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.Identity
import com.halilkrkn.mydictionary.R
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClient
import com.halilkrkn.mydictionary.navigation.Screens
import com.halilkrkn.mydictionary.ui.theme.FacebookColor
import com.halilkrkn.mydictionary.ui.theme.Shapes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignInScreen(
    navController: NavController,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val context = LocalContext.current.applicationContext
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context),
        )
    }

    val state = viewModel.state.collectAsStateWithLifecycle()
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            navController.navigate(Screens.Home.route)
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.value.isSingInSuccessful) {
        if (state.value.isSingInSuccessful) {
            Toast.makeText(
                context,
                "Sign In Successful",
                Toast.LENGTH_SHORT
            ).show()
            navController.navigate(Screens.Home.route)
            viewModel.resetSignInState()
        }
    }

    SignInButton(
        state = state.value,
        onSignInClick = {
            lifecycleScope.launch {
                withContext(Dispatchers.Default) {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@withContext
                        ).build()
                    )
                }
            }
        }
    )
}

@Composable
fun SignInButton(
    state: SignInState,
    onSignInClick: () -> Unit,
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
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.dictionary_logo),
            contentDescription = "Google Icon",
            modifier = Modifier.size(450.dp)
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
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                icon = painterResource(id = R.drawable.icons8_google_logo_48),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(
                onClick = { /*TODO*/ },
                text = "Sign In with Facebook",
                loadingText = "Signing Up...",
                iconContentDescription = "Facebook Icon",
                buttonColors = ButtonDefaults.buttonColors(
                    containerColor = FacebookColor,
                    contentColor = Color.White
                ),
                icon = painterResource(id = R.drawable.icons8_facebook_48),
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
            horizontalArrangement = Arrangement.SpaceAround
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