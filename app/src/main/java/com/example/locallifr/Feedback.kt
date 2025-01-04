package com.example.locallifr

data class Feedback(
    val taskId: String?, // Optional to link feedback to a task
    val userId: String,  // User providing feedback
    val comment: String  // Feedback text
)
