package nemesis.diiscover;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by inigo on 28/07/2015.
 */
public class ProfesorViewHolder extends RecyclerView.ViewHolder {
    protected TextView nombre;
    protected TextView id;
    protected ImageView imagen;
    protected TextView correo;

    public ProfesorViewHolder(View v) {
        super(v);

        id = (TextView) v.findViewById(R.id.textViewProfesorID);
        correo = (TextView) v.findViewById(R.id.textViewcorreoProfesor);

        nombre = (TextView) v.findViewById(R.id.textViewNombreProfesor);
        imagen = (ImageView) v.findViewById(R.id.imagenProfesor);


    }
}
