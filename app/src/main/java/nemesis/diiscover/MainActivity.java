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
        setContentView(R.layout.activity_main);/*
MetodosAuxiliares Maux= new MetodosAuxiliares();
        Maux.pasarURLaBlob("http://html.rincondelvago.com/000761121.png",1,"asignatura");
        Maux.pasarURLaBlob("http://arantxa.ii.uam.es/~aa/practicas/recursos/ordenes.png",2,"asignatura");
        Maux.pasarURLaBlob("http://imagens.canaltech.com.br/16675.29991-Rede-de-Computadores.jpg",3,"asignatura");
        Maux.pasarURLaBlob("http://www.olarweb.com/wp-content/uploads/2014/03/bases-de-datos.jpg", 4, "asignatura");
        Maux.pasarURLaBlob("http://cd1.eju.tv/wp-content/uploads/2014/03/650_1000_inteligencia-artificial.jpg", 5, "asignatura");
        Maux.pasarURLaBlob("http://webdiis.unizar.es/asignaturas/AB/wp/wp-content/uploads/travelling_salesman_problem.png",6,"asignatura");
        Maux.pasarURLaBlob("http://www.arumani.es/wp-content/uploads/2015/07/PlanProyecto.png",7,"asignatura");
        Maux.pasarURLaBlob("http://imgs.xkcd.com/comics/exploits_of_a_mom.png",8,"asignatura");
        Maux.pasarURLaBlob("http://camptecnologico.com/wp-content/uploads/2014/02/ev3.jpg",9,"asignatura");
        Maux.pasarURLaBlob("http://www.dayherald.com/wp-content/uploads/2015/05/Super-Smash-Bros.jpg",10,"asignatura");
*/


/*
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
        //    Intent i = new Intent(this, CarreraListadoActivity.class);
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


}
