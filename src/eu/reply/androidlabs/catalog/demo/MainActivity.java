package eu.reply.androidlabs.catalog.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity
{
	private MainFragment mMainFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) 
		{
			mMainFragment = new MainFragment();
			
			getSupportFragmentManager().beginTransaction().add(R.id.container, mMainFragment).commit();
		}
	}
	
	
	@Override
	public void onBackPressed() 
	{
		if (mMainFragment != null)
		{
			boolean eventManagmentByFragment = mMainFragment.onBackPressed();
			
			if (eventManagmentByFragment == false) super.onBackPressed();
		}
		else
		{
			super.onBackPressed();
		}
	}
}
