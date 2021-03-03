package com.example.androiddevchallenge.models

data class Configuration(val countdownTimeInSeconds: Int) {
    companion object {
        fun default() = Configuration(countdownTimeInSeconds = 30)
    }
}