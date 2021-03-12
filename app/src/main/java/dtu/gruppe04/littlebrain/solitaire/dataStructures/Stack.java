package dtu.gruppe04.littlebrain.solitaire.dataStructures;

public class Stack<T> implements StackADT<T> {

    protected Node<T> head;

    protected int size = 0;

    public Stack(){}

    public void push(T value) {
        if (head == null) {
            head = new Node(value, null);
        } else {
            Node<T> latest = new Node<T>(value,head);
            head = latest;
        }
        size++;
    }

    public T pop() {
        if(isEmpty()){
            return null;
        }
        size--;
        T temp = head.getValue();
        head = head.getNext();
        return temp;
    }

    public T peek(){
        if(isEmpty()){
            return null;
        }
        return head.getValue();
    }

    public void clear(){
        head = null;
        size = 0;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public int size(){
        return size;
    }

    public void reverse(){
        reverse(head, null);
    }

    private void reverse(Node<T> node, Node<T> prev){
        if(node != null)
        {
            reverse(node.getNext(), node);
            if(node.getNext() == null)
            {
                head = node;
            }
            node.setNext(prev);
        }
    }

    public Stack<T> reverseCopy(){
        Stack<T> temp = new Stack<T>();
        for(Node<T> node = head; node != null; node = node.getNext()){
            temp.push(node.getValue());
        }
        return temp;
    }

    public Stack<T> copy(){
        Stack<T> temp = reverseCopy();
        temp.reverse();
        return temp;
    }

    public void appendStack(Stack<T> stack){
        if(stack == null || stack.isEmpty()){
            return;
        }
        //So that the given stack is unmodified. It is reversed so that the
        //the elements are added in the proper order.
        Stack<T> temp = stack.reverseCopy();
        while(!temp.isEmpty()){
            push(temp.pop()); //We append each element of the
        }
    }

}
