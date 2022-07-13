import 'dart:async';

import 'package:flutter/services.dart';

class SunmiBarcodeScanner {
  static const String BARCODE_LISTENER_METHOD = "listenBarcodeScanningProcess";
  static const String SCAN_BARCODE = "scan_barcode_event";
  static const String INIT_SCANNER = "init_scanner";
  static const String KILL_SCAN_BARCODE = "kill_scan_barcode_event";
  static const String SUNMI_SCANNER_METHOD_CHANNEL = "sunmi_barcode_scanner";
  static EventChannel? _eventChannel;

  static const MethodChannel _channel =
      MethodChannel(SUNMI_SCANNER_METHOD_CHANNEL);

  static Future<String?> get scan async {
    return await _channel.invokeMethod(SCAN_BARCODE);
  }

  static void get init {
    _eventChannel = const EventChannel(BARCODE_LISTENER_METHOD);
    // final String? scan = await _channel.invokeMethod(INIT_SCANNER);
  }

  static Stream<String>? listener() {
    return _eventChannel
        ?.receiveBroadcastStream()
        .map((event) => event.toString());
  }

  static Future<String?> get dispose async {
    return await _channel.invokeMethod(KILL_SCAN_BARCODE);
  }

  static Future<String?> get callListener async {
    return await _channel.invokeMethod(KILL_SCAN_BARCODE);
  }
}