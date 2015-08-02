package nemesis.diiscover;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nemesis.BD.*;

/**
 * La clase representa la pantalla que contiene el formulario para registrar una
 * incidencia en la aplicación.
 */
public class IncidenciaActivity extends Activity {

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
                Sentencia insercion = new Sentencia("",sentencia);
                insercion.execute();

                /* Enviar correo */
                Mail mail = new Mail(IncidenciaActivity.this, usuario, fecha, descripcion);
                mail.enviar();

                finish();
            }
        });
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
