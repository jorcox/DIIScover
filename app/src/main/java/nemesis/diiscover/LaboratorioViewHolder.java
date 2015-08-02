package nemesis.diiscover;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jorge on 02/08/2015.
 */
public class LaboratorioViewHolder extends RecyclerView.ViewHolder{
    protected TextView nombre;

    protected TextView id;
    protected ImageView imagen;

    public LaboratorioViewHolder(View v) {
        super(v);

        id = (TextView) v.findViewById(R.id.textViewLaboratorioID);
        nombre = (TextView) v.findViewById(R.id.textViewLaboratorioNombre);
        imagen = (ImageView) v.findViewById(R.id.ImageViewLaboratorioImagen);

    }
}
