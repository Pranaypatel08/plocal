package com.example.locallifr

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(navController: NavHostController, taskId: String?) {
    val context = LocalContext.current
    val sharedPrefs = SharedPrefsHelper(context)
    val task = sharedPrefs.getTasks().find { it.id == taskId }

    if (task != null) {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Task Details") }) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Title: ${task.title}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Description: ${task.description}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Status: ${if (task.isVolunteered) "Volunteered" else "Available"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Location: ${task.location ?: "No location provided"}",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Volunteer/Cancel Volunteering Button
                Button(
                    onClick = {
                        task.isVolunteered = !task.isVolunteered
                        sharedPrefs.editTask(task) // Update task in Shared Preferences
                        navController.navigate("home") // Navigate back to refresh
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (task.isVolunteered) "Cancel Volunteering" else "Volunteer")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Delete Task Button
                Button(
                    onClick = {
                        sharedPrefs.deleteTask(task.id)
                        navController.navigate("home") // Navigate back after deletion
                    },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete Task")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Back to Login Button
                Button(
                    onClick = { navController.navigate("login") },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Back to Home Button
                Button(
                    onClick = { navController.navigate("home_page") }, // Navigate to Home Page
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Home")
                }
            }
        }
    } else {
        Scaffold(
            topBar = { TopAppBar(title = { Text("Task Not Found") }) }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Task not found", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
