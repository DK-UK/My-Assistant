package com.example.productive._ui

import android.graphics.Paint.Align
import android.graphics.drawable.GradientDrawable
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.media.audiofx.EnvironmentalReverb.Settings
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.productive.R
import com.example.productive.Utility.Utility
import kotlinx.coroutines.delay

private val hours = (0..24).toList()
private val minsAndSecs = (0..60).toList()

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Timer(
    modifier: Modifier = Modifier,
    onShowCountDownTimer: (Int, Int, Int) -> Unit = { hr, min, sec -> },
    onShowTimerScreen: () -> Unit
) {
    var hour by remember {
        mutableStateOf(0)
    }
    var min by remember {
        mutableStateOf(0)
    }
    var sec by remember {
        mutableStateOf(0)
    }

    Surface(modifier = modifier) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                drawTimer(list = hours, { hr ->
                    hour = hr
                })

                drawTimer(list = minsAndSecs, { mins ->
                    min = mins
                })
                drawTimer(list = minsAndSecs, { secs ->
                    sec = secs
                })
            }

            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    modifier = Modifier.clip(CircleShape),
                    onClick = {
                        // redirect to countdown screen
                        onShowCountDownTimer.invoke(0, 0, 2)
                        onShowTimerScreen.invoke()
                    }) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play button")
                }
            }
        }

    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun drawTimer(
    list: List<Int>,
    onSelectedTimeDigit: (Int) -> Unit
) {
    var lazyState = rememberLazyListState(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % list.size)
    var flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyState)
    val firstVisibleItem = lazyState.firstVisibleItemIndex % list.size
    val centerIndex = firstVisibleItem + list.indexOf(list[2])

    Log.e("Dhaval", "drawTimer: firstItem : ${firstVisibleItem} -- centerIndex : ${centerIndex}", )
    LazyColumn(
        state = lazyState,
        flingBehavior = flingBehavior,
        modifier = Modifier
            .height(250.dp)
            .padding(horizontal = 15.dp)
    ) {

        items(Int.MAX_VALUE) {
            val index = it % list.size
            val item = list[index]

            if ((centerIndex) == index) {
                Text(
                    text = Utility.formatTimerDigits(item),
                    style = MaterialTheme.typography.displayMedium
                        .copy(
                            textAlign = TextAlign.Center
                        )
                )
                onSelectedTimeDigit.invoke(item)
            } else {
                Text(
                    text = Utility.formatTimerDigits(item),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun CountDownScreen(
    hours: Int,
    mins: Int,
    secs: Int,
    onTimerStop: () -> Unit
) {

    var totalMillis = (hours * 3600 + mins * 60 + secs) * 1000L
    var pauseTimer by remember {
        mutableStateOf(false)
    }

    var isSilentBtnClicked by remember {
        mutableStateOf(false)
    }

    if (isSilentBtnClicked){
        Timer {

        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Log.e("Dhaval", "HOUR : ${hours} -- MIN : ${mins} -- SECS : ${secs}")
        Surface(
            shadowElevation = 7.dp,
            modifier = Modifier
                .zIndex(5f),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {

            val timer: String by produceState(initialValue = "00:00:00", key1 = pauseTimer) {

                while (totalMillis >= 0L && !pauseTimer) {

                    val remainingHours = totalMillis / 3600000
                    val remainingMinutes = (totalMillis % 3600000) / 60000
                    val remainingSeconds = (totalMillis % 60000) / 1000

                    value = String.format(
                        "%02d:%02d:%02d",
                        remainingHours,
                        remainingMinutes,
                        remainingSeconds
                    )
                    totalMillis -= 1000L
                    delay(1000)
                }
            }


            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Log.e("Dhaval", "TIMER : ${timer}")
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 200.dp)
                    /*.drawBehind {
                        this.drawCircle(
                            style = Fill,
                            radius = this.size.maxDimension / 2,
                            color = Color.Magenta,
                            center = Offset(size.width / 2, size.height / 2)
                        )
                    }*/
                ) {

                    Text(
                        text = timer, style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        text = "Total ${hours} hours ${mins} minutes ${secs} seconds",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxSize()
                ) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 30.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Button(
                            modifier = Modifier.clip(CircleShape),
                            onClick = {
                                // redirect to timer screen
                                onTimerStop.invoke()
                            }) {
                            Icon(
                                painterResource(id = R.drawable.ic_stop),
                                contentDescription = "Stop button"
                            )
                        }
                        Button(
                            modifier = Modifier.clip(CircleShape),
                            onClick = {
                                // pause the timer
                                pauseTimer = !pauseTimer
                            }) {
                            Icon(
                                painterResource(id = if (pauseTimer) R.drawable.ic_play else R.drawable.ic_pause),
                                contentDescription = "Play button"
                            )
                        }
                    }
                }
            }

            if(timer == "00:00:00"){
                // show time out screen
                timeOutScreen {
                    onTimerStop.invoke()
                }
            }
        }
    }
}

@Composable
fun timeOutScreen(
    modifier : Modifier = Modifier,
    isSilentBtnClicked : () -> Unit
) {
    val context = LocalContext.current

    /*val size = 180
    val alarmSize by produceState(initialValue = size){
        while(size == (size / 2 + 50)){
            value = value - 10
            delay(200)
        }
        while (value == size){
            value = value + 10
            delay(200)
        }
    }*/

    // Sound effect for time out


    DisposableEffect(Unit){
        var ringtone = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE)
        var mediaPlayer = MediaPlayer.create(context, ringtone)
        mediaPlayer.start()
        onDispose {
            ringtone = null
            mediaPlayer.release()
        }
    }
    Surface(
        modifier = modifier.fillMaxSize()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 100.dp)
        ) {

            Image(painterResource(id = R.drawable.ic_alarm), contentDescription = "Alarm",
                modifier = Modifier
                    .size(180.dp)
                    .alpha(0.8f)
                    .rotate(0f)
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            Button(onClick = isSilentBtnClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp)
            ) {
                Text(text = "Silent",
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun prevTimer() {
   /* Timer(onShowTimerScreen = {

    })
    CountDownScreen(hours = 12, mins = 5, secs = 45,
        {

        })*/
    timeOutScreen {

    }
}