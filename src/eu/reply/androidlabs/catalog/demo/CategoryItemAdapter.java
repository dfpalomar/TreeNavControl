package eu.reply.androidlabs.catalog.demo;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import eu.reply.androidlabs.catalog.demo.adapter.TreeListAdapter.TreeListItemAdapter;
import eu.reply.androidlabs.catalog.demo.tree.TreeNode;

/**
 * 
 * @author Diego Palomar (d.palomar@replyltd.co.uk)
 *
 * @param <T> Type of the data that will be displayed in the cells of the list.
 */
public class CategoryItemAdapter<T> implements TreeListItemAdapter<T>
{
	@Override
	public int getListItemLayoutResId() 
	{
		return R.layout.list_view_item;
	}
	
	@Override
	public Object createViewHolder(View rootView)
	{
		TextView categoryView = (TextView) rootView.findViewById(R.id.dataView);
		
		CategoryViewHolder categoryViewHolder = new CategoryViewHolder(categoryView);
		
		return categoryViewHolder;
	}

	@Override
	public void displayFinalCategory(Object viewHolder, TreeNode<T> node) 
	{
		CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;
		
		TextView categoryView = categoryViewHolder.categoryView;
		
    	categoryView.setBackgroundResource(R.drawable.list_item_leaf_selector);
    	categoryView.setTextColor(Color.BLACK);
    	
    	if (node.hasChildren()) 
    		categoryView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    	else
    		categoryView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.arrow,0);
    	
    	categoryView.setText("\t\t" + node.toString());
	}

	@Override
	public void displayCurrentParentCategory(Object viewHolder, TreeNode<T> node) 
	{	
		CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;
		
		TextView categoryView = categoryViewHolder.categoryView;
		
    	categoryView.setBackgroundResource(R.drawable.list_item_category_selector);
    	categoryView.setTextColor(Color.RED);
    	categoryView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    	
    	categoryView.setText(node.toString());
	}

	@Override
	public void displayCategoryAncestor(Object viewHolder, TreeNode<T> node) 
	{	
		CategoryViewHolder categoryViewHolder = (CategoryViewHolder) viewHolder;
		
		TextView categoryView = categoryViewHolder.categoryView;
		
    	categoryView.setBackgroundResource(R.drawable.list_item_category_selector);
    	categoryView.setTextColor(Color.BLACK);
    	categoryView.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
    	
    	categoryView.setText(node.toString());
	}
	
	
	private static class CategoryViewHolder
	{	
	    public final TextView categoryView;

	    public CategoryViewHolder(TextView categoryView) 
	    {
	        this.categoryView = categoryView;
	    }
	}
}
