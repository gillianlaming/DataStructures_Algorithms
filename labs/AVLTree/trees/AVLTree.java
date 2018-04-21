package AVLTree.trees;

import AVLTree.nodes.AVLTreeNode;
import timing.Ticker;

import java.util.LinkedList;

public class AVLTree<T extends Comparable<T>> extends BST<T> {
	private AVLTreeNode<T> root;
	public Ticker ticker;
	public int balanceFactor; //i added
	public int height; //i added

	public AVLTree(Ticker t) {
		super();
		this.root = null;
		this.ticker = t;
		this.balanceFactor = 0;

	}

	public AVLTreeNode<T> Root() {
		return root;
	}

	/**
	 * Attempts to locate a value in the Binary Search Tree.
	 * Returns the node the node if it exists, null otherwise
	 * Utilizes a helper function that is recursively called.
	 *
	 * @param value the value to be found
	 * @return the node element if it exists/otherwise null
	 */
	//not inserting the third element
	//i wrote everything in this method
	public AVLTreeNode<T> Find(T value) {
		return findHelper(value, this.root);
	}

	// helper function for find, see above for description.
	private AVLTreeNode<T> findHelper(T value, AVLTreeNode<T> curr) {		
		if (curr == null) {
			return curr;
		}
		T rootVal = curr.getValue();
		if (rootVal.compareTo(value) == 0) { //equal
			return curr;
		}

		if (rootVal.compareTo(value) < 0) { //greater than
			return findHelper(value, curr.Right());
		}

		else { 	//less than
			return findHelper(value, curr.Left());
		}
	}

	/**
	 * Attempts to insert a value into the AVL Binary Search Tree.
	 * Returns the node that was inserted.
	 *
	 * @param value the value to be inserted
	 * @return the node element that was inserted
	 */
	public AVLTreeNode<T> Insert(T value) { 

		AVLTreeNode<T> toInsert = new AVLTreeNode<T>(value);

		this.root = insertHelper(this.root, toInsert);
		size++;
		return toInsert;


	}

	/**
	 * helper method for insertion into the AVL Binary Search Tree.
	 * Returns the (possibly different) root of the rebalanced
	 *   subtree.
	 *
	 * @param toInsert the value being inserted
	 * @param node the root of the subtree to insert into.
	 * @return the node element that was inserted
	 */
	private AVLTreeNode<T> insertHelper(AVLTreeNode<T> node, AVLTreeNode<T> toInsert){
		if (node == null) {
			toInsert.height = 1;
			return toInsert;
		}

		else {
			if (toInsert.getValue().compareTo(node.getValue()) < 0 ) {
				node.setLeft(insertHelper(node.Left(), toInsert));
				node.Left().setParent(node);

			}
			else {
				node.setRight(insertHelper(node.Right(), toInsert));
				node.Right().setParent(node);
			}
		}
		fixHeight(node);
		//	return rebalance(node);
		return node;

	}

	/** 
	 * Rebalances the subtree rooted at the input node (if necessary).
	 * Returns the (possibly different) root of the rebalanced
	 *   subtree.
	 *
	 * @param node the root of the subtree to rebalance
	 * @return the node at the root of the rebalanced subtree
	 */
	private AVLTreeNode<T> rebalance(AVLTreeNode<T> node) {
		int bal = node.getBalance();
		if (Math.abs(bal) >= 1) { 
			return node;
		}
		//else need to determine which case it is
		else if (bal < 0) { //left heavy
			//determine which case it is
			if (node.Left().Left() != null) { //case 2 (left left)
				AVLTreeNode<T> newRoot = rightRotate(node); //right rotate
				//now what do i do with the new root?
			}
			else { //case 4 (left right)
				AVLTreeNode<T> newRoot = leftRotate(rightRotate(node)); //right rotate //left rotate
			}
		}

		else if (bal > 0) { //right heavy
			if (node.Right().Right() != null) { //case 1 (right right)
				AVLTreeNode<T> newRoot = leftRotate(node); //left rotate
			}
			else { //case 3 (right left)
				AVLTreeNode<T> newRoot = rightRotate(leftRotate(node)); //left rotate, right rotate
			}
		}
		return node;
	}

