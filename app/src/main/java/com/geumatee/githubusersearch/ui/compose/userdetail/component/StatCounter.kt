package com.geumatee.githubusersearch.ui.compose.userdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geumatee.githubusersearch.ui.compose.userdetail.formatCommaSeparate

@Composable
internal fun StatCounter(label: String, value: Int?, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(value.formatCommaSeparate())
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
private fun StatCounterPreview() {
    StatCounter(label = "Followers", value = 46292)
}