package nemesis.diiscover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.HttpURLConnection;
import java.net.URL;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;
import nemesis.BD.ObtenerFotoInternet;

/**
 * Created by inigo on 29/07/2015.
 */
public class MetodosAuxiliares {
    public Cursor Consulta(String consulta){

        Consulta consultaUsarios = new Consulta(consulta);
        consultaUsarios.execute();//mirar el metodo processFinish
        try{
            double tiempo=System.currentTimeMillis();
            while (consultaUsarios.cursor== null && Math.abs(tiempo-System.currentTimeMillis())<3000 ) {
                Thread.sleep(80);
            }

        }catch(Exception a){    }
        return consultaUsarios.cursor;
    }
    public Bitmap cargarFoto(String url){
        Bitmap loadedImage =null;
        ObtenerFotoInternet obtenerfoto= new ObtenerFotoInternet(url);
        obtenerfoto.execute();
        try{
            double tiempo=System.currentTimeMillis();
            while (obtenerfoto.imagen== null && Math.abs(tiempo-System.currentTimeMillis())<3000 ) {
                Thread.sleep(80);
            }

        }catch(Exception a){    }
        return loadedImage;
    }
}
