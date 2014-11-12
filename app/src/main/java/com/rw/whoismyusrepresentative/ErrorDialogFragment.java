package com.rw.whoismyusrepresentative;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


/**
 * This Fragment will display a generic dialog to the user
 * saying there was an issue and no information could be retrieved.
 */
public class ErrorDialogFragment extends DialogFragment {

    public static ErrorDialogFragment newInstance(){
        ErrorDialogFragment frag = new ErrorDialogFragment();
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Dialog);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_message)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //do nothing
                            }
                        }
                )
                .create();
    }
}


