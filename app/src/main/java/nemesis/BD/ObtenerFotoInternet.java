package nemesis.BD;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by inigo on 30/07/2015.
 */

public class ObtenerFotoInternet extends AsyncTask<Void, Void, Void> {
     public String url ="";
    public  Bitmap imagen=null;
    private boolean directorio=false;
    public ObtenerFotoInternet(String url,boolean directorio ){
        this.url=url;this.directorio=directorio;
    }


    @Override
    protected Void doInBackground(Void... params) {
        try{

            if (directorio){
              File foto= new File(url);
                int size = (int) foto.length();
                byte[] bytes = new byte[size];
                try {
                    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(foto));
                    buf.read(bytes, 0, bytes.length);
                    buf.close();
                    imagen = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }


            }
                else  {
                URL imageUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
                conn.connect();
                imagen = BitmapFactory.decodeStream(conn.getInputStream());
            }

        }catch (Exception a){
            String e=a.toString();

        }
        cancel(true);
        return null;
    }
    @Override

    protected void onPreExecute() {
    }






}