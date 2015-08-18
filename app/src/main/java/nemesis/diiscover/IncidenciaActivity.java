package nemesis.diiscover;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nemesis.BD.*;

/**
 * La clase representa la pantalla que contiene el formulario para registrar una
 * incidencia en la aplicación.
 */
public class IncidenciaActivity extends AppCompatActivity {

    private static final String INFO_USUARIO = "INFO_USUARIO";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidencia);

        Button enviar = (Button) findViewById(R.id.boton_enviar_incidencia);
        enviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText editDescripcion;
                String sentencia, fecha, descripcion;
                int usuario;

                /* Insertar en BD */
                editDescripcion = (EditText) findViewById(R.id.texto_incidencia);
                descripcion = editDescripcion.getText().toString();
                usuario = getUsuario();
                fecha = getFecha();
                sentencia = generarInsert(usuario, fecha, descripcion);
                Sentencia insercion = new Sentencia(sentencia);
                insercion.execute();

                /* Enviar correo */
                MailProfesores mail = new MailProfesores(IncidenciaActivity.this, "Incidencia", descripcion,"diiscover.soporte@gmail.com");
                mail.enviar(IncidenciaActivity.this);
                Toast toast1 =
                        Toast.makeText(IncidenciaActivity.this,
                                "¡Gracias por ayudarnos a mejorar!", Toast.LENGTH_SHORT);

                toast1.show();


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_incidencia, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
    /**
     * Dados el usuario que genera la incidencia, la fecha actual y la descripcion de la
     * incidencia, devuelve una sentencia SQL para insertar la incidencia en la base de datos.
     */
    private String generarInsert(int usuario, String fecha, String descripcion) {
        String sentencia = "insert into incidencia (id_usuario, fecha, descripcion) values (";
        sentencia += usuario + ", ";
        sentencia += "'" + fecha + "', ";
        sentencia += "'" + descripcion + "')";
        return sentencia;
    }

    /**
     * Devuelve la id del usuario que esta utilizando la aplicacion, que se ha
     * almacenado en SharedPreferences en el momento de registrarse o hacer login.
     *
     * @return la id del usuario que esta usando la aplicacion, o -1 en caso
     * de error.
     */
    private int getUsuario() {
        SharedPreferences settings = getSharedPreferences(INFO_USUARIO, 0);
        int id = settings.getInt("id_usuario", -1);
        return id;
    }

    /**
     * Devuelve la fecha actual con el formato yyyy-MM-dd, para poder ser insertada
     * correctamente en la base de datos con el formato Date de MySQL.
     */
    private String getFecha() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

}
