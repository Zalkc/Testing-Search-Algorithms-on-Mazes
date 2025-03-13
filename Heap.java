import java.util.Comparator;

public class Heap<T> implements PriorityQueue<T>{
    //attributes
    private Comparator<T> comparator;
    private int size;
    private Node<T> root;
    private Node<T> last;

    //inner class
    private static class Node<T>{
        //attributes
        T data;
        Node<T> left;
        Node<T> right;
        Node<T> parent;
        public Node(T data, Node<T> left, Node<T> right, Node<T> parent){
            //constructor
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }
    }

    public Heap(){
        //constructor
        this(null, false);
    }

    public Heap(boolean maxHeap){
        //constructor
        this(null, maxHeap);
    }

    public Heap(Comparator<T> comparator){
        //constructor
        this(comparator, false);
    }

    public Heap(Comparator<T> comparator, boolean maxHeap){
        //constructor
        if(comparator == null){
            this.comparator = new Comparator<T>(){
                @Override
                public int compare(T o1, T o2){
                    return ((Comparable<T>) o1).compareTo(o2);
                }
            };
        }

        else{
            this.comparator = comparator;
        }

        if(maxHeap){
            this.comparator = new Comparator<T>()
            {
                @Override
                public int compare(T o1, T o2)
                {
                    return Heap.this.comparator.compare(o2, o1);
                }
            };
        }

    }

    private void swap(Node<T> node1, Node<T> node2) {
        //method swaps the data of the given nodes
        T temp = node1.data;
        node1.data = node2.data;
        node2.data = temp;
    }

    private void bubbleUp(Node<T> curNode){
        //method bubbles up the given node
        if (curNode != root) {
            T curData = curNode.data;
            T parData = curNode.parent.data;
            if (comparator.compare(curData, parData) < 0){
                swap(curNode, curNode.parent);
                bubbleUp(curNode.parent);
            }
        }
        else{
            return;
        }

    }

    private void bubbleDown(Node<T> curNode){
        //method bubbles down the given node
        if (curNode.left == null){
            return;
        }
        else if(curNode.right == null){
            if(comparator.compare(curNode.left.data, curNode.data) < 0){
                swap(curNode, curNode.left);
                bubbleDown(curNode.left);
            }
        }
        else{
            if(comparator.compare(curNode.left.data, curNode.right.data) < 0){
                if (comparator.compare(curNode.left.data, curNode.data) < 0){
                    swap(curNode, curNode.left);
                    bubbleDown(curNode.left);
                }
            }
            else{
                if (comparator.compare(curNode.right.data, curNode.data) < 0){
                    swap(curNode, curNode.right);
                    bubbleDown(curNode.right);
                }
            }
        }
    }

    public void offer(T item){
        //method adds the given item into the queue
        if (size == 0){
            root = new Node<T>(item, null, null, null);
            last = root;
        }
        else if(size % 2 == 0){
            last.parent.right = new Node<T>(item, null, null, last.parent);
            last = last.parent.right;
        }
        else{
            Node<T> curNode = last;
            while(curNode != root && curNode == curNode.parent.right){
                curNode = curNode.parent;
            }
            if(curNode != root){
                curNode = curNode.parent.right;
            }
            while(curNode.left != null){
                curNode = curNode.left;
            }
            curNode.left = new Node<T>(item, null, null, curNode);
            last = curNode.left;
        }
        bubbleUp(last);
        size++;
    }

    public T poll() {
        //method returns and removes the item of greatest priority in the queue
        if (size == 0) {
            return null;
        }
        T removed = root.data;
        if (size == 1) {
            root = null;
            last = null;
            size--;
            return removed;
        }
        swap(root, last);
        if (size % 2 == 1) {
            last.parent.right = null;
            last = last.parent.left;
        } else {
            Node<T> curNode = last;
            while (curNode != root && curNode == curNode.parent.left) {
                curNode = curNode.parent;
            }
            if (curNode != root) {
                curNode = curNode.parent.left;
            }
            while (curNode.right != null) {
                curNode = curNode.right;
            }
            last.parent.left = null;
            last = curNode;
        }
        size--;
        bubbleDown(root);
        return removed;
    }
    
    public int size(){
        //method returns the number of items in the queue
        return size;
    }

    public T peek(){
        //method returns the item of greatest priority in the queue
        return root.data;
    }

    public void updatePriority(T item){
        //method updates the priority of the given item
        Node<T> node = nodeSearch(item);
        updatePriority(item, node);
    }

    private void updatePriority(T item, Node<T> node){
        //method updates the priority of the given item
        if (comparator.compare(node.data, node.parent.data) < 0){
            swap(node, node.parent);
            node = node.parent;
        }
        else if (comparator.compare(node.data, node.left.data) > 0){
            swap(node, node.left);
            node = node.left;
        }
        else if (comparator.compare(node.data, node.right.data) > 0){
            swap(node, node.right);
            node = node.right;
        }
        else{
            return;
        }
        updatePriority(item, node);
    }

    private Node<T> nodeSearch(T item){
        //method returns the node with the given item
        if (size != 0){
            return nodeSearch(item, root);
        }
        else{
            return null;
        }
    }

    private Node<T> nodeSearch(T item, Node<T> curNode){
        //method returns the node with the given item
        Node<T> node;
        if (curNode == null){
            return null;
        }
        if (comparator.compare(curNode.data, item) == 0){
            return curNode;
        }
        node = nodeSearch(item, curNode.left);
        node = nodeSearch(item, curNode.right);
        return node;
    }
}