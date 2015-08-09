package nemesis.diiscover;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by inigo on 28/07/2015.
 */
public class AsignaturaViewHolder extends RecyclerView.ViewHolder {
    protected Button nombre;

    protected TextView id;

    public AsignaturaViewHolder(View v) {
        super(v);
        id = (TextView) v.findViewById(R.id.textViewAsignaturaID);
        nombre = (Button) v.findViewById(R.id.ButtonAsignaturaNombre);

    }
}
