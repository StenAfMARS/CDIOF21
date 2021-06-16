package dtu.gruppe04.littlebrain.solitaire;

import java.util.LinkedList;
import java.util.List;

import dtu.gruppe04.littlebrain.solitaire.card.Card;
import dtu.gruppe04.littlebrain.solitaire.card.Suit;

public class Klondike {
    public Card[][] piles;
    public int [] topPos;

    /*
    0 facedown
    1 faceup
    2-8 descending
    9 - 12 SPADES, HEARTS, DIAMONDS, CLUBS;
     */

    public Klondike(){
        piles = new Card[13][];

        for (int i = 2; i < 13; i++) {
            piles[i] = new Card[13];
        }

        piles[0] = new Card[56];
        piles[1] = new Card[56];

        topPos = new int[13];

        for (int i = 0; i < 13; i++) {
            topPos[i] = -1;
        }
    }

    public int calculateValue(){
        int output = 0;

        for (int i = 9; i < 13; i++)
            output += topPos[i] + 1;

        for (int i = 2; i < 9; i++)
            for (int j = 0; j < topPos[i]; j++)
                if (piles[i][j].isHidden())
                    output += j - 8;

        return output;
    }

    public int calculateValue(Move move){
        int output = 0;

        for (int i = 9; i < 13; i++)
            output += topPos[i] + 1;

        for (int i = 2; i < 9; i++){

            int adjustedSize = topPos[i];

            if (move.From == i)
                adjustedSize -= move.Amount + 1;

            for (int j = 0; j < adjustedSize; j++) {

                if (piles[i][j].isHidden())
                    output += j - 8;
            }
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

        // Cards in the deck can only be turned facedown into the faceup pile
        if (from == 0)
            return to == 1 && amount == 1 && (topPos[0] >= 0 || topPos[1] >= 0);

        // You can only move cards from 0 to 1, we have already covered that case - note that resting the deck is considered drawing
        if (to < 2)
            return false;

        // Ensure the from pile contains enough cards first
        if (topPos[from] + 1 < amount)
            return false;

        Card fromCard = piles[from][topPos[from] - amount];

        if (fromCard.isHidden())
            return false;

        boolean fromIsStack = from > 2 && from < 9;
        boolean toIsStack = to < 9;

        // Ensure we only move multiple cards in stack piles
        if (amount > 1 && !(fromIsStack && toIsStack))
            return false;

        Card preCard = fromCard;
        // Ensure cards above the lowest moved card are in correct order -- Only runs when amount is above 1, which can only happen on stack to stack transfer
        for (int i = topPos[from] + 2 - amount; i <= topPos[from]; i++) {
            Card curCard = piles[from][i];

            if (preCard.getSuit().getColour() == curCard.getSuit().getColour() || preCard.getValue() + 1 != curCard.getValue())
                return false;

            preCard = curCard;
        }

        // If moving to an empty pile -- Note that this always returns when landing on an empty pile
        if (topPos[to] == -1){
            if (toIsStack)
                // You need a king to move onto a new stack
                return fromCard.getValue() == 13;
            else
                // You need the correct suit and an ace to start a suit pile
                return fromCard.getSuit() == Suit.values()[to - 9] && fromCard.getValue() == 1;
        }

        // Now that we have ensured that the to pile is not empty, we can peek at it.

        // If to is a suit pile
        if (!toIsStack)
            return topSuit(from) == topSuit(to) && topValue(from) + 1 == topValue(to);

        // If from is not a stack pile, note that the line above ensures that to is a stack pile
        if (!fromIsStack)
            return topColour(from) != topColour(to) && topValue(from) == topValue(to) + 1;

        // Both from and to are stack piles bellow this point.
        // Check for colour matching of the lowest moved card
        return fromCard.getSuit().getColour() != topColour(to) && fromCard.getValue() == topValue(to) + 1;
    }

    private int topColour(int pileID){
        return piles[pileID][topPos[pileID]].getSuit().getColour();
    }

    private Suit topSuit(int pileID){
        return piles[pileID][topPos[pileID]].getSuit();
    }

    private Card topCard(int pileID){
        return piles[pileID][topPos[pileID]];
    }

    private int topValue(int pileID){
        return piles[pileID][topPos[pileID]].getValue();
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