package app.rowing.jobakker.rowingapp.views.adapter;

import android.view.View;

import java.util.Locale;

import app.rowing.jobakker.rowingapp.sensors.api.DistanceSensor;

public class DistanceCardViewHolder extends CardViewHolder implements DistanceSensor {

    public DistanceCardViewHolder(View layoutView) {
        super(layoutView);
    }

    @Override
    public void updatedDistance(float distance) {
        setDataText(String.format(Locale.getDefault(), ".2f", distance));
    }
}
