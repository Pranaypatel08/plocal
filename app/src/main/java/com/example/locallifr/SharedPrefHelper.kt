package com.example.locallifr

import android.content.Context
import android.content.SharedPreferences
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPrefsHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LocalLiftPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun registerUser(userProfile: UserProfile) {
        val users = getUsers().toMutableList()
        users.add(userProfile)
        saveUsers(users)
    }



    // Save a single user profile
    fun saveUserProfile(userProfile: UserProfile) {
        val userJson = gson.toJson(userProfile)
        sharedPreferences.edit().putString("user_profile", userJson).apply()
    }

    // Retrieve a single user profile
    fun getUserProfile(): UserProfile? {
        val userJson = sharedPreferences.getString("user_profile", null)
        return userJson?.let { gson.fromJson(it, UserProfile::class.java) }
    }

    // Check credentials for login
    fun isValidCredentials(username: String, password: String): Boolean {
        val users = getUsers() // Retrieve all stored users
        return users.any { it.username == username && it.password == password }
    }


    // Retrieve all tasks
    fun getTasks(): List<Task> {
        val tasksJson = sharedPreferences.getString("tasks", null)
        return if (tasksJson != null) {
            val type = object : TypeToken<List<Task>>() {}.type
            gson.fromJson(tasksJson, type)
        } else {
            emptyList() // Return an empty list instead of null
        }
    }

    // Save all tasks
    fun saveTasks(tasks: List<Task>) {
        val tasksJson = gson.toJson(tasks)
        sharedPreferences.edit().putString("tasks", tasksJson).apply()
    }

    // Retrieve all user profiles
    fun getUsers(): List<UserProfile> {
        val usersJson = sharedPreferences.getString("user_profiles", null)
        return if (usersJson != null) {
            val type = object : TypeToken<List<UserProfile>>() {}.type
            gson.fromJson(usersJson, type)
        } else {
            emptyList()
        }
    }

    // Save all user profiles
    fun saveUsers(users: List<UserProfile>) {
        val usersJson = gson.toJson(users)
        sharedPreferences.edit().putString("user_profiles", usersJson).apply()
    }

    // Edit an existing task
    fun editTask(updatedTask: Task) {
        val tasks = getTasks().toMutableList()
        val index = tasks.indexOfFirst { it.id == updatedTask.id }
        if (index != -1) {
            tasks[index] = updatedTask
            saveTasks(tasks)
        }
    }

    // Delete a task
    fun deleteTask(taskId: String) {
        val tasks = getTasks().toMutableList()
        tasks.removeAll { it.id == taskId }
        saveTasks(tasks)
    }

    // Edit an existing user
    fun editUser(updatedUser: UserProfile) {
        val users = getUsers().toMutableList()
        val index = users.indexOfFirst { it.email == updatedUser.email }
        if (index != -1) {
            users[index] = updatedUser
            saveUsers(users)
        }
    }

    // Delete a user
    fun deleteUser(email: String) {
        val users = getUsers().toMutableList()
        users.removeAll { it.email == email }
        saveUsers(users)
    }

    // Retrieve all feedback
    fun getFeedbacks(): List<Feedback> {
        val feedbacksJson = sharedPreferences.getString("feedbacks", null)
        return if (feedbacksJson != null) {
            val type = object : TypeToken<List<Feedback>>() {}.type
            gson.fromJson(feedbacksJson, type)
        } else {
            emptyList()
        }
    }

    // Save all feedback
    fun saveFeedbacks(feedbacks: List<Feedback>) {
        val feedbacksJson = gson.toJson(feedbacks)
        sharedPreferences.edit().putString("feedbacks", feedbacksJson).apply()
    }

    // Add a single feedback
    fun addFeedback(feedback: Feedback) {
        val feedbacks = getFeedbacks().toMutableList()
        feedbacks.add(feedback)
        saveFeedbacks(feedbacks)
    }

    // Role-based navigation
    fun RoleBasedNavigation(navController: NavHostController, user: UserProfile) {
        if (user.role == "admin") {
            navController.navigate("admin_dashboard")
        } else {
            navController.navigate("home")
        }
    }
}
