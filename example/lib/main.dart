import 'package:flutter/material.dart';
import 'package:sunmi_barcode_scanner/sunmi_barcode_scanner.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    SunmiBarcodeScanner.init;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Column(
            children: [
              ElevatedButton(
                  onPressed: () {
                    SunmiBarcodeScanner.dispose;
                  },
                  child: const Text('Stop')),
              const SizedBox(height: 10),
              ElevatedButton(
                  onPressed: () {
                    SunmiBarcodeScanner.init;
                  },
                  child: const Text('Start')),
              const SizedBox(height: 10),
              StreamBuilder<String>(
                  stream: SunmiBarcodeScanner.listener(),
                  builder: (context, snapshot) {
                    if (snapshot.hasData) {
                      return Text('${snapshot.data}');
                    } else {
                      return Container();
                    }
                  }),
              const SizedBox(height: 10),
              ElevatedButton(
                  onPressed: () async {
                    await SunmiBarcodeScanner.scan;
                  },
                  child: const Text('scan')),
              const SizedBox(height: 10),
            ],
          ),
        ),
      ),
    );
  }
}
