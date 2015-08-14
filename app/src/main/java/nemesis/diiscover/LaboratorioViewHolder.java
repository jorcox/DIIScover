package nemesis.diiscover;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jorge on 02/08/2015.
 */
public class LaboratorioViewHolder extends RecyclerView.ViewHolder{
    protected Button nombre;

    protected TextView id;

    public LaboratorioViewHolder(View v) {
        super(v);

        id = (TextView) v.findViewById(R.id.textViewLaboratorioID);
        nombre = (Button) v.findViewById(R.id.ButtonLaboratorioNombre);

    }
}
