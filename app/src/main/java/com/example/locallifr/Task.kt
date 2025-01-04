package com.example.locallifr

import java.util.UUID

data class Task(
    val title: String,
    val description: String, // Add this property
    val location: String = "", // Add default value for location
    var isVolunteered: Boolean = false,
    val id: String = UUID.randomUUID().toString(), // Unique identifier for each task
    val assignedTo: String? = null // Add this property to track the user the task is assigned to
)