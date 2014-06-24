package eu.reply.androidlabs.catalog.demo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import eu.reply.androidlabs.catalog.demo.tree.TreeNode;

/**
 * <p>Adapter backed by a list of arbitrary objects and used to feed a ListView which should show 
 * information from a Tree-type data source. By default this class is not responsible to fill the 
 * views of the items from the list, this task will be made by an instance of a class that implements 
 * the {@link TreeListItemAdapter} interface, which should be provided by the client of this class.</p>
 * 
 * @author Diego Palomar (d.palomar@replyltd.co.uk)
 * @version 1.0.0
 *
 * @param <T> Type of the data stored in a tree's node.
 */
public class TreeListAdapter<T> extends ArrayAdapter<TreeNode<T>> implements OnItemClickListener
{
	/**
	 * Interface that must be implemented by the client to fill the views of the ListView's cells.
	 * This interface defines the methods required to allow a concrete class display the tree in a 
	 * custom way depending of the cell-type that has to be render (leaf, normal category or ancestor).
	 * 
	 * @param <T> Type of the data that will be displayed in the cells of the ListView.
	 */
	public interface TreeListItemAdapter<T>
	{
		/**
		 * Returns the resource ID for an XML layout resource containing the views 
		 * that make up a cell of the ListView.
		 * 
		 * @return Id of the layout used to display a cell.
		 */
		public int getListItemLayoutResId();
	
		/**
		 * Create a ViewHolder/Wrapper that contains references to the views that make up a cell of the ListView.
		 * 
		 * @param rootView Root of the view hierarchy that represents a cell.
		 * 
		 * @return A view holder containing references to all views that make up a cell of the ListView.
		 */
		public Object createViewHolder(View rootView);
		
		/**
		 * Display the node's data in the views referenced by the viewholder.
		 * The cell to be render does not have sub-categories (It is a leaf).
		 * 
		 * @param viewHolder Root of the view hierarchy that represents a cell.
		 * @param node Node that contains the information to be displayed.
		 */
		public void displayFinalCategory(Object viewHolder, TreeNode<T> node);
		
		/**
		 * Display the node's data in the views referenced by the viewholder. 
		 * The cell to be render is the parent of the current leafs of the tree.
		 * 
		 * @param viewHolder Root of the view hierarchy that represents a cell.
		 * @param node Node that contains the information to be displayed.
		 */
		public void displayCurrentParentCategory(Object viewHolder, TreeNode<T> node);
		
		/**
		 * Display the node's data in the views referenced by the viewholder. 
		 * The cell to be render is an ancestor of the current parent category selected.
		 * 
		 * @param viewHolder Root of the view hierarchy that represents a cell.
		 * @param node Node that contains the information to be displayed.
		 */
		public void displayCategoryAncestor(Object viewHolder, TreeNode<T> node);
	}
	
	
    /**
     * Interface definition for a callback to be invoked when a leaf of the tree has been selected by the user.
     * 
     * @param <T> Type of the data stored into the item selected.
     */
	public interface OnLeafSelectedListener<T>
	{
		/**
		 * Called when the user has selected a leaf of the tree (category without sub-categories).
		 *  
		 * @param item The final category selected by the user.
		 */
		public void onLeafSelected(TreeNode<T> item);
	}
	
	
	private Context mContext;
	
	/**
	 * Root node of tree that is represented in the ListView.
	 */
	private TreeNode<T> mRootNode;
	
	/**
	 * Adapter responsible to display the information of the data source in the cells of the ListView.
	 */
	private TreeListItemAdapter<T> mListItemAdapter;
	
    /**
     * Contains the list of objects that represent the data of this Adapter.
     */
	private List<TreeNode<T>> mDataSourceList;
	
	/**
	 * Listener used to dispatch the event produced when the user select a leaf of the tree.
	 */
	private OnLeafSelectedListener<T> mLeafSelectionListener;
	
