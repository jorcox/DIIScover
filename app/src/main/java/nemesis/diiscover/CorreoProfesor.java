package nemesis.diiscover;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CorreoProfesor extends AppCompatActivity {
        String to="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correo_profesor);
        Bundle extras = getIntent().getExtras();
        String nombre=extras.getString("profesor");
        setTitle("Enviar correo a "+nombre);
        to=extras.getString("correo");

    }

    public void enviarCorreo(View vista) {
        TextView asunto= (TextView)findViewById(R.id.asuntoText);
        TextView descripcion= (TextView)findViewById(R.id.texto_mensaje);
        MailProfesores mail = new MailProfesores(CorreoProfesor.this, asunto.getText().toString(), descripcion.getText().toString(),to);
        FragmentManager fragmentManager = getSupportFragmentManager();
        DialogoAlerta dialogo = new DialogoAlerta();
        dialogo.mail=mail;
        dialogo.actividad=this;
        dialogo.show(fragmentManager, "Enviar");

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_correo_profesor, menu);
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
