package nemesis.diiscover;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import nemesis.AsyncResponse;
import nemesis.BD.Consulta;
import nemesis.BD.Cursor;


public class MainActivity extends ActionBarActivity  implements AsyncResponse {
    TextView text=null;Consulta consultaUsarios=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //¿COMO CARGAR UNA LISATA DE USUARIOS?
         consultaUsarios = new Consulta("Rellenar lista usuarios","Select * from Usuario");
        consultaUsarios.delegate = this;
        consultaUsarios.execute();//mirar el metodo processFinish
    }
    public void rellenarListaUsuarios(Cursor cursor) {
        // Aquí rellenar codigo de rellenar cosas
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void processFinish(Cursor cursor,String output){
        switch (output) {
            case "Rellenar lista usuarios":

            //AQUI UTILIZAR consultaUusarios.cursor que ya estará inicializado
                try{

                    ResultSet result= cursor.getResultSet ();
                    while(result.next()){
                        String usuario=result.getString("nip");
                        text=(TextView) findViewById(R.id.textView);
                        text.setText(usuario);
                    }


            }  catch(Exception a){}

                break;
            case "Rellenar lista otros":


                break;

            default:


                break;
        }
    }
}
