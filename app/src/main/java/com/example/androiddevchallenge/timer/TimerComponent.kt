package com.example.androiddevchallenge.timer

import com.example.androiddevchallenge.models.Configuration
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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