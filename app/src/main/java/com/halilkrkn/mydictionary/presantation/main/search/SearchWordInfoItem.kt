package com.halilkrkn.mydictionary.presantation.main.search

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import com.halilkrkn.mydictionary.R
import com.halilkrkn.mydictionary.domain.model.Definition
import com.halilkrkn.mydictionary.domain.model.Meaning
import com.halilkrkn.mydictionary.domain.model.WordInfo

@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = wordInfo.word,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            PopUpSoundPlay()

        }

//        wordInfo.phonetics?.forEach {
//            Text(text = it.text, fontWeight = FontWeight.Light)
//            Log.d("Phonetics", "WordInfoItem: ${it.text}")
//            Log.d("Phonetics", "WordInfoItem: ${it.audio}")
//            Text(text = it.audio, fontWeight = FontWeight.Light)
//        }
        Spacer(modifier = Modifier.height(16.dp))

        wordInfo.meanings.forEach { meaning ->
            Text(text = meaning.partOfSpeech, fontWeight = FontWeight.Bold)
            meaning.definitions.forEachIndexed { i, definition ->
                Text(text = "${i + 1}. ${definition.definition}")
                Spacer(modifier = Modifier.height(8.dp))
                definition.example?.let { example ->
                    Text(text = "Example: $example", fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun PopUpSoundPlay() {
    var isProfilePopupVisible by remember { mutableStateOf(false) }
    var isIconVisible by remember { mutableStateOf(true) }
    val audioUriUK = "https://api.dictionaryapi.dev/media/pronunciations/en/father-us.mp3"
    val audioUriUS = "https://api.dictionaryapi.dev/media/pronunciations/en/father-uk.mp3"

    IconButton(
        onClick = { /*TODO*/
            isProfilePopupVisible = !isProfilePopupVisible
            isIconVisible = !isIconVisible
        }) {
        if (isIconVisible) {
            AsyncImage(
                model = Image(imageVector = Icons.Default.PlayArrow, contentDescription = ""),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(24.dp)
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
        }
    }
    // Kullanıcı profili popup'ı
    if (isProfilePopupVisible) {
        Popup(
            alignment = Alignment.CenterEnd,
            offset = IntOffset(0, 0),
            onDismissRequest = {
                isProfilePopupVisible = false
                isIconVisible = true
            },
            properties = PopupProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {

            Row(
                modifier = Modifier
                    .wrapContentSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp),
            ) {
                IconButton(onClick = {
                    meadiaSoundPlayer(sound = audioUriUK)
                }) {
                    AsyncImage(
                        model = R.drawable.uk_96,
                        contentDescription = "uk logo",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }


                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = {
                    meadiaSoundPlayer(sound = audioUriUS)
                }) {
                    AsyncImage(
                        model = R.drawable.us_96,
                        contentDescription = "us logo",
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
fun meadiaSoundPlayer(sound: String) {
    val mediaPlayer: MediaPlayer = MediaPlayer()

    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .build()
    )
    try {
        mediaPlayer.setDataSource(sound)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener { mp ->
            mp.start()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
//
    mediaPlayer.setOnCompletionListener { mp ->
        mp.reset()
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WordInfoItemPreview(
    wordInfo: WordInfo = WordInfo(
        meanings = listOf(
            Meaning(
                definitions = listOf(
                    Definition(
                        definition = "Lorem impsum hell brooo example brooo",
                        example = "Lorem impsum hell brooo example brooo",
                        antonyms = listOf(""),
                        synonyms = listOf(""),

                        )
                ),
                partOfSpeech = ""
            )
        ),
        word = "example"
    ),
) {
    WordInfoItem(wordInfo = wordInfo)
}


