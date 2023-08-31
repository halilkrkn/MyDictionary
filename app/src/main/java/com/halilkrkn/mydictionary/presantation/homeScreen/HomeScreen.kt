package com.halilkrkn.mydictionary.presantation.homeScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClient
import com.halilkrkn.mydictionary.data.auth.UserData
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController
) {
    val context = LocalContext.current.applicationContext
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope

    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context),
        )
    }

    SignOut(
        userData = googleAuthUiClient.getSignedInUser(),
        onSignOut = {
            lifecycleScope.launch {
                googleAuthUiClient.signOut()
                Toast.makeText(
                    context,
                    "Signed Out",
                    Toast.LENGTH_LONG
                ).show()
                navController.popBackStack()
            }
        }
    )
}

@Composable
fun SignOut(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (userData?.email != null) {
            Text(
                text = userData.email,
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            onClick = onSignOut,
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
        ) {
            Text(text = "Sign Out")
        }
    }
}