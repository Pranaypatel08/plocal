package com.example.locallifr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.locallifr.ui.theme.LocalLifrTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalLifrTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {
        composable("splash") { SplashScreen(navController) }
        composable("welcome") { WelcomeScreen(navController) }
        composable("registration") { RegistrationScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("home_page") { HomePage(navController) }
        composable("task_dashboard") { TaskDashboard(navController) }
        composable("add_task") { AddTaskScreen(navController) }
        composable(
            "taskDetail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
            val sharedPrefs = SharedPrefsHelper(navController.context)
            val task = sharedPrefs.getTasks()?.find { it.id == taskId }
            if (task != null) {
                TaskDetailScreen(navController = navController, task = task)
            }
        }
        composable("filterAndSort") { FilterAndSortScreen(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminFeedbackScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs = SharedPrefsHelper(context)
    val feedbacks = sharedPrefs.getFeedbacks()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("All Feedback") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (feedbacks.isEmpty()) {
                Text("No feedback available.")
            } else {
                feedbacks.forEach { feedback ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "User: ${feedback.userId}", style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Task: ${feedback.taskId ?: "General"}", style = MaterialTheme.typography.bodySmall)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Comment: ${feedback.comment}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}


fun TaskDetailScreen(navController: NavHostController, task: Task) {
    TODO("Not yet implemented")
}
