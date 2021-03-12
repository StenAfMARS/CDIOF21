package dtu.gruppe04.littlebrain.solitaire.card;

public class Card implements Comparable<Card>
{
    private final Suit SUIT;
    private final int VALUE;
    private boolean hidden;

    public Card() {
        this(Suit.SPADES, 1, false);
    }

    public Card(Suit suit, int value ,boolean hidden){

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

    public Suit getSuit()
    {
        return SUIT;
    }

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

    public boolean colorEquals(Card card){
        return SUIT.getColour() == card.getSuit().getColour();
    }
}
