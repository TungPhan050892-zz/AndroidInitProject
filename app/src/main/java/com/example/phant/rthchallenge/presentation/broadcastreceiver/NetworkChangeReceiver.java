package com.example.phant.rthchallenge.presentation.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by phant on 5/29/2017.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        int status = NetworkUtil.getConnectivityStatusString(context);
        if (intent.getAction().equals(NetworkUtil.CONNECTIVITY_CHANGE)) {
            if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                Toast.makeText(context, "Disconnect from the internet!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Connected to the internet!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
