import 'dart:async';

import 'package:flutter/services.dart';

class SunmiBarcodeScanner {
  static const String BARCODE_LISTENER_METHOD = "listenBarcodeScanningProcess";
  static const String SCAN_BARCODE = "scan_barcode_event";
  static const String INIT_SCANNER = "init_scanner";
  static const String KILL_SCAN_BARCODE = "kill_scan_barcode_event";
  static const String SUNMI_SCANNER_METHOD_CHANNEL = "sunmi_barcode_scanner";
  static EventChannel? _eventChannel;
  static MethodChannel? _methodChannel;

  static Future<bool?> get scan async {
    return await _methodChannel?.invokeMethod(SCAN_BARCODE);
  }

  static Stream<String>? get init {
    _methodChannel = const MethodChannel(SUNMI_SCANNER_METHOD_CHANNEL);
    _eventChannel = const EventChannel(BARCODE_LISTENER_METHOD);

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
    bool? success = await _methodChannel?.invokeMethod(KILL_SCAN_BARCODE);
    _eventChannel = null;
    _methodChannel = null;
    return success;
  }
}
