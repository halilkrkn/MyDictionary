package com.halilkrkn.mydictionary.presantation.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.halilkrkn.mydictionary.R
import com.halilkrkn.mydictionary.navigation.screens.AuthScreen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    navController: NavController,
    splashViewModel: SplashViewModel = hiltViewModel(),
)
{
//    var startAnimation by remember {
//        mutableStateOf(false)
//    }

    val startAnimation = splashViewModel.isLoading.collectAsState()

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation.value) 1f else 0.25f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearEasing
        ),
        label = "MyDictionary"
    )

    LaunchedEffect(key1 = true) {
        startAnimation.value
        delay(6000)
        navController.popBackStack()
        navController.navigate(AuthScreen.SignIn.route)
    }

    SplashScreen(alpha = alphaAnim.value)
}

@Composable
fun SplashScreen(alpha: Float) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_lmy2n5is)
    )
    var isCompleted by remember { mutableStateOf(false) }

    val clipSpecs = LottieClipSpec.Progress(
        min = 0.0f,
        max = if (isCompleted) 0.44f else 0.282f
    )

    var isPlaying by remember {
        mutableStateOf(false)
    }

    // render the animation


    Box(
        modifier = Modifier
            .background(if (isSystemInDarkTheme()) Color.Black else Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            modifier = Modifier.size(size = 360.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
        )

//        Image(
//            modifier = Modifier
//                .size(180.dp)
//                .alpha(alpha),
//            painter = painterResource(id = R.drawable.ic_language_2),
//            contentDescription = "splash icon"
//        )
    }
}