package dtu.gruppe04.littlebrain.solitaire.card;

import android.graphics.Color;

public enum  Suit {
    SPADES, HEARTS, DIAMONDS, CLUBS;

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
