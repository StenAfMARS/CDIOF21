package dtu.gruppe04.littlebrain.solitaire.card;

import dtu.gruppe04.littlebrain.solitaire.dataStructures.Stack;

public class StackOfCards extends Stack<Card> {

    public StackOfCards(){}

    public static StackOfCards randomDeck(){
        StackOfCards deck = new StackOfCards();
        deck.fillBySuit();
        deck.shuffle();
        return deck;
    }

    public void fillBySuit(){
        for(Suit suit : Suit.values()){
            for(int i = 1; i < 14; i++){
                push(new Card(suit, i, false));
            }
        }
    }

    public void shuffle(){

    }
}
