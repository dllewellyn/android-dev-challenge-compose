/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dps
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.common.PrimaryButton
import com.example.androiddevchallenge.models.Configuration
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.GlobalScope

@Composable
fun TimerComponent.Render() {
    val state = state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timer") },
            )
        }
    ) {
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
