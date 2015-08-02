package nemesis.diiscover;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by inigo on 28/07/2015.
 */
public class AsignaturaViewHolder extends RecyclerView.ViewHolder {
    protected TextView nombre;

    protected TextView id;
    protected ImageView imagen;

    public AsignaturaViewHolder(View v) {
        super(v);
        id = (TextView) v.findViewById(R.id.textViewAsignaturaID);
        nombre = (TextView) v.findViewById(R.id.textViewAsignaturaNombre);
        imagen = (ImageView) v.findViewById(R.id.ImageViewAsignaturaImagen);

    }
}
