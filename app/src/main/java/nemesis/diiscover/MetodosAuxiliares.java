package nemesis.diiscover;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;
import nemesis.BD.ObtenerFotoInternet;
import nemesis.BD.SentenciaImagenPrepared;

/**
 * Created by inigo on 29/07/2015.
 */

public class MetodosAuxiliares {
    public Cursor Consulta(String consulta, int tiempoEspera){
        Consulta consultaUsarios = new Consulta(consulta);
        consultaUsarios.execute();//mirar el metodo processFinish
        try{
           double tiempo=System.currentTimeMillis();
            while (consultaUsarios.cursor== null && Math.abs(tiempo-System.currentTimeMillis())<tiempoEspera ) {
                Thread.sleep(120);
            }


        }catch(Exception a){
            String ee=a.toString();  }
        return consultaUsarios.cursor;
    }

    public void pasarURLaBlob(String url, int id, String tabla){
        ObtenerFotoInternet obtenerfoto= new ObtenerFotoInternet(url,false);
        obtenerfoto.execute();
        try{
            double tiempo=System.currentTimeMillis();
            while (obtenerfoto.imagen== null && Math.abs(tiempo-System.currentTimeMillis())<7000 ) {
                Thread.sleep(50);
            }

        }catch(Exception a){    }
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            obtenerfoto.imagen.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            String update = "update " + tabla + " set imagen = ? where id = '" + id + "' ";
            SentenciaImagenPrepared sentencia= new SentenciaImagenPrepared(update, bArray);
            sentencia.execute();
            }catch(Exception a){
                String e=a.toString();
            }
        }
    public void pasarURLDirectorioaBlob(String url, int id, String tabla){
        ObtenerFotoInternet obtenerfoto= new ObtenerFotoInternet(url,true);
        obtenerfoto.execute();
        try{
            double tiempo=System.currentTimeMillis();
            while (obtenerfoto.imagen== null && Math.abs(tiempo-System.currentTimeMillis())<7000 ) {
                Thread.sleep(50);
            }

        }catch(Exception a){    }
        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            obtenerfoto.imagen.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            String update = "update " + tabla + " set imagen = ? where id = '" + id + "' ";
            SentenciaImagenPrepared sentencia= new SentenciaImagenPrepared(update, bArray);
            sentencia.execute();
        }catch(Exception a){
            String e=a.toString();
        }
    }
}
