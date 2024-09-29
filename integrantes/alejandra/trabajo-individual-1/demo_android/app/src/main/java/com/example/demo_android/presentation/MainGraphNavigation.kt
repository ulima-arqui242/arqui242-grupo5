package com.example.demo_android.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    itineraryViewModel: ItineraryViewModel = hiltViewModel()
) {
    NavHost(
        navController = navHostController,
        startDestination = "main"
    ){
        composable("main"){
            TuriItinerary(itineraryViewModel = itineraryViewModel)
        }
    }
}