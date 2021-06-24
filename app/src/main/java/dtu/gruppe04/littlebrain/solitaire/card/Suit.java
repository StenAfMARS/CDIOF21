package dtu.gruppe04.littlebrain.solitaire.card;

import android.graphics.Color;

// Group nr. 4

public enum  Suit {
    SPADES, DIAMONDS, CLUBS, HEARTS;

    Suit()
    {

    }

    public int getColour()
    {
        switch (this)
        {
            case HEARTS: case DIAMONDS:
                return Color.RED;
            case SPADES: case CLUBS: default:
                return Color.BLACK;
        }
    }


}
