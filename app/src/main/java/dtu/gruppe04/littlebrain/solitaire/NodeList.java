package dtu.gruppe04.littlebrain.solitaire;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class NodeList<T> implements Iterable<T> {
    private int count;
    private Node<T> head;

    public void prepend(T value){
        head = insertNode(new Node<T>(value));
    }

    public void append(T value){
        insertNode(new Node<T>(value));
    }

    public int getCount() {
        return count;
    }

    public void cut(int index, NodeList<T> other){
        int moveCount = other.count - index;
        Node<T> moveHead = other.getNode(index);

        other.head.swapPrev(moveHead);
        other.count -= moveCount;

        if (other.count == 0)
            other.head = null;

        if (count == 0)
            head = moveHead;
        else {
            head.swapPrev(moveHead);
        }

        count += moveCount;
    }
    public void reverseNode() {
        if (count != 0) {

            Node<T> cur = head;
            Node<T> temp;
            do {
                temp = cur.prev;
                cur.prev = cur.next;
                cur.next = temp;

                cur = cur.next;
            } while (cur != head);
            head = head.next;
        }

    }
    public T peek(int index){
        return getNode(index).value;
    }

    private Node<T> insertNode(Node<T> node){
        if (count == 0)
            head = node.setNext(node).setPrev(node);
        else
            node.setPrev(head.prev).setNext(head);

        count++;

        return node;
    }

    private Node<T> getNode(int index) {
        if (index >= 0) {

            Node<T> cur = head;

            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }

            return cur;
        }
        else
        {
            Node<T> cur = head;

            for (int i = 0; i > index; i--) {
                cur = cur.prev;
            }

            return cur;
        }
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return new NodeListIterator(head);
    }

    public class NodeListIterator implements Iterator<T>{

        Node<T> startNode;
        Node<T> nextNode;

        public NodeListIterator(Node<T> head) {
            startNode = nextNode = head;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public T next() {
            T out = nextNode.value;

            if (nextNode.next == startNode)
                nextNode = null;
            else
                nextNode = nextNode.next;

            return out;
        }
    }

    private class Node<T> {
        private Node<T> prev;
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node<T> setPrev(Node<T> prev){
            this.prev = prev;

            if (prev != null)
                prev.next = this;

            return this;
        }

        public Node<T> setNext(Node<T> next){
            this.next = next;

            if (next != null)
                next.prev = this;

            return this;
        }

        public void swapPrev(Node<T> other){
            Node<T> temp = this.prev;

            this.setPrev(other.prev);
            other.setPrev(temp);
        }
    }
}
