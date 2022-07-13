import 'package:flutter/material.dart';
import 'package:sunmi_barcode_scanner/sunmi_barcode_scanner.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const MaterialApp(
      home: BarcodePage(),
    );
  }
}

class BarcodePage extends StatefulWidget {
  const BarcodePage({Key? key}) : super(key: key);

  @override
  State<BarcodePage> createState() => _BarcodePageState();
}

class _BarcodePageState extends State<BarcodePage> {
  Stream<String>? scannerListener;

  _initScanner() {
    scannerListener = SunmiBarcodeScanner.init;
  }

  @override
  void initState() {
    _initScanner();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async => await SunmiBarcodeScanner.dispose ?? false,
      child: Scaffold(
        appBar: AppBar(
          title: const Text('Sunmi Barcode Scanner'),
        ),
        body: Center(
          child: Column(
            children: [
              ElevatedButton(
                  onPressed: () async {
                    bool? isSuccess = await SunmiBarcodeScanner.dispose;// <---- must be dispose
                    print('$isSuccess');
                  },
                  child: const Text('Stop')),
              const SizedBox(height: 10),
              ElevatedButton(
                  onPressed: () {
                    _initScanner();
                    setState(() {});
                  },
                  child: const Text('Start')),
              const SizedBox(height: 10),
              StreamBuilder<String>(
                  stream: scannerListener,
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
