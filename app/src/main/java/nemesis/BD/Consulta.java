package nemesis.BD;

import android.os.AsyncTask;

import java.util.concurrent.Semaphore;

/**
 * Created by inigo on 19/07/2015.
 */
public class Consulta extends AsyncTask<Void, Void, Void> {

    private String consulta="";
    public Cursor cursor=null;
    public Consulta(String consulta ){
        this.consulta=consulta;
    }

    @Override
    /**
     * Un proceso que realiza una consulta y se obtiene un cursor de ella
     */
    protected Void doInBackground(Void... params) {
        JDBCTemplate con =null;
        try {
              con = JDBCTemplate.getJDBCTemplate();
            this. cursor = con.executeQuery(consulta);

        } catch (Exception e) {
            e.printStackTrace();
        }
            cancel(true);
        return null;
    }





}