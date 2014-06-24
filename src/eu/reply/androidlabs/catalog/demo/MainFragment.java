package eu.reply.androidlabs.catalog.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import eu.reply.androidlabs.catalog.demo.adapter.TreeListAdapter;
import eu.reply.androidlabs.catalog.demo.adapter.TreeListAdapter.OnLeafSelectedListener;
import eu.reply.androidlabs.catalog.demo.tree.NAryTree;
import eu.reply.androidlabs.catalog.demo.tree.TreeNode;

public class MainFragment extends Fragment implements OnClickListener
{
	private static final String TAG = "CustomTreeControl";
	
	private TextView mTreeLevelTraversalView;
	private Button mExecButton;
	private ListView mListView;
	
	private NAryTree<String> mTreeDS;
	
	private TreeListAdapter<String> mListAdapter;
	
	public MainFragment() { }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{	
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);
		
		setupViews(rootView);
		
		mTreeDS = createMockTree();
		mTreeDS.calcNodesLevels();
		
		mListAdapter = new TreeListAdapter<String>(getActivity(), new CategoryItemAdapter<String>(), 
				mTreeDS.getRoot(), leafSelectedListener);
		
		mListView.setAdapter(mListAdapter);
		mListView.setOnItemClickListener(mListAdapter);

		return rootView;
	}
	
	private void setupViews(View rootView)
	{
		mTreeLevelTraversalView = (TextView) rootView.findViewById(R.id.resultsView);
		mExecButton  			= (Button) rootView.findViewById(R.id.execButton);
		mListView   		    = (ListView) rootView.findViewById(R.id.treeeListView);
		
		mExecButton.setOnClickListener(this);
	}
	
	private OnLeafSelectedListener<String> leafSelectedListener = new OnLeafSelectedListener<String>() 
	{
		@Override
		public void onLeafSelected(TreeNode<String> item) {
			Log.i(TAG, item.toString());
			Toast.makeText(getActivity(), item.toString(), Toast.LENGTH_SHORT).show();
		}
	};
	

	@Override
	public void onClick(View v)
	{
		mTreeLevelTraversalView.setText(mTreeDS.calcNodesLevels().toString());
	}
	
	
	public NAryTree<String> createMockTree()
	{
		TreeNode<String> one = new TreeNode<String>("Fresh");
		TreeNode<String> one_dot_one = new TreeNode<String>("Vegetables");
		TreeNode<String> one_dot_one_one = new TreeNode<String>("Herbs");
		TreeNode<String> one_dot_one_two = new TreeNode<String>("Cut Vegetables");
		TreeNode<String> one_dot_one_three = new TreeNode<String>("Fresh Aperitifs");
		one_dot_one.addChild(one_dot_one_one);
		one_dot_one.addChild(one_dot_one_two);
		one_dot_one.addChild(one_dot_one_three);
		TreeNode<String> one_dot_two = new TreeNode<String>("Fruit");
		TreeNode<String> one_dot_two_one = new TreeNode<String>("Herbs");
		TreeNode<String> one_dot_two_three = new TreeNode<String>("Fresh Aperitifs");
		one_dot_two.addChild(one_dot_two_one);
		one_dot_two.addChild(one_dot_two_one);
		one_dot_two.addChild(one_dot_two_three);
		TreeNode<String> one_dot_three = new TreeNode<String>("Fish");
		TreeNode<String> one_dot_three_one = new TreeNode<String>("Sushi");
		TreeNode<String> one_dot_three_two = new TreeNode<String>("Seafood");
		TreeNode<String> one_dot_three_three = new TreeNode<String>("Smoked Fish");
		one_dot_three.addChild(one_dot_three_one);
		one_dot_three.addChild(one_dot_three_two);
		one_dot_three.addChild(one_dot_three_three);
		
		one.addChild(one_dot_one);
		one.addChild(one_dot_two);
		one.addChild(one_dot_three);
	
		TreeNode<String> two = new TreeNode<String>("Salty");
		TreeNode<String> two_dot_one = new TreeNode<String>("Appetizers");
		TreeNode<String> two_dot_one_dot_one = new TreeNode<String>("Chips");
		TreeNode<String> two_dot_one_dot_two = new TreeNode<String>("Sausages");
		two_dot_one.addChild(two_dot_one_dot_one);
		two_dot_one.addChild(two_dot_one_dot_two);
		TreeNode<String> two_dot_two = new TreeNode<String>("Pastas & Rices");
		TreeNode<String> two_dot_two_dot_one = new TreeNode<String>("Pastas");
		TreeNode<String> two_dot_two_dot_two = new TreeNode<String>("Rices");
		TreeNode<String> two_dot_two_dot_three = new TreeNode<String>("Snacks");
		two_dot_two.addChild(two_dot_two_dot_one);
		two_dot_two.addChild(two_dot_two_dot_two);
		two_dot_two.addChild(two_dot_two_dot_three);
		
		two.addChild(two_dot_one);
		two.addChild(two_dot_two);
				
		TreeNode<String> three = new TreeNode<String>("Sweet Food");
		TreeNode<String> three_dot_one = new TreeNode<String>("Breakfast");
		TreeNode<String> three_dot_one_one = new TreeNode<String>("Cereales");
		TreeNode<String> three_dot_one_two = new TreeNode<String>("Honeys");
		TreeNode<String> three_dot_one_three = new TreeNode<String>("Toasts");
		three_dot_one.addChild(three_dot_one_one);
		three_dot_one.addChild(three_dot_one_two);
		three_dot_one.addChild(three_dot_one_three);
		
		TreeNode<String> three_dot_two = new TreeNode<String>("Candy");
		TreeNode<String> three_dot_two_one = new TreeNode<String>("Marzipan");
		TreeNode<String> three_dot_two_two = new TreeNode<String>("Chewing Gum");
		TreeNode<String> three_dot_two_three = new TreeNode<String>("Pastilles");
		three_dot_two.addChild(three_dot_one_one);
		three_dot_two.addChild(three_dot_two_two);
		three_dot_two.addChild(three_dot_two_three);
		
		TreeNode<String> three_dot_three = new TreeNode<String>("Desserts");
		TreeNode<String> three_dot_three_one = new TreeNode<String>("Flour");
		TreeNode<String> three_dot_three_two = new TreeNode<String>("Sugar");
		TreeNode<String> three_dot_three_three = new TreeNode<String>("Preparations");
		three_dot_three.addChild(three_dot_three_one);
		three_dot_three.addChild(three_dot_three_two);
		three_dot_three.addChild(three_dot_three_three);
		
		three.addChild(three_dot_one);
		three.addChild(three_dot_two);
		three.addChild(three_dot_three);
		
		TreeNode<String> four = new TreeNode<String>("Drinks");
		TreeNode<String> four_dot_one = new TreeNode<String>("Water");
		TreeNode<String> four_dot_one_dot_one = new TreeNode<String>("Still Water");
		TreeNode<String> four_dot_one_dot_two = new TreeNode<String>("Sparkling Water");
		TreeNode<String> four_dot_one_dot_three = new TreeNode<String>("Flavored water");
		four_dot_one.addChild(four_dot_one_dot_one);
		four_dot_one.addChild(four_dot_one_dot_two);
		four_dot_one.addChild(four_dot_one_dot_three);
		
		TreeNode<String> four_dot_two = new TreeNode<String>("Hot Drinks");
		TreeNode<String> four_dot_two_dot_one = new TreeNode<String>("Coffe");
		TreeNode<String> four_dot_two_dot_two = new TreeNode<String>("Tea");
		TreeNode<String> four_dot_two_dot_three = new TreeNode<String>("Chocolate");
		four_dot_two.addChild(four_dot_two_dot_one);
		four_dot_two.addChild(four_dot_two_dot_two);
		four_dot_two.addChild(four_dot_two_dot_three);
		
		TreeNode<String> four_dot_three = new TreeNode<String>("Alcohol");
		TreeNode<String> four_dot_three_dot_one = new TreeNode<String>("Aperitifs");
		TreeNode<String> four_dot_three_dot_two = new TreeNode<String>("Cocktails");
		TreeNode<String> four_dot_three_dot_three = new TreeNode<String>("Liquors");
		four_dot_three.addChild(four_dot_three_dot_one);
		four_dot_three.addChild(four_dot_three_dot_two);
		four_dot_three.addChild(four_dot_three_dot_three);
		
		four.addChild(four_dot_one);
		four.addChild(four_dot_two);
		four.addChild(four_dot_three);
		
		TreeNode<String> rootNode = new TreeNode<String>("Shopping");
		rootNode.addChild(one);
		rootNode.addChild(two);
		rootNode.addChild(three);
		rootNode.addChild(four);
		
		NAryTree<String> tree = new NAryTree<String>(rootNode);
		
		return tree;
	}
	
	/**
	 * @return True if the event has been manage by the fragment, false otherwise.
	 */
	public boolean onBackPressed()
	{
		return mListAdapter.goBackOnNavigationHistory();
	}
}
