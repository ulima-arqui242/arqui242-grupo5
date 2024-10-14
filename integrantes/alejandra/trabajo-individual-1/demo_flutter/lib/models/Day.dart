import 'package:demo_flutter/models/Restaurant.dart';

class Day {
  final int id;
  final List<Restaurant> list;

  Day({required this.id, List<Restaurant>? list})
    :list = list ?? [];
}