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


