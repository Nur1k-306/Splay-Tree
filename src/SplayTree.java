public class SplayTree<T extends Comparable<T>> implements Tree<T> {

    private Node<T> root;
    private long insertOperations;
    private long searchOperations;
    private long deleteOperations;

    public Node<T> getRoot() {
        return root;
    }

    public long getInsertOperations() {
        return insertOperations;
    }

    public long getSearchOperations() {
        return searchOperations;
    }

    public long getDeleteOperations() {
        return deleteOperations;
    }

    @Override
    public Node<T> insert(T data) {
        root = insert(root, new Node<>(data));
        return root;
    }

    private Node<T> insert(Node<T> node, Node<T> nodeToInsert) {
        if (node == null) {
            return nodeToInsert;
        }
        int comparisonResult = nodeToInsert.getData().compareTo(node.getData());
        if (comparisonResult < 0) {
            node.setLeftChild(insert(node.getLeftChild(), nodeToInsert));
            node.getLeftChild().setParent(node);
        } else if (comparisonResult > 0) {
            node.setRightChild(insert(node.getRightChild(), nodeToInsert));
            node.getRightChild().setParent(node);
        }
        insertOperations++; // Увеличиваем счетчик операций при каждом вызове метода insert
        return node;
    }

    @Override
    public Node<T> delete(T data) {
        root = delete(data, root);
        return root;
    }

    private Node<T> delete(T data, Node<T> node) {
        if (node == null) return null;

        int comparisonResult = data.compareTo(node.getData());
        if (comparisonResult < 0) {
            node.setLeftChild(delete(data, node.getLeftChild()));
            if (node.getLeftChild() != null) node.getLeftChild().setParent(node);
        } else if (comparisonResult > 0) {
            node.setRightChild(delete(data, node.getRightChild()));
            if (node.getRightChild() != null) node.getRightChild().setParent(node);
        } else {
            // One Child or Leaf Node (no children)
            if (node.getLeftChild() == null) return node.getRightChild();
            else if (node.getRightChild() == null) return node.getLeftChild();
            // Two Children
            node.setData(getMax(node.getLeftChild()));
            node.setLeftChild(delete(node.getData(), node.getLeftChild()));
            if (node.getLeftChild() != null) node.getLeftChild().setParent(node);
        }
        deleteOperations++; // Увеличиваем счетчик операций при каждом вызове метода delete
        return node;
    }

    @Override
    public Node<T> find(T data) {
        Node<T> node = root;
        while (node != null) {
            searchOperations++; // Увеличиваем счетчик операций при каждой итерации поиска
            if (node.getData().compareTo(data) == 0) {
                splay(node);
                return node;
            }
            node = data.compareTo(node.getData()) < 0 ? node.getLeftChild() : node.getRightChild();
        }
        return null;
    }

    @Override
    public Node<T> findRecursively(T data) {
        return find(root, data);
    }

    public Node<T> find(Node<T> node, T data) {
        if (node != null) {
            if (node.getData().compareTo(data) == 0) {
                splay(node);
                return node;
            }
            Node<T> nextNode = data.compareTo(node.getData()) > 0 ? node.getRightChild() : node.getLeftChild();
            find(nextNode, data);
        }
        return null;
    }

    private void splay(Node<T> node) {
        while(node != root) {
            Node<T> parent = node.getParent();
            if (node.getGrandParent() == null) {
                if (node.isLeftChild()) {
                    rotateRight(parent);
                } else {
                    rotateLeft(parent);
                }
            } else if (node.isLeftChild() && parent.isLeftChild()) {
                rotateRight(node.getGrandParent());
                rotateRight(parent);
            } else if (node.isRightChild() && parent.isRightChild()) {
                rotateLeft(node.getGrandParent());
                rotateLeft(parent);
            } else if (node.isLeftChild() && parent.isRightChild()) {
                rotateRight(parent);
                rotateLeft(parent);
            } else {
                rotateLeft(parent);
                rotateRight(parent);
            }
        }
    }

    private void rotateRight(Node<T> node) {
        Node<T> leftNode = node.getLeftChild();
        if (leftNode == null) return; // Добавим проверку на null

        node.setLeftChild(leftNode.getRightChild());
        if (node.getLeftChild() != null) {
            node.getLeftChild().setParent(node);
        }
        updateChildrenOfParentNode(node, leftNode);
        leftNode.setParent(node.getParent());
        leftNode.setRightChild(node);
        node.setParent(leftNode);
    }

    private void rotateLeft(Node<T> node) {
        Node<T> rightNode = node.getRightChild();
        if (rightNode == null) return; // Добавляем проверку на null

        node.setRightChild(rightNode.getLeftChild());
        if (node.getRightChild() != null) {
            node.getRightChild().setParent(node);
        }
        updateChildrenOfParentNode(node, rightNode);
        rightNode.setParent(node.getParent());
        rightNode.setLeftChild(node);
        node.setParent(rightNode);
    }

    private void updateChildrenOfParentNode(Node<T> node, Node<T> tempNode) {
        if (node.getParent() == null) {
            root = tempNode;
        } else if (node.isLeftChild()) {
            node.getParent().setLeftChild(tempNode);
        } else {
            node.getParent().setRightChild(tempNode);
        }
    }

    @Override
    public void traverse() {
        traverseInOrder(root);
    }

    private void traverseInOrder(Node<T> node) {
        if (node != null) {
            traverseInOrder(node.getLeftChild());
            System.out.println(node);
            traverseInOrder(node.getRightChild());
        }
    }

    @Override
    public T getMax() {
        if (isEmpty()) {
            return null;
        }
        return getMax(root);
    }

    private T getMax(Node<T> node) {
        if (node.getRightChild() != null) {
            return getMax(node.getRightChild());
        }
        return node.getData();
    }

    @Override
    public T getMin() {
        if (isEmpty()) {
            return null;
        }
        return getMin(root);
    }

    private T getMin(Node<T> node) {
        if (node.getLeftChild() != null) {
            return getMin(node.getLeftChild());
        }
        return node.getData();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }
}