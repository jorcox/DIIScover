package nemesis.diiscover;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;


public class MainActivity extends ActionBarActivity   {
    TextView text=null;Consulta consultaUsarios=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        MetodosAuxiliares Maux= new MetodosAuxiliares();
      Maux.pasarURLaBlob("http://www.lineainformatica.org/wp-content/uploads/2013/04/fondo1.jpg",2);
        Maux.pasarURLaBlob("http://fress.co/wp-content/uploads/2014/09/39-cursos-gratis-informatica1.jpg",1);

        //¿COMO CARGAR UNA LISATA DE USUARIOS?
        Cursor cursor=Maux.Consulta("Select * from Usuario");
        try{

            ResultSet result= cursor.getResultSet();
            while(result.next()){
                String usuario=result.getString("nip");
                text=(TextView) findViewById(R.id.textView);
                text.setText(usuario);
                 }  catch(Exception a){
            String e=a.getMessage();
        }
            }
*/
            Intent i = new Intent(this, CarreraListadoActivity.class);

            startActivity(i);


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

    public void irAcarreras(View view){

    }
}
