package dtu.gruppe04.littlebrain.solitaire;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dtu.gruppe04.littlebrain.solitaire.card.Card;
import dtu.gruppe04.littlebrain.solitaire.card.Suit;

public class Klondike {
    public NodeList<Card>[] piles;

    /*
    0 facedown
    1 faceup
    2-8 descending
    9 - 12 SPADES, DIAMONDS, CLUBS, HEARTS;
     */

    public Klondike(){
        piles = new NodeList[13];

        for (int i = 0; i < 13; i++) {
            piles[i] = new NodeList<>();
        }

        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                piles[i+2].append(new Card(Suit.values()[random.nextInt(4)], random.nextInt(13) + 1, false));
            }
        }

        for (int i = 0; i < 24; i++) {
            piles[0].append(new Card(Suit.values()[random.nextInt(4)], random.nextInt(13) + 1, true));
        }
    }

    public boolean doMove(int from, int to, int amount){
        if (!isLegalMove(from, to, amount))
            return false;

        topCard(from).setHidden(false);
        piles[to].cut(piles[from].getCount()-amount,piles[from]);

        return true;
    }

    public int calculateValue(){
        int output = 0;

        for (int i = 9; i < 13; i++)
            output += piles[i].getCount();

        for (int i = 2; i < 9; i++) {
            int j = 0;
            for (Card card : piles[i])
                if (card.isHidden())
                    output += j++ - 8;
        }

        return output;
    }

    public Move[] possibleMoves(){
        List<Move> moves = new LinkedList<>();

        moves.add(new Move(0, 1));
        // From 1 to others
        for (int to = 2; to < 13; to++) {
            if (isLegalMove(1, to))
                moves.add(new Move(1, to));
        }
        // From stack to stack
        for (int from = 2; from < 9; from++) {
            for (int to = 2; to < 9; to++) {
                for (int amount = 1; amount <= 13; amount++) {
                    if (isLegalMove(from, to, amount))
                        moves.add(new Move(from, to, amount));
                }
            }
        }

        // From Suit
        for (int from = 9; from < 13; from++) {
            for (int to = 2; to < 9; to++) {
                if (isLegalMove(from, to))
                    moves.add(new Move(from, to));
            }
        }

        return moves.toArray(new Move[0]);
    }

    public boolean isLegalMove(int from, int to){
        return isLegalMove(from, to, 1);
    }

    public boolean isLegalMove(int from, int to, int amount){
        if (from == to || amount < 1)
            return false;

        // Cards in the deck can only be turned facedown into the faceup pileK
        if (from == 0)
            return to == 1 && amount == 1 && (piles[0].getCount() > 0 || piles[1].getCount() > 0);

        // You can only move cards from 0 to 1, we have already covered that case - note that resting the deck is considered drawing
        if (to < 2)
            return false;

        // Ensure the from pile contains enough cards first
        if (piles[from].getCount() < amount)
            return false;

        Card fromCard = piles[from].peek(piles[from].getCount() - amount);

        if (fromCard.isHidden())
            return false;

        boolean fromIsStack = from > 1 && from < 9;
        boolean toIsStack = to < 9;

        // Ensure we only move multiple cards in stack piles
        if (amount > 1 && !(fromIsStack && toIsStack))
            return false;

        Card preCard = fromCard;
        // Ensure cards above the lowest moved card are in correct order -- Only runs when amount is above 1, which can only happen on stack to stack transfer
        for (int i = piles[from].getCount() + 1 - amount; i < piles[from].getCount(); i++) {
            Card curCard = piles[from].peek(i);

            if (preCard.getSuit().getColour() == curCard.getSuit().getColour() || preCard.getValue() != curCard.getValue() + 1)
                return false;

            preCard = curCard;
        }

        // If moving to an empty pile -- Note that this always returns when landing on an empty pile
        if (piles[from].getCount() == 0){
            if (toIsStack)
                // You need a king to move onto a new stack
                return fromCard.getValue() == 13;
            else
                // You need the correct suit and an ace to start a suit pile
                return fromCard.getSuit() == Suit.values()[to - 9] && fromCard.getValue() == 1;
        }

        // Now that we have ensured that the to pile is not empty, we can peek at it.

        // If to is a suit pile
        if (!toIsStack){
            if (piles[to].getCount() == 0)
                return topSuit(from) == Suit.values()[to-9] && topValue(from) == 1;

            return topSuit(from) == topSuit(to) && topValue(from) == topValue(to) + 1;
        }

        // If from is not a stack pile, note that the line above ensures that to is a stack pile
        if (!fromIsStack)
            return topColour(from) != topColour(to) && topValue(from) == topValue(to) + 1;

        if (piles[to].getCount() == 0)
            return fromCard.getValue() == 13;

        // Both from and to are stack piles bellow this point.
        // Check for colour matching of the lowest moved card
        return fromCard.getSuit().getColour() != topColour(to) && fromCard.getValue() == topValue(to) - 1;
    }

    private int topColour(int pileID){
        return piles[pileID].peek(-1).getSuit().getColour();
    }

    private Suit topSuit(int pileID){
        return piles[pileID].peek(-1).getSuit();
    }

    private Card topCard(int pileID){
        return piles[pileID].peek(-1);
    }

    private int topValue(int pileID){
        return piles[pileID].peek(-1).getValue();
    }

    public class Move{
        public int From;
        public int To;
        public int Amount;

        public Move(int from, int to) {
            From = from;
            To = to;
            Amount = 1;
        }

        public Move(int from, int to, int amount) {
            From = from;
            To = to;
            Amount = amount;
        }
    }
}