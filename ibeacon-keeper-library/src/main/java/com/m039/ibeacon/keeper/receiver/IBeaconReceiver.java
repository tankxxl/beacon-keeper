/** IBeaconReceiver.java ---
 *
 * Copyright (C) 2014 Dmitry Mozgin
 *
 * Author: Dmitry Mozgin <m0391n@gmail.com>
 *
 *
 */

package com.m039.ibeacon.keeper.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.m039.ibeacon.keeper.content.IBeaconEntity;
import com.m039.ibeacon.keeper.service.IBeaconService;

/**
 *
 *
 * Created: 07/01/14
 *
 * @author Dmitry Mozgin
 * @version
 * @since
 */
public class IBeaconReceiver extends BroadcastReceiver
{
    public static final String TAG = "m039-IBeaconReceiver";

    public static Intent registerReceiver(Context ctx, BroadcastReceiver br) {
        IntentFilter filter = new IntentFilter();

        filter.addAction(IBeaconService.ACTION_FOUND_IBEACON);
        filter.addAction(IBeaconService.ACTION_BLE_ENABLED);
        filter.addAction(IBeaconService.ACTION_BLE_DISABLED);

        return ctx.registerReceiver(br, filter);
    }

    public static void unregisterReceiver(Context ctx, BroadcastReceiver br) {
        ctx.unregisterReceiver(br);
    }

    public Intent registerReceiver(Context ctx) {
        return registerReceiver(ctx, this);
    }

    public void unregisterReceiver(Context ctx) {
        unregisterReceiver(ctx, this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(IBeaconService.ACTION_FOUND_IBEACON)) {
            onFoundIBeacon((IBeaconEntity) intent
                           .getParcelableExtra(IBeaconService.EXTRA_IBEACON_ENTITY));
        } else if (action.equals(IBeaconService.ACTION_BLE_ENABLED)) {
            onBleEnabled();
        } else if (action.equals(IBeaconService.ACTION_BLE_DISABLED)) {
            onBleDisabled();
        }
    }

    protected void onFoundIBeacon(IBeaconEntity iBeaconEntity) {
        // log
    }

    protected void onBleEnabled() {
        // log
    }

    protected void onBleDisabled() {
        // log
    }

} // IBeaconReceiver
