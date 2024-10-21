package com.geumatee.githubusersearch.ui.compose.userdetail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.geumatee.githubusersearch.R

@Composable
internal fun BackIcon(navigateBack: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = navigateBack, modifier = modifier) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BackIconPreview() {
    BackIcon(
        navigateBack = {},
    )
}