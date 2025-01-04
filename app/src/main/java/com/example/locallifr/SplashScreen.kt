package com.example.locallifr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import java.util.logging.Handler

@Composable
fun SplashScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Welcome to Local Lift!",
            textAlign = TextAlign.Center
        )
    }

    // Navigate to Welcome Screen after a delay
    LaunchedEffect(Unit) {
        delay(2000) // 2-second delay for the splash screen
        navController.navigate("welcome") {
            popUpTo("splash") { inclusive = true }
        }
    }
}
