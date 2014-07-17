/** IBeacon.java ---
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

package com.m039.ibeacon.keeper.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.m039.ibeacon.keeper.U;
import com.m039.ibeacon.keeper.app.R;
import com.m039.ibeacon.keeper.content.IBeaconEntity;

/**
 *
 *
 * Created:
 *
 * @author
 * @version
 * @since
 */
public class IBeaconEntityAdapter
    extends RecyclerView.Adapter<IBeaconEntityAdapter.ViewHolder>
            implements View.OnClickListener
{

    private List<IBeaconEntity> mIBeaconEntities;

    public IBeaconEntityAdapter() {
        this(new ArrayList<IBeaconEntity>());
    }

    public IBeaconEntityAdapter(List<IBeaconEntity> iBeaconEntities) {
        if (iBeaconEntities == null) {
            throw new IllegalArgumentException("iBeaconEntities is null");
        }

        mIBeaconEntities = iBeaconEntities;
    }

    public boolean replace(IBeaconEntity iBeaconEntity) {
        int index = mIBeaconEntities.indexOf(iBeaconEntity);
        boolean result = false;

        if (index == -1) {
            result = mIBeaconEntities.add(iBeaconEntity);
            if (result) {
                notifyItemInserted(mIBeaconEntities.size() - 1);
            }

        } else {
            mIBeaconEntities.set(index, iBeaconEntity);
            result = true;
            notifyItemChanged(index);
        }

        return result;
    }

    public void clear () {
        int size = mIBeaconEntities.size();
        mIBeaconEntities.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void removeOld(int timeToLiveMs) {
        List<IBeaconEntity> toRemove = null;
        long currentTimeMs = System.currentTimeMillis();

        for (IBeaconEntity iBeaconEntity : mIBeaconEntities) {
            if ((currentTimeMs - iBeaconEntity.getTimestamp()) > timeToLiveMs) {
                if (toRemove == null) {
                    toRemove = new ArrayList<IBeaconEntity>();
                }

                toRemove.add(iBeaconEntity);
            }
        }

        if (toRemove != null) {
            mIBeaconEntities.removeAll(toRemove);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView proximityUuid;
        TextView major;
        TextView minor;
        TextView txPower;
        TextView accuracy;
        TextView distance;
        TextView lastUpdate;
        ImageView producer;

        ViewHolder(View v) {
            super(v);

            proximityUuid = (TextView) v.findViewById(R.id.proximity_uuid);
            major = (TextView) v.findViewById(R.id.major);
            minor = (TextView) v.findViewById(R.id.minor);
            txPower = (TextView) v.findViewById(R.id.tx_power);
            accuracy = (TextView) v.findViewById(R.id.accuracy);
            distance = (TextView) v.findViewById(R.id.distance);
            lastUpdate = (TextView) v.findViewById(R.id.last_update);
            producer = (ImageView) v.findViewById(R.id.producer);
        }

    }

    @Override
    public IBeaconEntityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent
            .getContext()
            .getSystemService (Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.e_list, parent, false);

        ViewHolder vh = new ViewHolder(v);

        v.setOnClickListener(this);
        v.setTag(vh);

        return vh;
    }

    @Override
    public void onClick(View view) {
        ViewHolder vh = (ViewHolder) view.getTag();
        onClick(mIBeaconEntities.get(vh.getPosition()));
    }

    protected void onClick(IBeaconEntity iBeaconEntity) {
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int position) {
        IBeaconEntity iBeaconEntity = mIBeaconEntities.get(position);

        h.proximityUuid.setText(iBeaconEntity.getProximityUuid());
        h.major.setText(String.valueOf(iBeaconEntity.getMajor()));
        h.minor.setText(String.valueOf(iBeaconEntity.getMinor()));
        h.txPower.setText(String.valueOf(iBeaconEntity.getTxPower()));
        h.accuracy.setText(String.format("%.2f", iBeaconEntity.getAccuracy()));
        h.lastUpdate.setText(U.IBeacon.getLastUpdate(iBeaconEntity));
        h.distance.setText(iBeaconEntity.getDistanceStringId());
        h.producer.setImageResource(getProducerDrawableId(iBeaconEntity));
    }

    @Override
    public int getItemCount() {
        return mIBeaconEntities.size();
    }

    private static int getProducerDrawableId(IBeaconEntity iBeaconEntity) {
        if (iBeaconEntity.getProducer() == IBeaconEntity.PRODUCER_ESTIMOTE) {
            return R.drawable.ibeacon_entity__producer__estimote;
        } else if (iBeaconEntity.getProducer() == IBeaconEntity.PRODUCER_KONTAKT) {
            return R.drawable.ibeacon_entity__producer__kontakt;
        } else {
            return R.drawable.ibeacon_entity__producer__unknown;
        }
    }

} // IBeaconEntityAdapter
