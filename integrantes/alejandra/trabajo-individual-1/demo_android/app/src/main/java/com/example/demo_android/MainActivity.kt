package com.example.demo_android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.demo_android.presentation.NavigationGraph
import com.example.demo_android.ui.theme.Demo_androidTheme

class MainActivity : ComponentActivity() {

    private val cameraPermissionRequestLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    this,
                    "Si tiene permiso de cámara :)",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Permite a esta aplicación al acceso de la cámara",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private var capturedImageBitmap = mutableStateOf<Bitmap?>(null)
    private val takePictureLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == RESULT_OK){
                val imageBitmap = result.data?.extras?.get("data") as Bitmap
                capturedImageBitmap.value = imageBitmap
                Toast.makeText(this, "Foto tomada", Toast.LENGTH_SHORT).show()
            }
        }

    private fun handleCameraPermission() : Boolean {
        return when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) -> {
                true
            }
            else -> {
                cameraPermissionRequestLauncher.launch(android.Manifest.permission.CAMERA)
                false
            }
        }
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun startDefaultCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent
                .putExtra("android.intent.extras.CAMERA_FACING", 0)
                .resolveActivity(packageManager)?.also {
                takePictureLauncher.launch(takePictureIntent)
            } ?: run {
                Toast.makeText(this, "No hay cámara disponible", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Demo_androidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { it ->
                        val navHostController = rememberNavController()
                        NavigationGraph(
                            navHostController = navHostController,
                            handleCameraPermission = {handleCameraPermission()},
                            startDefaultCamera = {startDefaultCamera()},
                            capturedImageBitmap = capturedImageBitmap.value
                        )
                    }
                }
            }
        }
    }
}