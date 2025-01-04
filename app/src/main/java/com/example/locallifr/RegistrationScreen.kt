package com.example.locallifr


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(navController: NavHostController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val sharedPrefs = SharedPrefsHelper(context)

    Scaffold(
        topBar = { TopAppBar(title = { Text("Register") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                        val newUser = UserProfile(username, email, password, role = "user")
                        val sharedPrefs = SharedPrefsHelper(context)
                        val users = sharedPrefs.getUsers().toMutableList().apply { add(newUser) }
                        sharedPrefs.saveUsers(users)

                        // Add this log to verify users
                        val savedUsers = sharedPrefs.getUsers()
                        Log.d("RegistrationScreen", "Saved Users: $savedUsers")

                        navController.navigate("login") // Navigate to login
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

        }
    }
}
