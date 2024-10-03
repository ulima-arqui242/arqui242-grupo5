package com.example.demo_android.presentation

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    itineraryViewModel: ItineraryViewModel = hiltViewModel(),
    handleCameraPermission: () -> Boolean,
    startDefaultCamera: () -> Unit,
    capturedImageBitmap : Bitmap?
) {
    NavHost(
        navController = navHostController,
        startDestination = "main"
    ){
        composable("main"){
            TuriItinerary(
                itineraryViewModel = itineraryViewModel,
                handleCameraPermission = handleCameraPermission,
                startDefaultCamera = startDefaultCamera,
                capturedImageBitmap = capturedImageBitmap
            )
        }
    }
}