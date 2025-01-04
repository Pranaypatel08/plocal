package com.example.locallifr

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPrefs = SharedPrefsHelper(context)

    val users = sharedPrefs.getUsers()
    val tasks = sharedPrefs.getTasks()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Admin Dashboard", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Users", style = MaterialTheme.typography.titleMedium)
        users.forEach { user ->
            Text(text = "User: ${user.username}, Role: ${user.role}")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Tasks", style = MaterialTheme.typography.titleMedium)
        tasks.forEach { task ->
            Row {
                Text(text = task.title)
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { sharedPrefs.deleteTask(task.id) }) {
                    Text("Delete")
                }
            }
        }
    }
}



fun assignTaskToUser(task: Task, userId: String, sharedPrefs: SharedPrefsHelper, tasks: MutableList<Task>) {
    val updatedTask = task.copy(assignedTo = userId)
    val updatedTasks = tasks.map { if (it.id == task.id) updatedTask else it }
    sharedPrefs.saveTasks(updatedTasks)
}

fun roleBasedHomeNavigation(navController: NavHostController, user: UserProfile) {
    if (user.role == "admin") {
        navController.navigate("admin_dashboard")
    } else {
        navController.navigate("user_dashboard")
    }
}


fun navigateBasedOnRole(navController: NavHostController, user: UserProfile) {
    if (user.role == "admin") {
        navController.navigate("admin_dashboard")
    } else {
        navController.navigate("home")
    }
}



private fun editTask(task: Task, sharedPrefs: SharedPrefsHelper, tasks: MutableList<Task>) {
    // Implement task editing logic
}

private fun deleteTask(task: Task, sharedPrefs: SharedPrefsHelper, tasks: MutableList<Task>) {
    tasks.remove(task)
    sharedPrefs.saveTasks(tasks)
}
