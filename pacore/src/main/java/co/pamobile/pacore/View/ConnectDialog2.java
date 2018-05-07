package co.pamobile.pacore.View;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


/**
 * Created by KhoaVin on 24/06/2017.
 */

public class ConnectDialog2 extends DialogFragment {
    Listener onClickReconnect;
    Listener onClickCancel;

    public void setOnClickListenerReconnect(Listener onClickListener) {
        this.onClickReconnect = onClickListener;
    }

    public void setOnClickListenerCancel(Listener onClickListener) {
        this.onClickCancel = onClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Failed to connect")
                .setPositiveButton("Reconnect", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onClickReconnect.onClick();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onClickCancel.onClick();
            }
        });
        return builder.create();
    }



    public interface Listener{
        void onClick();
    }

}
