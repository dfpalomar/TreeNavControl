package eu.reply.androidlabs.catalog.demo.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a general purpose node free to have any number of children.
 * 
 * @author Diego Palomar
 * @version 1.0.0
 *
 * @param <T> Type of the data stored into the node.
 */
public class TreeNode<T>
{ 
	/**
	 * Data stored in the node.
	 */
    public T mData;
    
    /**
     * Contains the list of children
     */
    public List<TreeNode<T>> mChildrenList;

    /**
     * Parent of the node. 
     */
    private TreeNode<T> mParent;
    
    /**
     * Depth level of the node into the tree. For this value to be assigned should 
     * first calculated the level traversal of the tree to which it belongs.
     */
    private int mDepthLevel;
    
    
    /**
     * Simple constructor used to create a node.
     */
    public TreeNode() 
    {
        super();
        mChildrenList = new ArrayList<TreeNode<T>>();
    }

    /**
     * Simple constructor used to create a node.
     * 
     * @param data The data to be stored in the node.
     */
    public TreeNode(T data) 
    {
        this();
        setData(data);
    }
    
    public TreeNode<T> getParent() 
    {
        return mParent;
    }

    public void setParent(TreeNode<T> parent) 
    {
        mParent = parent;
    }

    public int getNumberOfChildren() 
    {
        return mChildrenList.size();
    }

    public boolean hasChildren() 
    {
        return !mChildrenList.isEmpty();
    }
    
    public List<TreeNode<T>> getChildren() {
        return mChildrenList;
    }
    
    public void setChildren(List<TreeNode<T>> children)
    {
        for (TreeNode<T> child : children)
        {
            child.setParent(this);
        }
            
        mChildrenList = children;
    }

    public void addChild(TreeNode<T> child) 
    {
        child.setParent(this);
        mChildrenList.add(child);
    }

    public void addChildAt(int index, TreeNode<T> child) throws IndexOutOfBoundsException 
    {
        child.setParent(this);
        mChildrenList.add(index, child);
    }

    public void removeChildren() 
    {
        mChildrenList.clear();
    }

    public void removeChildAt(int index) throws IndexOutOfBoundsException 
    {
        mChildrenList.remove(index);
    }

    public TreeNode<T> getChildAt(int index) throws IndexOutOfBoundsException 
    {
        return mChildrenList.get(index);
    }

    public T getData() 
    {
        return mData;
    }

    public void setData(T data) 
    {
        mData = data;
    }
    
    public int getDepthLevel() 
    {
        return mDepthLevel;
    }

    public void setDepthLevel(int level)
    {
        mDepthLevel = level;
    }

    public String toString() 
    {
        StringBuilder objectDescription = new StringBuilder();
        objectDescription.append(mData.toString()).append(" (L");
        objectDescription.append(mDepthLevel).append(")");
        
        return objectDescription.toString();
    }

    public boolean equals(TreeNode<T> node) 
    {
        return mData.equals(node.getData());
    }
}