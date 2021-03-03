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
package com.example.androiddevchallenge.configure

import com.example.androiddevchallenge.models.Configuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConfigurationComponent(
    private val coroutineScope: CoroutineScope,
    private val selectedStart: (Configuration) -> Unit
) {

    private val _state = MutableStateFlow(ConfigurationComponentState.default())
    val state: StateFlow<ConfigurationComponentState> = _state

    fun updateTimer(newTime: Float) = coroutineScope.launch {
        _state.emit(_state.value.copy(selectedTime = newTime))
    }

    fun onSelectedStart() = selectedStart(state.value.toConfiguration())
}

fun ConfigurationComponentState.toConfiguration() =
    Configuration(countdownTimeInSeconds = selectedTime.toInt())
