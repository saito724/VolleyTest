package com.e.saito.volleytest.wiget;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by e.saito on 2014/06/03.
 */
public class LoadingDialog extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Now Loading ...");

    }
}
