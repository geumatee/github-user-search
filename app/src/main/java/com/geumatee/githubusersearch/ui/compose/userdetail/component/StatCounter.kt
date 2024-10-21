package com.geumatee.githubusersearch.ui.compose.userdetail.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.geumatee.githubusersearch.ui.compose.userdetail.formatCommaSeparate

@Composable
internal fun StatCounter(label: String, value: Int?, modifier: Modifier = Modifier) {
    var targetValue by remember { mutableIntStateOf(0) }
    val valueCounter by animateIntAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing,
        ),
        label = "${label}Counter",
    )
    LaunchedEffect(value) {
        targetValue = value ?: 0
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(valueCounter.formatCommaSeparate())
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
private fun StatCounterPreview() {
    StatCounter(label = "Followers", value = 46292)
}