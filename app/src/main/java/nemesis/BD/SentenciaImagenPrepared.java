package nemesis.BD;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * Created by javier on 29/07/15.
 */
public class SentenciaImagenPrepared extends AsyncTask<Void, Void, Void> {
    private String sentencia="";
    public PreparedStatement statement=null;
    private  JDBCTemplate con =null;
    private byte [] bArray=null;

    public SentenciaImagenPrepared(String sentencia, byte[] bArray){
        this.sentencia=sentencia;
      this.bArray=bArray;

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            JDBCTemplate con = JDBCTemplate.getJDBCTemplate();
            try{
                this.statement= con.getConnection().prepareStatement(sentencia);
                statement.setBytes(1, bArray);
            }
            catch(Exception a){
                String e=a.getMessage();
            }
            statement.executeUpdate();

           // con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}