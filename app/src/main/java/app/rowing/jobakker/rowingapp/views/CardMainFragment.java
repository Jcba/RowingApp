package app.rowing.jobakker.rowingapp.views;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import app.rowing.jobakker.rowingapp.R;
import app.rowing.jobakker.rowingapp.sensors.api.DistanceSensor;
import app.rowing.jobakker.rowingapp.sensors.api.SpeedSensor;
import app.rowing.jobakker.rowingapp.sensors.api.StrokerateSensor;
import app.rowing.jobakker.rowingapp.views.adapter.CardRecyclerViewAdapter;
import app.rowing.jobakker.rowingapp.views.adapter.CardType;
import app.rowing.jobakker.rowingapp.views.card.CardData;

public class CardMainFragment extends Fragment implements DistanceSensor, StrokerateSensor, SpeedSensor {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "card_main_section";

    private GridLayoutManager gridLayoutManager;

    private List<CardData> cardDataList;

    private CardRecyclerViewAdapter cardRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main_data, container, false);

        cardDataList = getAllItemList();
        gridLayoutManager = new GridLayoutManager(rootView.getContext(), 2, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        cardRecyclerViewAdapter = new CardRecyclerViewAdapter(rootView.getContext(), cardDataList);
        recyclerView.setAdapter(cardRecyclerViewAdapter);

        Log.v("CardMainFragment", "fragment created");
        return rootView;
    }

    private List<CardData> getAllItemList() {
        List<CardData> allItems = new ArrayList<>();
        allItems.add(new CardData("Strokerate", "0", CardType.STROKERATE));
        allItems.add(new CardData("Distance", "0", CardType.DISTANCE));
        allItems.add(new CardData("Speed", "0", CardType.SPEED));
        allItems.add(new CardData("Time", "0", CardType.TIME));
        allItems.add(new CardData("HeartRate", "0", CardType.HEARTRATE));
        return allItems;
    }

    public static CardMainFragment newInstance(int sectionNumber) {
        CardMainFragment fragment = new CardMainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateCard(CardType type, String text) {
        for(int i = 0; i < cardDataList.size(); i++){
            CardData data= cardDataList.get(i);
            if(data.getCardType() == type){
                data.setDataText(text);
                cardRecyclerViewAdapter.notifyItemChanged(i);
            }
        }
    }

    @Override
    public void updatedDistance(float distance) {
        updateCard(CardType.DISTANCE, String.format(Locale.getDefault(), "%.2f", distance));
    }

    @Override
    public void stroke(int strokerate) {
        updateCard(CardType.STROKERATE, String.format(Locale.getDefault(), "%d", strokerate));
    }

    @Override
    public void newSpeed(float speed) {
        updateCard(CardType.SPEED, String.format(Locale.getDefault(), "%.2f", speed));
    }
}
