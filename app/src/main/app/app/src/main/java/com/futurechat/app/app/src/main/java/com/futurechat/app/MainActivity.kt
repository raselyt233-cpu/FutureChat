package com.futurechat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FutureChatApp()
        }
    }
}

@Composable
fun FutureChatApp() {
    var phone by remember { mutableStateOf("") }
    var loggedIn by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<String>() }

    MaterialTheme {
        if (!loggedIn) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("FutureChat", style = MaterialTheme.typography.headlineLarge)
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") }
                )
                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = { if (phone.isNotBlank()) loggedIn = true }
                ) {
                    Text("Continue")
                }
                Text("Demo login only — OTP is not connected")
            }
        } else {
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Text("FutureChat", style = MaterialTheme.typography.headlineLarge)
                Text("Welcome: $phone")
                Text("Messages (local demo)")
                LazyColumn(Modifier.weight(1f)) {
                    items(messages) { item ->
                        Text(item, modifier = Modifier.padding(8.dp))
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.weight(1f),
                        label = { Text("Write a message") }
                    )
                    Button(onClick = {
                        if (message.isNotBlank()) {
                            messages.add(message)
                            message = ""
                        }
                    }) {
                        Text("Send")
                    }
                }
            }
        }
    }
}
