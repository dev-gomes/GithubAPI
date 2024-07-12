package com.example.githubapi.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.githubapi.R
import com.example.githubapi.ui.theme.Dimensions

@Composable
fun ErrorScreen() {
    Text(
        text = stringResource(R.string.something_went_wrong),
        modifier = Modifier
            .padding(Dimensions.mediumPadding)
            .fillMaxSize()
            .wrapContentSize()
    )
}
