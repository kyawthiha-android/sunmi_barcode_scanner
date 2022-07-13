# sunmi_barcode_scanner

Sunmi barcode scanner

## Getting Started

init plugin

```

 Stream<String>? scannerListener = SunmiBarcodeScanner.init; 

```

Listen

```
 scannerListener?.listen((barcode) { 
   print('scanned barcode : $barcode');
 });
    
```

Call laser scan with button

```

bool? scannerCalled = await SunmiBarcodeScanner.scan;

```

Tested devices (✔)

- L2        
- V2 pro    


[L2 userguide_EN0731](https://github.com/kyawthiha-android/sunmi_barcode_scanner/blob/master/L2%20userguide_EN0731.pdf)


