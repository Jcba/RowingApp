package app.rowing.jobakker.rowingapp.views.card;

import app.rowing.jobakker.rowingapp.views.adapter.CardType;

public class CardData {
    private final String headerText;
    private final CardType cardType;
    private String dataText;

    public CardData(String headerText, String dataText, CardType cardType) {
        this.headerText = headerText;
        this.dataText = dataText;
        this.cardType = cardType;
    }

    public String getHeaderText() {
        return headerText;
    }

    public String getDataText() {
        return dataText;
    }

    public void setDataText(String dataText) {
        this.dataText = dataText;
    }

    public CardType getCardType() {
        return cardType;
    }
}
