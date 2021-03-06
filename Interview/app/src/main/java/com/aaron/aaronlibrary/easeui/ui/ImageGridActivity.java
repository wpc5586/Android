package com.aaron.aaronlibrary.easeui.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.aaron.aaronlibrary.easeui.base.EaseBaseActivity;
import com.aaron.interview.R;

public class ImageGridActivity extends EaseBaseActivity {

	private static final String TAG = "ImageGridActivity";

    @Override
    protected int getContentLayoutId() {
        return R.layout.em_activity_chat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new ImageGridFragment()).commit();
        }
    }
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);	
	}	
	
}
