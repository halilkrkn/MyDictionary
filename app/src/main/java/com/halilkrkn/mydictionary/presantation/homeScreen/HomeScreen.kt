package com.halilkrkn.mydictionary.presantation.homeScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.halilkrkn.mydictionary.data.auth.GoogleAuthUiClientImpl
import com.halilkrkn.mydictionary.data.auth.UserData
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val context = LocalContext.current.applicationContext
    val lifecycleScope = LocalLifecycleOwner.current.lifecycleScope
    val firebaseAuth = FirebaseAuth.getInstance()

    val googleAuthUiClient by lazy {
        GoogleAuthUiClientImpl(
            context = context,
            oneTapClient = Identity.getSignInClient(context),
            auth = firebaseAuth
        )
    }

    SignOutPersonTabBar(
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignOutPersonTabBar(
    userData: UserData?,
    onSignOut: () -> Unit,
) {
//    var searchText by remember { mutableStateOf("") }
    var isProfilePopupVisible by remember { mutableStateOf(false) }
//    val keyboardController = LocalSoftwareKeyboardController.current


    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                TopAppBar(
                    title = { Text(text = "MyDictionary") },
                    actions = {
                        IconButton(
                            onClick = {
                                isProfilePopupVisible = !isProfilePopupVisible
                            }
                        ) {
                            if (userData?.profilePictureUrl != null) {
                                AsyncImage(
                                    model = userData.profilePictureUrl,
                                    contentDescription = "Profile Picture",
                                    modifier = Modifier
                                        .size(36.dp)
                                        .border(
                                            border = BorderStroke(
                                                width = 1.dp,
                                                color = Color.DarkGray,
                                            ),
                                            shape = CircleShape
                                        )
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile Picture",
                                    tint = Color.Red
                                )
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )

                /*        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = searchText,
                            onValueChange = {
                                searchText = it
                                // Arama işlemi burada yapılabilir
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Text
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                    // Arama işlemi burada yapılabilir
                                }
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            singleLine = true,
                            placeholder = {
                                Text(text = "Ara...")
                            }
                        )*/

                Spacer(modifier = Modifier.height(16.dp))

                // Kullanıcı profili popup'ı
                if (isProfilePopupVisible) {
                    Popup(
                        alignment = Alignment.TopEnd,
                        offset = IntOffset(-24, 96),
                        properties = PopupProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(MaterialTheme.colorScheme.background)
                                .border(
                                    border = BorderStroke(
                                        width = 2.dp,
                                        color = Color.DarkGray,
                                    ),
                                    shape = RoundedCornerShape(36.dp)
                                )
                                .clip(RoundedCornerShape(36.dp))
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            SignOut(
                                userData = userData,
                                onSignOut = onSignOut,
                                isProfilePopupVisible = isProfilePopupVisible
                            )
                        }
                    }
                }
            }
        }
    ) {}
}

@Composable
fun SignOut(
    userData: UserData?,
    onSignOut: () -> Unit,
    isProfilePopupVisible: Boolean
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (userData?.profilePictureUrl != null) {
            AsyncImage(
                model = userData.profilePictureUrl,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(48.dp)
                    .border(
                        border = BorderStroke(
                            width = 1.dp,
                            color = Color.DarkGray,
                        ),
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (userData?.username != null) {
            Text(
                text = userData.username,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (userData?.email != null) {
            Text(
                text = userData.email,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            onClick = {
                // Çıkış işlemi burada yapılabilir
                isProfilePopupVisible
                onSignOut()
            }
        ) {
            Text(text = "Çıkış Yap")
        }
    }
}
