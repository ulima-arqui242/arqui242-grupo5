import 'package:demo_flutter/models/Restaurant.dart';
import 'package:demo_flutter/presentation/ItineraryViewModel.dart';
import 'package:flutter/material.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:provider/provider.dart';

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final scrollContoller = ScrollController();
  final TextEditingController _hostController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final _viewModel = Provider.of<ItineraryViewModel>(context);
    final greenPrimary = Color(0xFF43A406);
    return Scaffold(
      appBar: AppBar(
        backgroundColor: greenPrimary,
        title: const Text("TURI", style: TextStyle(color: Colors.white)),
      ),
      body: Expanded(
        child: SingleChildScrollView(
          controller: scrollContoller,
          physics: const ScrollPhysics(),
          child: Padding(
            padding: const EdgeInsets.all(10),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                const Text(
                  "Generar itinerario",
                  style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold),
                ),
                SizedBox(height: 10.0),
                _viewModel.daysList.isNotEmpty
                    ? ListView.builder(
                        shrinkWrap: true,
                        itemCount: _viewModel.daysList.length,
                        itemBuilder: (context, index) {
                          return ExpansionTile(
                              title: Text(
                                "Día ${_viewModel.daysList[index].id}: ",
                                style: const TextStyle(
                                    fontWeight: FontWeight.bold, fontSize: 25),
                              ),
                              children: _viewModel.daysList[index].list
                                  .map((e) => ListTile(
                                        title: Text(e.name),
                                        trailing: Icon(Icons.favorite, color: Colors.black,),
                                      ))
                                  .toList(),
                              trailing: GestureDetector(
                                child: Icon(
                                  Icons.add_outlined,
                                ),
                                onTap: () {
                                  _showDialog(
                                      context, _viewModel.daysList[index].id);
                                },
                              ),
                              shape: RoundedRectangleBorder(
                                  side:
                                      BorderSide(color: Colors.grey, width: 1),
                                  borderRadius: BorderRadius.circular(25)));
                        },
                        controller: scrollContoller,
                      )
                    : Column(
                        children: [
                          TextField(
                            decoration: new InputDecoration(
                                labelText: "Días de viaje",
                                border: OutlineInputBorder(
                                    borderSide: BorderSide(
                                        color: Colors.grey, width: 1),
                                    borderRadius:
                                        BorderRadius.all(Radius.circular(25)))),
                            keyboardType: TextInputType.number,
                            controller: _hostController,
                          ),
                          SizedBox(height: 10.0),
                          ElevatedButton(
                            onPressed: () {
                              int dayCount =
                                  int.tryParse(_hostController.text) ?? 1;
                              _viewModel.createDays(dayCount);
                            },
                            child: Text(
                              "Crear",
                              style: TextStyle(color: Colors.white),
                            ),
                            style: ButtonStyle(
                              backgroundColor:
                                  MaterialStateProperty.all(Color(0xFF43A406)),
                            ),
                          ),
                        ],
                      )
              ],
            ),
          ),
        ),
      ),
    );
  }
}

void _showDialog(BuildContext context, int dayIndex) {
  final _viewModel = Provider.of<ItineraryViewModel>(context, listen: false);
  Restaurant selectedRestaurant = _viewModel.selectedRestaurant;

  showModalBottomSheet(
      context: context,
      builder: (context) {
        return Center(
          child: Padding(
            padding: EdgeInsets.all(10),
            child: Column(
              children: [
                Text(
                  "Mi lista",
                  style: TextStyle(fontSize: 40, fontWeight: FontWeight.bold),
                ),
                ListView.builder(
                    itemCount: _viewModel.restaurantOptions.length,
                    shrinkWrap: true,
                    padding: EdgeInsets.symmetric(vertical: 10),
                    itemBuilder: (context, index) {
                      final resturant = _viewModel.restaurantOptions[index];
                      return Padding(
                        padding: const EdgeInsets.symmetric(vertical: 10.0),
                        child: ListTile(
                            title: Text(resturant.name),
                            onTap: () {
                              _viewModel.setSelectedRestaurant(resturant);
                            },
                            selectedColor: resturant == selectedRestaurant
                                ? Colors.grey
                                : Colors.transparent,
                            shape: RoundedRectangleBorder(
                                side: BorderSide(color: Colors.grey, width: 1),
                                borderRadius: BorderRadius.circular(25))),
                      );
                    }),
                ElevatedButton(
                  onPressed: () {
                    _viewModel.addRestaurantToDay(dayIndex);
                    Navigator.pop(context);
                  },
                  child: Text(
                    "Agregar",
                    style: TextStyle(color: Colors.white),
                  ),
                  style: ButtonStyle(
                    backgroundColor:
                        MaterialStateProperty.all(Color(0xFF43A406)),
                  ),
                ),
              ],
            ),
          ),
        );
      });
}
