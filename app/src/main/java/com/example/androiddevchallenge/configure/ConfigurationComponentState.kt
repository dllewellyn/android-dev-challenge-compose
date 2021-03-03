package com.example.androiddevchallenge.configure

data class ConfigurationComponentState(val selectedTime: Float) {
    companion object {
        fun default() = ConfigurationComponentState(selectedTime = 30f)
    }
}