package nemesis.diiscover;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by inigo on 28/07/2015.
 */
public class CarreraAdapter extends RecyclerView.Adapter<CarreraViewHolder>{
    private List<Carrera> carreras;
    public CarreraAdapter(List<Carrera> carreras) {
        this.carreras = carreras;
    }
    @Override
    public int getItemCount() {
        return carreras.size();
    }

    @Override
    public void onBindViewHolder(CarreraViewHolder contactViewHolder, int i) {
        Carrera carrera = carreras.get(i);
        contactViewHolder.nombre.setText(carrera.tipoCarrera+ " en "+carrera.nombre);
        contactViewHolder.cuatris.setText(String.valueOf(carrera.cuatrimestres));
      contactViewHolder.id.setText(String.valueOf(carrera.id));


        //cargo imagen
        try {
            byte[] data = carrera.imagen;
            Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);
            contactViewHolder.imagen.setImageBitmap(bitmap);
        } catch (Exception ex) {
        }

        /*
        URL imageUrl = null;
        try {

            MetodosAuxiliares Maux= new MetodosAuxiliares();
            Bitmap loadedImage = Maux.cargarFoto(carrera.fotoURL);
            contactViewHolder.imagen.setImageBitmap(loadedImage);

        } catch (Exception e) {
        System.out.println(e.toString());
        }
        */
    }
    //irAsignatuars

    @Override
    public CarreraViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.carrera, viewGroup, false);
        itemView.setOnClickListener(CarreraListadoActivity.myOnClickListener);
        return new CarreraViewHolder(itemView);
    }


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
