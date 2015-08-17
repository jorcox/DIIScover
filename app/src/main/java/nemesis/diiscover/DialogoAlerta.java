package nemesis.diiscover;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by inigo on 17/08/2015.
 */
public class DialogoAlerta extends DialogFragment {

    protected MailProfesores mail=null;
    Activity actividad=null;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage("¿Desea enviar este mensaje? El mensaje se enviará desde un correo por defecto de la aplicación y quedará allí registrado.")
                .setTitle("Enviar correo")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        mail.enviar(actividad);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
}