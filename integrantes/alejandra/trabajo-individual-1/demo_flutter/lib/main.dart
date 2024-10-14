import 'package:demo_flutter/presentation/ItineraryViewModel.dart';
import 'package:demo_flutter/presentation/TuriItinerary.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:device_preview/device_preview.dart';
import 'package:provider/provider.dart'; // Importa Pro

void main()  => runApp(
  DevicePreview(
    enabled: !kReleaseMode,
    builder: (context) => ChangeNotifierProvider(
      create: (_) => ItineraryViewModel(), // Aquí colocas tu ViewModel
      child: const MyApp(),
    ),
  ),
);

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      useInheritedMediaQuery: true,
      locale: DevicePreview.locale(context),
      builder: DevicePreview.appBuilder,
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(),
    );
  }
}
