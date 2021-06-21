package dtu.gruppe04.littlebrain.solitaire.card;

import android.renderscript.Sampler;

public class Card implements Comparable<Card>
{
    private Suit SUIT;
    private int VALUE;
    private boolean hidden;
    private boolean unknown;

    public Card(boolean hidden) {
        this(Suit.SPADES, 1, hidden);
        unknown = true;
    }

    public void setUnknown(boolean unknown) {
        this.unknown = unknown;
    }

    public boolean isUnknown() {
        return unknown;
    }

    public Card(Suit suit, int value, boolean hidden){

        if(value < 1 || value > 13){
            throw new IllegalArgumentException("Value out of range.");
        }

        this.SUIT = suit;
        this.VALUE = value;
        this.hidden = hidden;
    }

    public int getValue()
    {
        return VALUE;
    }

    public Suit getSuit() { return SUIT; }
    public void setSuit(Suit suit){ this.SUIT = suit; }
    public void setVALUE(int value){ this.VALUE = value; }
    public void flip()
    {
        hidden = !hidden;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden)
    {
        this.hidden = hidden;
    }

    @Override
    public int compareTo(Card card) {
        return VALUE - card.getValue();
    }

    public boolean colourEquals(Card card){
        return SUIT.getColour() == card.getSuit().getColour();
    }

    public String toToon(){
        StringBuilder sb = new StringBuilder();

        switch (VALUE){
            case 1:
                sb.append('A');
                break;
            case 11:
                sb.append('J');
                break;
            case 12:
                sb.append('Q');
                break;
            case 13:
                sb.append('K');
                break;
            default:
                sb.append(VALUE);
                break;
        }

        switch (SUIT){
            case CLUBS:
                sb.append("♣");
                break;
            case HEARTS:
                sb.append("♥");
                break;
            case SPADES:
                sb.append("♠");
                break;
            case DIAMONDS:
                sb.append("♦");
                break;
        }

        return sb.toString();
    }
}
