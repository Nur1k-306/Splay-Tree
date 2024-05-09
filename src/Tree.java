public interface Tree<T extends Comparable<T>> {

    Node<T> insert(T data);

    Node<T> delete(T data);

    Node<T> find(T data);

    Node<T> findRecursively(T data);

    void traverse();

    T getMax();

    T getMin();

    boolean isEmpty();

}