package com.example.androiddevchallenge.configure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.ui.theme.MyTheme
import kotlinx.coroutines.GlobalScope

@Composable
fun ConfigurationComponent.Render() {
    val state = state.collectAsState()

    Surface(color = MaterialTheme.colors.background) {
        Column {
            ConfigurationTitle()

            CurrentConfigurationSeconds(state)
            ConfigurationSecondsSlider(state, ::updateTimer)

            Button(onClick = ::onSelectedStart) {
                Text("Start!")
            }
        }
    }
}

@Composable
private fun ConfigurationSecondsSlider(
    state: State<ConfigurationComponentState>,
    valueChanged: (Float) -> Unit
) {
    Slider(
        value = state.value.selectedTime,
        onValueChange = { valueChanged(it) },
        valueRange = 0f..60f,
        steps = 60
    )
}

@Composable
private fun CurrentConfigurationSeconds(state: State<ConfigurationComponentState>) {
    Text(
        stringResource(
            id = R.string.seconds,
            formatArgs = arrayOf(state.value.selectedTime.toInt())
        )
    )
}

@Composable
private fun ConfigurationTitle() {
    Text(
        stringResource(id = R.string.time_configuration_question),
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.basic_padding))
    )
}


@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        ConfigurationComponent(GlobalScope) {}.Render()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        ConfigurationComponent(GlobalScope) {}.Render()
    }
}
