package nemesis.diiscover;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by inigo on 28/07/2015.
 */
public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorViewHolder>{
    private List<Profesor> profesores;
    //constructor
    Context con=null;
    public ProfesorAdapter(List<Profesor> profesores,Context cont) {
        this.profesores = profesores;con=cont;
    }
    @Override
    //devuelve el numero de items en el adapter
    public int getItemCount() {
        return profesores.size();
    }

    @Override
    public void onBindViewHolder(ProfesorViewHolder contactViewHolder, int i) {
        /*
        Primero se obtiene el elemento que se está enlazando y acontinuacion se da valor a las vistas  del Cardview en este caso
         */
        Profesor profesor = profesores.get(i);
        contactViewHolder.nombre.setText(profesor.nombre);
        contactViewHolder.correo.setText(profesor.correo);
      contactViewHolder.id.setText(String.valueOf(profesor.id));


        //cargo imagen
        try {
            byte[] data = profesor.imagen;
            if(profesor.imagen==null){
                contactViewHolder.imagen.setImageResource(con.getResources().getIdentifier("profesor.png", "drawable", con.getPackageName()));
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(data , 0, data .length);
            contactViewHolder.imagen.setImageBitmap(getCircularBitmapFrom(bitmap));
        } catch (Exception ex) {
        }


    }

    @Override
    public ProfesorViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //obtienes el itemview que es el cardview
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.profesor, viewGroup, false);
        //a dicha cardview le ligas el listener onclick que mas tarde se creara en CarreraListadoActivity
        itemView.setOnClickListener(ProfesorListadoActivity.myOnClickListener);
        //se devuelve
        return new ProfesorViewHolder(itemView);
    }


    public  static Bitmap getCircularBitmapFrom(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        float radius = bitmap.getWidth() > bitmap.getHeight() ? ((float) bitmap
                .getHeight()) / 2f : ((float) bitmap.getWidth()) / 2f;
        Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                radius, paint);

        return canvasBitmap;
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
