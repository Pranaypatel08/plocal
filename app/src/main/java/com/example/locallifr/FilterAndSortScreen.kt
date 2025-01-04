package com.example.locallifr

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterAndSortScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Filter & Sort Tasks") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Filter and Sort functionality goes here!")
        }
    }
}
