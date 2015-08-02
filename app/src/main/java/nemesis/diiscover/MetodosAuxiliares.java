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

    public void pasarURLaBlob(String url, int id, String tabla){
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
            String update = "update " + tabla + " set imagen = ? where id = '" + id + "' ";
            SentenciaImagenPrepared sentencia= new SentenciaImagenPrepared(update, bArray);
            sentencia.execute();
            }catch(Exception a){
                String e=a.toString();
            }
        }
    public void cargarImagen( Blob imagen){

    }
}
