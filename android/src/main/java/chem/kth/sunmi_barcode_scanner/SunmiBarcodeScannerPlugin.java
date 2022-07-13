package chem.kth.sunmi_barcode_scanner;

import android.app.Activity;
import android.os.RemoteException;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/**
 * SunmiBarcodeScannerPlugin
 */
public class SunmiBarcodeScannerPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private SunmiScannerUtils connection;
    private Activity activity;
    private BarcodeBroadcastReceiver broadcastReceiver;
    private MethodChannel channel;
    private static final String BARCODE_LISTENER_EVENT_CHANNEL = "listenBarcodeScanningProcess";
    private static final String SCAN_BARCODE = "scan_barcode_event";
    private static final String KILL_SCAN_BARCODE = "kill_scan_barcode_event";
    private static final String SCANNER_METHOD_CHANNEL = "sunmi_barcode_scanner";
    private FlutterPluginBinding flutterPluginBinding;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), SCANNER_METHOD_CHANNEL);
        channel.setMethodCallHandler(this);
        this.flutterPluginBinding = flutterPluginBinding;
        onListenScannerEventChannel();
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        switch (call.method) {
            case SCAN_BARCODE:
                try {
                    if (connection != null)
                        connection.scan();
                } catch (RemoteException e) {
                    e.printStackTrace();
//                    result.error("0", e.getMessage(), e.getLocalizedMessage());
                    result.success(false);
                } catch (Exception e) {
                    e.printStackTrace();
                    result.success(false);
                }
                result.success(true);
                break;
            case KILL_SCAN_BARCODE:
                System.out.println("Oh! You kill barcode listener :p");
                connection.disconnectScannerService(activity.getApplicationContext(), broadcastReceiver);
                result.success(true);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    private void onListenScannerEventChannel() {
        EventChannel barcodeListener = new EventChannel(flutterPluginBinding.getBinaryMessenger(), BARCODE_LISTENER_EVENT_CHANNEL);
        barcodeListener.setStreamHandler(new EventChannel.StreamHandler() {
            @Override
            public void onListen(Object arguments, EventChannel.EventSink events) {
                if (broadcastReceiver == null)
                    broadcastReceiver = new BarcodeBroadcastReceiver(events);
                else broadcastReceiver.setEventChannel(events);
                callBarcodeScanEvent(broadcastReceiver);
            }

            @Override
            public void onCancel(Object arguments) {
                connection.disconnectScannerService(activity.getApplicationContext(), broadcastReceiver);
            }
        });
    }

    private void callBarcodeScanEvent(BarcodeBroadcastReceiver broadcastReceiver) {
        connection = SunmiScannerUtils.getInstance();
        connection.connectScannerService(activity.getApplicationContext(), broadcastReceiver);
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {

    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {

    }

    @Override
    public void onDetachedFromActivity() {

    }
}
