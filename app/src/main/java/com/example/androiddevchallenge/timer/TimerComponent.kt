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

import com.example.androiddevchallenge.models.Configuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

enum class TimerState {
    Started,
    Completed,
    NotStarted
}

data class TimerComponentState(
    val secondsRemaining: Int,
    val timerState: TimerState = TimerState.NotStarted
) {
    companion object {
        fun default(configuration: Configuration) =
            TimerComponentState(secondsRemaining = configuration.countdownTimeInSeconds)
    }
}

class TimerComponent(
    private val coroutineScope: CoroutineScope,
    private val configuration: Configuration
) {

    private val _state = MutableStateFlow(TimerComponentState.default(configuration))
    val state: StateFlow<TimerComponentState> = _state

    private val jobs = listOf<Job>()

    fun start() {
        if (jobs.isEmpty()) {
            coroutineScope.launch {
                setupTimer()

                while (isActive && state.value.secondsRemaining > 0) {
                    delay(TimeUnit.SECONDS.toMillis(SECONDS_TO_LOOP))
                    countdownOneSecond()
                }

                completeTimer()
            }
        }
    }

    private suspend fun completeTimer() {
        _state.emit(TimerComponentState(0, TimerState.Completed))
    }

    private suspend fun countdownOneSecond() {
        _state.emit(_state.value.copy(secondsRemaining = _state.value.secondsRemaining - 1))
    }

    private suspend fun setupTimer() {
        _state.emit(TimerComponentState(configuration.countdownTimeInSeconds, TimerState.Started))
    }

    companion object {
        private const val SECONDS_TO_LOOP: Long = 1
    }
}
