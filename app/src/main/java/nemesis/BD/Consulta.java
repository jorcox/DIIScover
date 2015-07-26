package nemesis.BD;

import android.os.AsyncTask;

import nemesis.AsyncResponse;
import nemesis.diiscover.MainActivity;


/**
 * Created by inigo on 19/07/2015.
 */
public class Consulta extends AsyncTask<Void, Void, Void> {

    public AsyncResponse delegate=null;
    public String consulta="";
    public String opcion="";
    public Cursor cursor=null;
    public Consulta(String opcion,String consulta){
        this.consulta=consulta;
        this.opcion=opcion;
    }


    @Override
    protected Void doInBackground(Void... params) {
        try {
            JDBCTemplate con = JDBCTemplate.getJDBCTemplate();
            this. cursor=con.executeQuery(consulta);
            delegate.processFinish(this.cursor,opcion);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    protected void onPostExecute() {
    //    delegate.processFinish(opcion);
    }

}