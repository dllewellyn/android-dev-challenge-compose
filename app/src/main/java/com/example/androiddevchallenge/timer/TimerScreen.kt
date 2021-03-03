package com.example.androiddevchallenge.timer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.common.PrimaryButton
import com.example.androiddevchallenge.models.Configuration
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.GlobalScope

@Composable
fun TimerComponent.Render() {
    val state = state.collectAsState()

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Timer") },
        )
    }) {
        Column {
            Surface(color = MaterialTheme.colors.background) {
                when (state.value.timerState) {
                    TimerState.Started -> StartedState(state)
                    TimerState.Completed -> CompletedState(::start)
                    TimerState.NotStarted -> NotStartedState(state, ::start)
                }
            }
        }
    }
}

@Composable
fun CompletedState(start: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.Blue)
        ) {
            Text(
                "Completed!!",
                style = MaterialTheme.typography.h5.copy(color = Color.White),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.basic_padding))
            )
        }
        PrimaryButton(onClick = { start() }, "Restart!")
    }
}

@Composable
private fun StartedState(state: State<TimerComponentState>) {
    SecondsToGoString(state)
}

@Composable
private fun NotStartedState(state: State<TimerComponentState>, start: () -> Unit) {
    Column {
        SecondsToGoString(state)
        PrimaryButton(onClick = { start() }, "Start!")
    }
}

@Composable
private fun SecondsToGoString(state: State<TimerComponentState>) {
    Text(
        stringResource(
            id = R.string.seconds_to_go,
            formatArgs = arrayOf(state.value.secondsRemaining),
        ),
        style = MaterialTheme.typography.h5,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Preview("Complete state")
@Composable
fun PreviewCompletedState() {
    CompletedState(start = {})
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        TimerComponent(GlobalScope, Configuration.default()).Render()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        TimerComponent(GlobalScope, Configuration.default()).Render()
    }
}
