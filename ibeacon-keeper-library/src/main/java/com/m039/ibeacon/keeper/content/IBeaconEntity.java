/** IBeaconEntity.java ---
 * 
 * Copyright (C) 2014 Dmitry Mozgin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.m039.ibeacon.keeper.content;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import com.m039.ibeacon.keeper.C;
import com.m039.ibeacon.keeper.library.R;

/**
 *
 *
 * Created: 07/03/14
 *
 * @author Dmitry Mozgin
 * @version
 * @since
 */
public class IBeaconEntity
    implements Parcelable,
               Comparable<IBeaconEntity>
{

    public static final int PRODUCER_UNKNOWN = 0;
    public static final int PRODUCER_ESTIMOTE = 1;
    public static final int PRODUCER_KONTAKT = 2;

    public static final int DISTANCE_FAR = 0;
    public static final int DISTANCE_NEAR = 1;
    public static final int DISTANCE_IMMEDIATE = 2;

    final protected IBeacon mIBeacon;

    protected int mProducer = PRODUCER_UNKNOWN;
    protected long mTimestamp = -1;
    protected BluetoothDevice mBluetoothDevice;
    protected int mRssi = 0;
    protected int mDistance = DISTANCE_FAR;
    protected float mAccuracy = -1.0f;

    protected byte[] mScanDataDebug = null;

    public IBeaconEntity(IBeacon iBeacon) {
        if (iBeacon == null) {
            throw new IllegalArgumentException("ibeacon should be not null");
        }

        mIBeacon = iBeacon;
    }

    public IBeacon getIBeacon() {
        return mIBeacon;
    }

    public String getProximityUuid() {
        return mIBeacon.getProximityUuid();
    }

    public int getMajor() {
        return mIBeacon.getMajor();
    }

    public int getMinor() {
        return mIBeacon.getMinor();
    }

    public int getTxPower() {
        return mIBeacon.getTxPower();
    }

    public int getProducer() {
        return mProducer;
    }

    public int getDistance() {
        return mDistance;
    }

    public int getDistanceStringId() {
        if (mDistance == DISTANCE_FAR) {
            return R.string.ibeacon_entity__distance__far;
        } else if (mDistance == DISTANCE_NEAR) {
            return R.string.ibeacon_entity__distance__near;
        } else if (mDistance == DISTANCE_IMMEDIATE) {
            return R.string.ibeacon_entity__distance__immediate;
        }

        return C.NO_ID;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public long getLastSeenTimestamp() {
        return getTimestamp();
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    public int getRssi() {
        return mRssi;
    }

    public float getAccuracy() {
        return mAccuracy;
    }

    /**
     * @return scanData if C.DEBUG is true
     */
    public byte[] getScanDataDebug() {
        return mScanDataDebug;
    }

    @Override
    public int hashCode() {
        return mIBeacon.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof IBeaconEntity)) {
            return false;
        }

        IBeaconEntity lhs = (IBeaconEntity) o;

        return (mIBeacon == null? 
                lhs.mIBeacon == null : mIBeacon.equals(lhs.mIBeacon));
    }

    @Override
    public int compareTo (IBeaconEntity another) {
        if (!(another instanceof IBeaconEntity)) {
            throw new ClassCastException("A IBeaconEntity object expected.");
        }

        return mIBeacon.compareTo(another.mIBeacon);
    }

    //
    // Parcelable
    //

    private IBeaconEntity(Parcel in) {
        mIBeacon = (IBeacon) in.readParcelable(IBeacon.class.getClassLoader());
        mBluetoothDevice = (BluetoothDevice) in.readParcelable(BluetoothDevice.class.getClassLoader());
        mProducer = in.readInt();
        mRssi = in.readInt();
        mDistance = in.readInt();
        mTimestamp = in.readLong();
        mAccuracy = in.readFloat();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(mIBeacon, flags);
        out.writeParcelable(mBluetoothDevice, flags);
        out.writeInt(mProducer);
        out.writeInt(mRssi);
        out.writeInt(mDistance);
        out.writeLong(mTimestamp);
        out.writeFloat(mAccuracy);
    }

    public static final Parcelable.Creator<IBeaconEntity> CREATOR = new Parcelable.Creator<IBeaconEntity>() {
            public IBeaconEntity createFromParcel(Parcel in) {
                return new IBeaconEntity(in);
            }

            public IBeaconEntity[] newArray(int size) {
                return new IBeaconEntity[size];
            }
        };

} // IBeaconEntity
