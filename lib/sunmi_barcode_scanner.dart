import 'dart:async';

import 'package:flutter/services.dart';

class SunmiBarcodeScanner {
  static const String barcodeListenerMethod = "listenBarcodeScanningProcess";
  static const String scanBarcode = "scan_barcode_event";
  // static const String INIT_SCANNER = "init_scanner";
  static const String killScanBarcodeListener = "kill_scan_barcode_event";
  static const String sunmiScannerMethodChannel = "sunmi_barcode_scanner";
  static EventChannel? _eventChannel;
  static MethodChannel? _methodChannel;

  static Future<bool?> get scan async {
    return await _methodChannel?.invokeMethod(scanBarcode);
  }

  static Stream<String>? get init {
    _methodChannel = const MethodChannel(sunmiScannerMethodChannel);
    _eventChannel = const EventChannel(barcodeListenerMethod);

    return _eventChannel
        ?.receiveBroadcastStream()
        .map((event) => event.toString());
  }

  static Stream<String>? listener() {
    return _eventChannel
        ?.receiveBroadcastStream()
        .map((event) => event.toString());
  }

  static Future<bool?> get dispose async {
    bool? success = await _methodChannel?.invokeMethod(killScanBarcodeListener);
    _eventChannel = null;
    _methodChannel = null;
    return success;
  }
}
