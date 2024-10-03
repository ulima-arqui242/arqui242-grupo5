package com.example.demo_android.presentation

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.demo_android.presentation.components.DayItem
import com.example.demo_android.presentation.components.FavItem
import com.example.demo_android.ui.theme.primaryGreen

@Composable
fun TuriItinerary(
    itineraryViewModel: ItineraryViewModel,
    handleCameraPermission: () -> Boolean,
    startDefaultCamera: () -> Unit,
    capturedImageBitmap : Bitmap?
) {

    val days by itineraryViewModel.days.collectAsState()
    val selectedOption by itineraryViewModel.selectedOption.collectAsState()
    val favorites = itineraryViewModel.favorites
    var takePhotoAble by remember {
        mutableStateOf(false)
    }

    val showModal = remember {
        mutableStateOf(false)
    }

    val daysList = itineraryViewModel.daysList

    LaunchedEffect(key1 = true){
        takePhotoAble  = handleCameraPermission()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp),
        contentPadding = PaddingValues(15.dp)
    ){
        
        item {
             Button(
                 onClick = {
                    if(takePhotoAble){
                        startDefaultCamera()
                    }else{
                        takePhotoAble = handleCameraPermission()
                    }
                 },
                 colors = ButtonDefaults.buttonColors(
                     containerColor = primaryGreen
                 )
             ) {
                 Text(text = "Tomar foto")
             }
        }

        item {
            capturedImageBitmap?.asImageBitmap()?.let {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    bitmap = it,
                    contentDescription = "taken"
                )
            }
        }
        
        item{
            Text(
                text = "Generar itinerario",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        if(daysList.isEmpty()){
            item{
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = days.toString(),
                    onValueChange = {
                        itineraryViewModel.onDaysChange(it)
                    },
                    label = {
                        Text(text = "Días")
                    },
                    placeholder = {
                        Text(text = "Ingresa la cantidad de días")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(25.dp)
                )
            }

            item{
                Button(
                    onClick = {
                        itineraryViewModel.setDays()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryGreen
                    ),
                ) {
                    Text(text = "Crear")
                }
            }
        }

        items(daysList){
            day ->
                DayItem(id = day.id) {
                    itineraryViewModel.setSelectedDay(day.id)
                    showModal.value = !showModal.value
                }
            day.list.forEach {
                ListItem(
                    headlineContent = {
                        Text(
                            text = it.name.toString(),
                            fontSize = 15.sp
                        )
                    },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "fav",
                            tint = Color.Black
                        )
                    }
                )
            }
        }

    }

    if(showModal.value){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.4f)),
        ){
            Dialog(
                onDismissRequest = {
                    showModal.value = false
                }
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.5f),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(15.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(text = "Mi lista", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight(0.9f),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ){
                            items(favorites){
                                    fav ->
                                FavItem(
                                    onClick = { itineraryViewModel.setSelectedOption(fav) },
                                    fav = fav,
                                    selectedOption = selectedOption
                                )
                            }
                        }
                        Button(
                            onClick = {
                                itineraryViewModel.addToDay()
                                itineraryViewModel.resetValues()
                                showModal.value = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryGreen
                            ),
                        ) {
                            Text(text = "Agregar")
                        }
                    }
                }
            }
        }
    }
}