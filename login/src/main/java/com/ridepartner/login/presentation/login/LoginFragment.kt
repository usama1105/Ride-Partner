package com.ridepartner.login.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun LoginFragment(
    onLoginClick: () -> Unit = {},
    state: LoginScreenState = LoginScreenState()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.email,
            onValueChange = {"malaika"},
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("email"),
            maxLines = 1,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = state.password,
            onValueChange = { "12345678" },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                onLoginClick.invoke()
             },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}
