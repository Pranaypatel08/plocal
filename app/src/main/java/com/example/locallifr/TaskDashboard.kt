package com.example.locallifr

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDashboard(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs = remember { SharedPrefsHelper(context) }
    val tasks = sharedPrefs.getTasks()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Task Dashboard") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_task") }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            LazyColumn {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onDelete = {
                            sharedPrefs.deleteTask(task.id)
                            navController.navigate("task_dashboard") // Refresh dashboard
                        },
                        onVolunteer = {
                            task.isVolunteered = !task.isVolunteered
                            sharedPrefs.editTask(task) // Update volunteer status
                            navController.navigate("task_dashboard") // Refresh dashboard
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Back to Home Button
            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Home")
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onDelete: () -> Unit, onVolunteer: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${task.title}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Description: ${task.description}", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Status: ${if (task.isVolunteered) "Volunteered" else "Available"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(
                    onClick = { onVolunteer() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (task.isVolunteered) "Cancel Volunteering" else "Volunteer")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onDelete() },
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
