package dtu.gruppe04.littlebrain;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;

import dtu.gruppe04.littlebrain.solitaire.Klondike;
import dtu.gruppe04.littlebrain.solitaire.card.Card;
import dtu.gruppe04.littlebrain.solitaire.card.Suit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class KlondikeTest {
    @Test
    public void test(){
        int wins = 0;
        int games = 1000;

        for (int i = 0; i < games; i++) {
            if (tryWinGame())
                wins++;
        }

        assertTrue("won " + wins + " games", false);
    }

    public boolean tryWinGame() {
        ArrayList<Card> cards = new ArrayList<>();

        for (int i = 1; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
                cards.add(new Card(Suit.values()[j], i, true));
            }
        }

        Collections.shuffle(cards);

        // Context of the app under test.
        Klondike klondike = new Klondike(cards);


        for (int i = 0; i < 500 && !gameWon(klondike); i++) {
            Klondike.Move bestMove = klondike.bestMove(3);

            klondike.doMove(bestMove.From, bestMove.To, bestMove.Amount);
        }

        return gameWon(klondike);
    }

    public boolean gameWon(Klondike klondike){
        return     klondike.piles[0].getCount() == 0
                && klondike.piles[1].getCount() == 0
                && (klondike.piles[2].getCount() == 0 || !klondike.piles[2].peek(0).isHidden())
                && (klondike.piles[3].getCount() == 0 || !klondike.piles[3].peek(0).isHidden())
                && (klondike.piles[4].getCount() == 0 || !klondike.piles[4].peek(0).isHidden())
                && (klondike.piles[5].getCount() == 0 || !klondike.piles[5].peek(0).isHidden())
                && (klondike.piles[6].getCount() == 0 || !klondike.piles[6].peek(0).isHidden())
                && (klondike.piles[7].getCount() == 0 || !klondike.piles[7].peek(0).isHidden())
                && (klondike.piles[8].getCount() == 0 || !klondike.piles[8].peek(0).isHidden());
    }
}