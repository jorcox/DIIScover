package nemesis.diiscover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Blob;
import java.sql.PreparedStatement;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;
import nemesis.BD.ObtenerFotoInternet;
import nemesis.BD.Sentencia;
import nemesis.BD.SentenciaPrepared;

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

    public void pasarURLaBlob(String url, int idCarrera){
        ObtenerFotoInternet obtenerfoto= new ObtenerFotoInternet(url);
        obtenerfoto.execute();
        try{
            double tiempo=System.currentTimeMillis();
            while (obtenerfoto.imagen== null && Math.abs(tiempo-System.currentTimeMillis())<3000 ) {
                Thread.sleep(80);
            }

        }catch(Exception a){    }
        try{


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        obtenerfoto.imagen.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] bArray = bos.toByteArray();
        String update = "update carrera set imagen = ? where id = '"+idCarrera+"' ";
        SentenciaPrepared sentencia= new SentenciaPrepared(update, bArray);
        sentencia.execute();
        }catch(Exception a){
            String e=a.toString();}
    }
    public void cargarImagen( Blob imagen){

    }
}
