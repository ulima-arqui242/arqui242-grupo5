package com.example.demo_android.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_android.presentation.models.DaysItinerary
import com.example.demo_android.presentation.models.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(

) : ViewModel() {

    private val _days = MutableStateFlow("")
    val days : StateFlow<String>
        get() = _days.asStateFlow()

    private val _daysList = mutableStateListOf<DaysItinerary>()
    val daysList = _daysList

    private val _daySelected = MutableStateFlow(-1)
    val daySelected : StateFlow<Int>
        get() = _daySelected.asStateFlow()

    private val _selectedOption = MutableStateFlow(Restaurant())
    val selectedOption : StateFlow<Restaurant>
        get() = _selectedOption.asStateFlow()

    val favorites = listOf(
        Restaurant(id = 1, name = "Siete sopas"),
        Restaurant(id = 2, name = "Tanta"),
        Restaurant(id = 3, name = "Delfino Mar"),
        Restaurant(id = 4, name = "Kiriko")
    )


    fun onDaysChange(text: String){
        _days.value = text
    }

    fun setDays(){
        viewModelScope.launch {
            for (i in 1.._days.value.toInt()) {
                _daysList.add(
                    DaysItinerary(
                        id = i,
                        list = mutableListOf()
                    )
                )
            }
            Log.i("LISTA DÍAS", _daysList.toList().toString())
        }
    }

    fun setSelectedDay(day: Int){
        _daySelected.value = day
    }

    fun setSelectedOption(restaurant: Restaurant){
        _selectedOption.value = restaurant
    }

    fun addToDay(){
        val dayItem = _daysList.first { it.id == _daySelected.value }
        dayItem.list.add(_selectedOption.value)
        Log.i("LISTA DÍAS - after add", _daysList.toList().toString())
    }

    fun deleteFromDay(restaurant: Restaurant, day: Int){
        val dayIem = _daysList.first { it.id == day }
        dayIem.list.remove(restaurant)
        Log.i("LISTA DÍAS - after remove", _daysList.toList().toString())
    }

    fun resetValues(){
        _daySelected.value = -1
        _selectedOption.value = Restaurant()
    }

}