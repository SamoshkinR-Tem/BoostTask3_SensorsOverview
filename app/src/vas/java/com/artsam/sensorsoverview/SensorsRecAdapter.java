package com.artsam.sensorsoverview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class SensorsRecAdapter extends RecyclerView.Adapter<SensorsRecAdapter.ViewHolder> {

    public static final int LL_ID = 0;
    public static final int LAYOUT_WIDTH = 0;
    public static final int LAYOUT_HEIGHT = 0;
    public static final int LL_LAYOUT_WEIGHT = 2;
    public static final int TV_LAYOUT_WEIGHT = 1;

    private Context mContext;
    private List<String> mSensorNames;
    private Map<String, float[]> mValuesMap;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvSensorName;
        private LinearLayout mLlSecond;

        public ViewHolder(View itemView) {
            super(itemView);
            mTvSensorName = (TextView) itemView.findViewById(R.id.tv_sensor_name);
            mLlSecond = (LinearLayout) itemView.findViewById(LL_ID);
        }
    }

    public SensorsRecAdapter(List<String> sensorNames, Map<String, float[]> sensors) {
        this.mSensorNames = sensorNames;
        this.mValuesMap = sensors;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.card_sensor, parent, false);

        LinearLayout llFirst = (LinearLayout) v.findViewById(R.id.ll_first);

        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LAYOUT_WIDTH, LinearLayout.LayoutParams.WRAP_CONTENT, LL_LAYOUT_WEIGHT);
        LinearLayout llSecond = new LinearLayout(mContext);
        llSecond.setId(LL_ID);
        llSecond.setOrientation(LinearLayout.VERTICAL);
        llFirst.addView(llSecond, lParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String sensorName = mSensorNames.get(position);
        holder.mTvSensorName.setText(sensorName);

        if (mValuesMap.get(sensorName) != null) {
            holder.mLlSecond.removeAllViews();
            tvCreator(mValuesMap.get(sensorName).length, sensorName, holder);
        }
    }

    private void tvCreator(int length, String sensorName, ViewHolder holder){
        TextView tv;
        LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LAYOUT_HEIGHT, 1);
        for (int i = 0; i < length; i++) {
            tv = new TextView(mContext);
            tv.setText(String.valueOf(mValuesMap.get(sensorName)[i]));
            tv.setBackgroundColor(Color.GREEN);
            holder.mLlSecond.addView(tv, tvParams);
        }
    }

    @Override
    public int getItemCount() {
        return mSensorNames.size();
    }
}