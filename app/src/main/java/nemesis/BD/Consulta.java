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
    protected Void doInBackground(Void... params) {
        try {
            JDBCTemplate con = JDBCTemplate.getJDBCTemplate();
            this. cursor = con.executeQuery(consulta);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
            cancel(true);
        return null;
    }
    @Override

    protected void onPreExecute() {
    }






}