	/**
	 * The current maximum depth of the tree, that is the distance between the
     * root of the tree and one the leaf in the current tree state.
	 */
	private int mCurrentMaxDepth;
	
	 
	/**
	 * Constructor
	 * 
	 * @param context The current context.
	 * @param listItemAdapter Adapter responsible to display the information of the data source in the cells of the ListView. 
	 * @param rootNode Root node of tree that will be represented in the ListView.
	 * @param leafSelectionListener Listener that will be called when the user selected a leaf of the tree.
	 */
	public TreeListAdapter(Context context, TreeListItemAdapter<T> listItemAdapter,
			TreeNode<T> rootNode, OnLeafSelectedListener<T> leafSelectionListener)
	{
		super(context, android.R.layout.simple_list_item_1);
		
		mContext = context;
		
		mListItemAdapter = listItemAdapter;
		
		mLeafSelectionListener = leafSelectionListener;
		
		mRootNode = rootNode;
		
		initDataSource();
	}
	
	
	private void initDataSource()
	{
		int initMaxDepth = mRootNode.getDepthLevel() + 1;
		
		mCurrentMaxDepth = initMaxDepth;
		
		mDataSourceList = new ArrayList<TreeNode<T>>();
		
		mDataSourceList.add(mRootNode);
		mDataSourceList.addAll(mRootNode.getChildren());
	}
	
	
	@Override
	public TreeNode<T> getItem(int position) 
	{
		return mDataSourceList.get(position);
	}
	
	
	@Override
	public int getCount() 
	{
		return mDataSourceList.size();
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Object viewHolder;
		
	    if (convertView == null)
	    {
	        convertView = LayoutInflater.from(mContext).inflate(mListItemAdapter.getListItemLayoutResId(), null, false);
	    	
	    	viewHolder = mListItemAdapter.createViewHolder(convertView);
	        
	        convertView.setTag(viewHolder);
	    } 
	    else 
	    {
	    	viewHolder = convertView.getTag();
	    }

	    TreeNode<T> category = getItem(position);
	    
	    if (category.getDepthLevel() == mCurrentMaxDepth)
	    {
	    	// Leafs of the tree
	    	mListItemAdapter.displayFinalCategory(viewHolder, category);
	    }
	    else if (category.getDepthLevel() == mCurrentMaxDepth - 1)
	    {
	    	// Current category selected
	    	mListItemAdapter.displayCurrentParentCategory(viewHolder, category);
	    } 
	    else
	    {
	    	// Ancestors of the current category selected
	    	mListItemAdapter.displayCategoryAncestor(viewHolder, category);
	    }

	    return convertView;
	}

	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		TreeNode<T> categorySelected = mDataSourceList.get(position);
		
		int levelSelected = categorySelected.getDepthLevel();
		
		// Return if the user has selected the current parent category (to avoid unnecessary processing
		// because the current tree representation would not change).
		if (categorySelected.getDepthLevel() == mCurrentMaxDepth - 1) return;
		
		if (categorySelected.hasChildren())
		{
			// User has selected a category with sub-categories. Delete from the data source list all 
			// categories which depth level is greater or equal to the level of the category selected so 
			// from now we'll have in the list  only the category selected and its ancestors (before adding its children).
			
			List<TreeNode<T>> itemsToRemove = new ArrayList<TreeNode<T>>();
				
			for (TreeNode<T> node : mDataSourceList)
			{
				if (node.getDepthLevel() >= levelSelected && node != categorySelected)
				{
					itemsToRemove.add(node);
				}
			}
			
			mDataSourceList.removeAll(itemsToRemove);
			
			mDataSourceList.addAll(categorySelected.getChildren());
			
			mCurrentMaxDepth = levelSelected + 1;
			
			notifyDataSetChanged();
		}
		else
		{
			// User has selected a leaf (category without sub-categories). 
			mLeafSelectionListener.onLeafSelected(categorySelected);
		}
	}
	
	
	/**
	 * <p>Causes go back in the tree navigation (back step in the user navigation history) if the
	 * user has moved deep into the tree.</p>
	 * 
	 * @return True if the data source was updated, false otherwise.
	 */
	public boolean goBackOnNavigationHistory()
	{
		// Because the minimum tree depth is 0 and initial depth is 1, if the current tree 
		// depth is greater or equal than 2 implies that the user has moved into the tree.
		
		boolean treeUpdated = false;
		
		int newLevelCategorySelected = mCurrentMaxDepth - 2;
		
		if (newLevelCategorySelected >= 0)
		{
			this.onItemClick(null, null, newLevelCategorySelected, newLevelCategorySelected);
			treeUpdated = true;
		}
		
		return treeUpdated;
	}
}