	/**
	 * Performs a standard right-rotation on a subtree rooted
	 *   at the input node.
	 * This node corresponds to node 'y' on the left half of
	 *   slide 158 of the Lecture 9 notes.
	 * Returns the (possibly different) root of the rebalanced
	 *   subtree.
	 *
	 * @param parent the root of the subtree to rotate
	 * @return the new root of the rotated subtree; i.e. the 
	 *         node taking the place of parent
	 * <p>
	 */
	//i wrote code in this method
	private AVLTreeNode<T> rightRotate(AVLTreeNode<T> parent) { //again, have to deal w all other nodes involved
		AVLTreeNode<T> leftKid = parent.Left();
		AVLTreeNode<T> other = leftKid.Right();
		//find left subtree
		parent.setParent(leftKid);
		leftKid.setRight(parent);
		parent.setLeft(other);
		parent.height = Math.max(parent.getLeftHeight(), parent.getRightHeight()) + 1;
		leftKid.height = Math.max(leftKid.getLeftHeight(), leftKid.getRightHeight()) + 1;

		return leftKid;



	}

	/**
	 * Performs a standard left-rotation on a subtree rooted
	 *   at the input node.
	 * This node corresponds to node 'x' on the right half of
	 *   slide 158 of the Lecture 9 notes.
	 * Returns the (possibly different) root of the rebalanced
	 *   subtree.
	 *
	 * @param parent the root of the subtree to rotate
	 * @return the new root of the rotated subtree; i.e. the 
	 *         node taking the place of parent
	 */
	private AVLTreeNode<T> leftRotate(AVLTreeNode<T> parent) { //how do i deal w all the other nodes
		AVLTreeNode<T> rightKid = parent.Right(); 
		AVLTreeNode<T> other = rightKid.Left();

		parent.setParent(rightKid);
		rightKid.setLeft(parent);
		parent.setRight(other);

		parent.height = Math.max(parent.getLeftHeight(), parent.getRightHeight()) + 1;
		rightKid.height = Math.max(rightKid.getLeftHeight(), rightKid.getRightHeight()) + 1;

		return rightKid;

	}

	/**
	 * Recompute the height of the input node and store in its
	 *   corresponding instance variable.
	 *
	 * @param node the node whose height is computed
	 */
	//i wrote this
	private void fixHeight(AVLTreeNode<T> node){
		node.height = Math.max(node.getLeftHeight(), node.getRightHeight()) + 1; //does this take too much time
		this.balanceFactor = node.getBalance();
	}

	public int getHeight(AVLTreeNode<T> node) {
		node = this.root;
		int height = 1;
		if (root.Right() == null && root.Left() == null) {
			return height;
		}
		else {
			return  height + Math.max(node.getLeftHeight(), node.getRightHeight());
		}
	}
	//FIXME (recommended): create a helper method to determine the height of a subtree.

	public boolean isEmpty() {
		return this.root == null;
	}

	public AVLTreeNode<T> minimum() {
		return minimumOfSubtree(this.root);
	}

	public AVLTreeNode<T> maximum() {
		return maximumOfSubtree(this.root);
	}

	public AVLTreeNode<T> minimumOfSubtree(AVLTreeNode<T> curr) {
		if (curr == null) {
			return null;
		}
		while (curr.Left() != null) {
			curr = curr.Left();
		}
		return curr;
	}

	public AVLTreeNode<T> maximumOfSubtree(AVLTreeNode<T> curr) {
		if (curr == null) {
			return null;
		}
		while (curr.Right() != null) {
			curr = curr.Right();
		}
		return curr;
	}

	public LinkedList<T> InorderTraversal(AVLTreeNode<T> curr) {
		if (curr == null) {
			return new LinkedList<T>();
		}
		LinkedList<T> list = InorderTraversal(curr.Left());
		list.addLast(curr.getValue());
		list.addAll(InorderTraversal(curr.Right()));
		return list;
	}

	public void PrintTree() {
		InorderTraversal(this.root);
	}

}
