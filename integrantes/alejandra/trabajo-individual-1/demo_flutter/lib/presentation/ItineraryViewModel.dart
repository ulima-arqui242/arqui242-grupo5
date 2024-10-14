import 'package:demo_flutter/models/Day.dart';
import 'package:demo_flutter/models/Restaurant.dart';
import 'package:flutter/material.dart';

class ItineraryViewModel extends ChangeNotifier{
  List<Day> _daysList = [];
  final List<Restaurant> _restaurantOptions = [
    Restaurant(id: 1, name: 'Siete sopas'),
    Restaurant(id: 2, name: 'Delfino Mar'),
    Restaurant(id: 3, name: 'Tanta'),
    Restaurant(id: 4, name: 'Yuuki'),
  ];
  Restaurant _selectedRestaurant = Restaurant(id: -1, name: "");


  List<Day> get daysList => _daysList;
  List<Restaurant> get restaurantOptions => _restaurantOptions;
  Restaurant get selectedRestaurant => _selectedRestaurant;

  void createDays(int count) {
    _daysList = List.generate(count, (index) => Day(id: index + 1));
    notifyListeners();
  }

  void setSelectedRestaurant(Restaurant restaurant){
    _selectedRestaurant = restaurant;
    notifyListeners();
  }

  void addRestaurantToDay(int dayIndex) {
    Day day = _daysList.where((element) => element.id == dayIndex).first;
    day.list.add(_selectedRestaurant);
    print(_daysList.toList());
    notifyListeners();
  }

  void resetValues() {
    _selectedRestaurant = Restaurant(id: -1, name: "");
  }
}