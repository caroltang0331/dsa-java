package edu.emory.cs.tree.balanced;


import edu.emory.cs.tree.BinaryNode;

/** @author Jinho D. Choi */
public class BalancedBinarySearchTreeQuiz<T extends Comparable<T>> extends AbstractBalancedBinarySearchTree<T, BinaryNode<T>> {
    @Override
    public BinaryNode<T> createNode(T key) {
        return new BinaryNode<>(key);
    }

    @Override
    protected void balance(BinaryNode<T> node) {
        if (node.hasParent() && node.getParent().hasParent() && node.getGrandParent().hasBothChildren()) {
        if (node.getGrandParent().isRightChild(node.getParent()) && !node.getParent().hasBothChildren() && !node.getUncle().hasBothChildren()){
            BinaryNode<T> uncle = node.getUncle();
            BinaryNode<T> grandparent = node.getGrandParent();
            if (node.getParent().isLeftChild(node)) rotateRight(node.getParent());
            rotateLeft(grandparent);
            if (uncle.hasRightChild()) rotateLeft(uncle);
            rotateRight(grandparent);

        }}
    }
}