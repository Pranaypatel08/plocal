package com.example.locallifr

data class UserProfile(
    val username: String,
    val email: String,
    val password: String,
    val role: String, // Add this property
    val newUser: UserProfile? = null // Avoid recursive instantiation
)