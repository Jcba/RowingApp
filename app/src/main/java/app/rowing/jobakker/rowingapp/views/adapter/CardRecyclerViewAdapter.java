package app.rowing.jobakker.rowingapp.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import app.rowing.jobakker.rowingapp.R;
import app.rowing.jobakker.rowingapp.views.card.CardData;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder> {
    private List<CardData> cardList;
    private Context context;

    public CardRecyclerViewAdapter(Context context, List<CardData> cardList) {
        this.cardList = cardList;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_small_card, null);
        CardViewHolder cardViewHolder = new CardViewHolder(layoutView);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        CardData data = cardList.get(position);
        holder.setHeaderText(data.getHeaderText());
        holder.setDataText(data.getDataText());
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }
}
