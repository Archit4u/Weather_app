package com.mycompany.stormy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by vaio on 27-03-2015.
 */
public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context=getActivity();
        AlertDialog.Builder builder=new AlertDialog.Builder(context).setTitle(context.getString(R.string.DialogTitle)).setMessage(context.getString(R.string.DialogMessage)).setPositiveButton("ok", null);

    AlertDialog dialog=builder.create();
        return dialog;

    }
}
