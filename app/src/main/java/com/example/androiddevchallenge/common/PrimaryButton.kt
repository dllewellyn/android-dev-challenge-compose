package com.example.androiddevchallenge.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.R

@Composable
fun PrimaryButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick, modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(id = R.dimen.basic_padding))
    ) {
        Text(text, style = MaterialTheme.typography.button)
    }
}

@Composable
@Preview
fun PreviewPrimaryButton() {
    PrimaryButton(onClick = {}, text = "Hello world")
}