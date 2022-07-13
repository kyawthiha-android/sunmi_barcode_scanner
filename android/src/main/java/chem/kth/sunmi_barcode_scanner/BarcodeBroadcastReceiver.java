package chem.kth.sunmi_barcode_scanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import io.flutter.plugin.common.EventChannel;

public class BarcodeBroadcastReceiver extends BroadcastReceiver {
    public static final String DATA = "data";
    private EventChannel.EventSink barcodeEventSink;

    public BarcodeBroadcastReceiver(EventChannel.EventSink events) {
        this.barcodeEventSink = events;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra(DATA);
        System.out.println(data);
        if (this.barcodeEventSink != null)
            this.barcodeEventSink.success(data);
        else System.out.println("barcode can't return data");
    }

    public void setEventChannel(EventChannel.EventSink events) {
        this.barcodeEventSink = events;
    }
}
