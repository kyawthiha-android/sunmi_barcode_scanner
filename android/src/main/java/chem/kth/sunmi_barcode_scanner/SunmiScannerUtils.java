package chem.kth.sunmi_barcode_scanner;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.sunmi.scanner.IScanInterface;

public class SunmiScannerUtils {
    private static SunmiScannerUtils mSunmiScannerService;
    private static final String SERVICEABLE = "com.sunmi.scanner";
    private static final String SERVICE_ACTION = "com.sunmi.scanner.IScanInterface";
    private static final String SUNMI_INTENT_FILTER = "com.sunmi.scanner.ACTION_DATA_CODE_RECEIVED";
    private IScanInterface scannerService;
    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            scannerService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            scannerService = IScanInterface.Stub.asInterface(service);
        }
    };



    private SunmiScannerUtils() {
    }

    public static SunmiScannerUtils getInstance() {
        if (mSunmiScannerService == null) {
            mSunmiScannerService = new SunmiScannerUtils();
        }
        return mSunmiScannerService;
    }

    public void connectScannerService(Context context, BroadcastReceiver broadcastReceiver) {
        Intent intent = new Intent();
        intent.setPackage(SERVICEABLE);
        intent.setAction(SERVICE_ACTION);
        context.getApplicationContext().bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        IntentFilter intentFilter = new IntentFilter(SUNMI_INTENT_FILTER);
        context.registerReceiver(broadcastReceiver, intentFilter);
    }

    public void disconnectScannerService(Context context, BroadcastReceiver broadcastReceiver) {
        if (scannerService != null) {
            context.getApplicationContext().unbindService(serviceConnection);
            context.unregisterReceiver(broadcastReceiver);
            scannerService = null;
        } else System.out.println("scanner is null can't kill");
    }

    public void sendKeyEvent(KeyEvent key) throws RemoteException {
        if (scannerService == null) return;
        scannerService.sendKeyEvent(key);
    }


    public void scan() throws RemoteException, SecurityException {
        if (scannerService == null) return;
        scannerService.scan();
    }

    public void stop() throws RemoteException {
        if (scannerService == null) return;
        scannerService.stop();
    }

    ///100 → NONE
    ///101 → P2Lite/V2Pro/P2Pro(em1365/BSM1825)
    ///102 → L2-newland(EM2096)
    ///103 → L2-zabra(SE4710)
    ///104 → L2-HoneyWell(N3601)
    ///105 → L2-HoneyWell(N6603)
    ///106 → L2-Zabra(SE4750)
    ///107 → L2-Zabra(EM1350)
    public int getScannerType() throws RemoteException {
        if (scannerService == null) return 0;
        return scannerService.getScannerModel();
    }
}