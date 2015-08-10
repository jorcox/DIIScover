package nemesis.diiscover;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by inigo on 28/07/2015.
 */
public class CarreraViewHolder extends RecyclerView.ViewHolder {
    protected TextView nombre;
    protected TextView id;
    protected ImageView imagen;
    protected TextView cuatris;

    public CarreraViewHolder(View v) {
        super(v);

        id = (TextView) v.findViewById(R.id.textViewCarreraID);
        cuatris = (TextView) v.findViewById(R.id.textViewCarreraCuatrimestres);

        nombre = (TextView) v.findViewById(R.id.textViewCarreraNombre);
        imagen = (ImageView) v.findViewById(R.id.ImageViewCarreraImagen);


    }
}
