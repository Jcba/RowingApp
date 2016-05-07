package app.rowing.jobakker.rowingapp.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import app.rowing.jobakker.rowingapp.R;
import app.rowing.jobakker.rowingapp.sensors.api.Sensor;

public abstract class CardViewHolder extends RecyclerView.ViewHolder implements Sensor {
    private TextView headerText;
    private TextView dataText;

    public CardViewHolder(View layoutView) {
        super(layoutView);
        headerText = (TextView) layoutView.findViewById(R.id.smallcardheader);
        dataText = (TextView) layoutView.findViewById(R.id.smallcarddata);
    }

    public void setHeaderText(String headerText) {
        this.headerText.setText(headerText);
    }

    public void setDataText(String dataText) {
        this.dataText.setText(dataText);
    }
}
