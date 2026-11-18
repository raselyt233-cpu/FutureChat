package com.futurechat.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { FutureChatApp() } }
    }
}

private data class DemoChat(val name: String, val preview: String, val initials: String, val time: String)

@Composable
private fun FutureChatApp() {
    var phone by remember { mutableStateOf("") }
    var enteredDemo by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }

    if (!enteredDemo) {
        LoginScreen(phone, { phone = it }) { enteredDemo = true }
    } else {
        HomeScreen(selectedTab) { selectedTab = it }
    }
}

@Composable
private fun LoginScreen(phone: String, onPhoneChange: (String) -> Unit, onContinue: () -> Unit) {
    Box(
        Modifier.fillMaxSize().background(
            Brush.linearGradient(listOf(Color(0xFF102A56), Color(0xFF326CE5), Color(0xFF7B61D1)))
        ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier.fillMaxWidth().padding(22.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(Modifier.background(Color(0xFF235DCA), CircleShape).padding(18.dp)) {
                    Text("FC", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(16.dp))
                Text("FutureChat", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color(0xFF17366B))
                Text("Connect • Chat • Call", color = Color.Gray)
                Spacer(Modifier.height(28.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = onPhoneChange,
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Phone number") },
                    placeholder = { Text("+8801XXXXXXXXX") },
                    singleLine = true
                )
                Spacer(Modifier.height(14.dp))
                Button(
                    onClick = onContinue,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = phone.length >= 8
                ) { Text("Continue") }
                Spacer(Modifier.height(8.dp))
                Text(
                    "Demo only: phone OTP verification is not connected yet.",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val chats = listOf(
        DemoChat("FutureChat Team", "Welcome to FutureChat!", "F", "Now"),
        DemoChat("Sample Contact", "Your messages will appear here.", "S", "9:30"),
        DemoChat("Friends Group", "Start a new conversation.", "G", "Yesterday")
    )
    Surface(Modifier.fillMaxSize(), color = Color(0xFFF6F8FC)) {
        Column {
            Row(
                Modifier.fillMaxWidth().background(Color(0xFF173F86)).padding(horizontal = 20.dp, vertical = 22.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("FutureChat", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.Bold)
                    Text("Messages that bring us closer", color = Color(0xFFD7E4FF), fontSize = 12.sp)
                }
                Text("FC", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            when (selectedTab) {
                0 -> {
                    Text("Recent chats", Modifier.padding(start = 16.dp, top = 18.dp, bottom = 8.dp), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    LazyColumn { items(chats) { ChatRow(it) } }
                }
                1 -> PlaceholderPage("Calls", "Your voice and video call history will appear here.", Icons.Default.Call)
                2 -> PlaceholderPage("Contacts", "Find friends and start a conversation.", Icons.Default.Contacts)
                else -> PlaceholderPage("Profile", "Your profile and account settings.", Icons.Default.Person)
            }
            Spacer(Modifier.weight(1f))
            NavigationBar(containerColor = Color.White) {
                NavigationBarItem(selectedTab == 0, { onTabSelected(0) }, icon = { Icon(Icons.Default.Chat, null) }, label = { Text("Chats") })
                NavigationBarItem(selectedTab == 1, { onTabSelected(1) }, icon = { Icon(Icons.Default.Call, null) }, label = { Text("Calls") })
                NavigationBarItem(selectedTab == 2, { onTabSelected(2) }, icon = { Icon(Icons.Default.Contacts, null) }, label = { Text("Contacts") })
                NavigationBarItem(selectedTab == 3, { onTabSelected(3) }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
            }
        }
    }
}

@Composable
private fun ChatRow(chat: DemoChat) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 14.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.background(Color(0xFFDCE8FF), CircleShape).padding(16.dp)) {
            Text(chat.initials, color = Color(0xFF174A9C), fontWeight = FontWeight.Bold)
        }
        Column(Modifier.weight(1f).padding(start = 12.dp)) {
            Text(chat.name, fontWeight = FontWeight.SemiBold)
            Text(chat.preview, color = Color.Gray, fontSize = 13.sp)
        }
        Text(chat.time, color = Color.Gray, fontSize = 11.sp)
    }
    HorizontalDivider(Modifier.padding(start = 76.dp), color = Color(0xFFE9EDF5))
}

@Composable
private fun PlaceholderPage(title: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(
        Modifier.fillMaxWidth().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icon, contentDescription = null, tint = Color(0xFF326CE5), modifier = Modifier.padding(12.dp))
        Text(title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Color.Gray)
    }
}
