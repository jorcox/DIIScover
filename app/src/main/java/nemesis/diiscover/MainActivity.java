package nemesis.diiscover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import nemesis.BD.Consulta;


public class MainActivity extends AppCompatActivity {
    TextView text=null;Consulta consultaUsarios=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void irAAsignaturas(View vista) {
        Intent i = new Intent(this, CarreraListadoActivity.class);
        startActivity(i);
    }
    public void irAAyudar(View vista) {
       Intent i = new Intent(this, AyudanosActivity.class);
        startActivity(i);
    }
      public void irAProfesores(View vista) {
        Intent i = new Intent(this, ProfesorListadoActivity.class);
        startActivity(i);
    }

    public void irALaboratorios(View vista) {
        Intent i = new Intent(this, LaboratorioListadoActivity.class);
        startActivity(i);
    }
    public void irARegistrarse(View vista) {
        Intent i = new Intent(this, RegistroActivity.class);
        startActivity(i);
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
        if (id == R.id.action_incidencia) {
            Intent i = new Intent(this, IncidenciaActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
