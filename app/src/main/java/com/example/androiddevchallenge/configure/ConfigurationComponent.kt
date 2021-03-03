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