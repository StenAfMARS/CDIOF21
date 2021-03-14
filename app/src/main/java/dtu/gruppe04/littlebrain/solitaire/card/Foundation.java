package dtu.gruppe04.littlebrain.solitaire.card;

public class Foundation extends StackOfCards {

    public Foundation(){}

    @Override
    public void push(Card card) {
        if (isEmpty()){
            if (card.getValue()== 1) {
                super.push(card);
            } else {
                throw new IllegalArgumentException(); //TODO "Implement error handling and logging"
            }
        } else {
            if (card.getValue() == peek().getValue() + 1
                            && card.getSuit() == peek().getSuit()) {

                super.push(card);
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
