package nemesis.BD;

import android.os.AsyncTask;

/**
 * Created by javier on 29/07/15.
 */
public class Sentencia extends AsyncTask<Void, Void, Void> {
    public String sentencia="";
    public String opcion="";

    public Sentencia(String sentencia){
        this.sentencia=sentencia;

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            JDBCTemplate con = JDBCTemplate.getJDBCTemplate();
            con.executeSentence(sentencia);
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
