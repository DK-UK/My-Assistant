package com.example.productive._ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.productive.Utility.Utility
import kotlinx.coroutines.delay
import java.util.Calendar

private val hours = (0..24).toList()
private val minsAndSecs = (0..60).toList()

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Timer(
    modifier: Modifier = Modifier
) {

    Surface(modifier = modifier) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            drawTimer(list = hours, selectedTimeDigit = {

            })
            drawTimer(list = minsAndSecs, selectedTimeDigit = {

            })
            drawTimer(list = minsAndSecs, selectedTimeDigit = {

            })

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun drawTimer(
    list : List<Int>,
    selectedTimeDigit : (Int) -> Unit
) {
    var lazyState = rememberLazyListState(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % list.size)
    var flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyState)

    LazyColumn(
        state = lazyState,
        flingBehavior = flingBehavior,
        modifier = Modifier
            .height(300.dp)
            .padding(horizontal = 15.dp)
    ){

        items(Int.MAX_VALUE){
            val index = it % list.size
            val item = list[index]

            Log.e("Dhaval", "drawTimer: index : ${index}", )
            if (-1 == index) {
                Text(
                    text = Utility.formatTimerDigits(item),
                    style = MaterialTheme.typography.displayMedium
                )
            }else{
                Text(
                    text = Utility.formatTimerDigits(item),
                    style = MaterialTheme.typography.displaySmall,
                    color = Color.LightGray
                )
            }
        }
    }
}
@Preview
@Composable
fun prevTimer() {
    Timer()
}