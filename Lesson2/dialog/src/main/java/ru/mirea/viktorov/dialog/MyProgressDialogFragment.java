package ru.mirea.viktorov.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends ProgressDialog {

    Context context;

    public MyProgressDialogFragment(Context context) {
        super(context);
        this.context = context;
    }

    public void show(){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Идёт загрузка...");
        progressDialog.show();
    }
}
