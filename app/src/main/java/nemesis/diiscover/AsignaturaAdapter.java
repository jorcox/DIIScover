package nemesis.diiscover;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by inigo on 28/07/2015.
 */
public class AsignaturaAdapter extends RecyclerView.Adapter<AsignaturaViewHolder>{
    private List<Asignatura> asignaturas;

    public AsignaturaAdapter(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }
    @Override
    public int getItemCount() {
        return asignaturas.size();
    }
    /*
           Primero se obtiene el elemento que se está enlazando y acontinuacion se da valor a las vistas  del Cardview en este caso
            */
    @Override
    public void onBindViewHolder(AsignaturaViewHolder contactViewHolder, int i) {
        Asignatura asignatura = asignaturas.get(i);
        contactViewHolder.nombre.setText(asignatura.nombre);

      contactViewHolder.id.setText(String.valueOf(asignatura.id));



    }

    @Override
    public AsignaturaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //obtienes el itemview que es el cardview

        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.asignaturas, viewGroup, false);
        //a dicha cardview le ligas el listener onclick que mas tarde se creara en AsignaturaListadoActivity
        itemView.setOnClickListener(AsignaturaListadoActivity.myOnClickListener);
        return new AsignaturaViewHolder(itemView);
    }


    /**
     * A continuacion  se hallan metodos de eficiencia
     */













    /**
     * Devuelve el numero de veces que hay que reducir la imagen para evitar desbordamientos de memoria de android
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Tamaño original de la imagen
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * Transforma una imagen original en una version reducida en bitmap pequeño para evitar desbordamiento de memoria y la devuelve
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // Preparar para testear tamaño de la imagen
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate reduccion de la imagen
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decodificar la imagen
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFile(String image, int reqWidth, int reqHeight){
        // Preparar para testear tamaño de la imagen
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image, options);

        // Calculate reduccion de la imagen
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decodificar la imagen
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(image, options);
    }
}
