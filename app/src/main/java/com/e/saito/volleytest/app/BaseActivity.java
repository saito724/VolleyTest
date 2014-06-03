package com.e.saito.volleytest.app;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;


/**
 * Created by e.saito on 2014/06/03.
 */
public class BaseActivity extends ActionBarActivity {
    private final String TAG_LOADING = "loadingDialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissLoadingDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected  void showLoadingDialog(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment dialog = fm.findFragmentByTag(TAG_LOADING);
        if(dialog != null){
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(dialog,TAG_LOADING);
            ft.commitAllowingStateLoss();
        }


    }

    protected  void dismissLoadingDialog(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment dialog = fm.findFragmentByTag(TAG_LOADING);
        if(dialog != null){
            ((DialogFragment)dialog).dismiss();
        }

    }
}
