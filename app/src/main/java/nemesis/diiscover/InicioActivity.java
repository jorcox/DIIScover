package nemesis.diiscover;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nemesis.BD.*;
/**
 * Clase que se encarga de identificar si los datos indroducidos por el usuario se
 * corresponden con los almacenados en la BD. Si coinciden, te redirecciona a la clase
 * InicioPaquetes si el usuario es admin y a MainActivity en el resto de los casos.
 * Por otro lado, también te puede redireccionar a RegistroActivity si el usuario no
 * está registrado
 */
public class InicioActivity extends AppCompatActivity {

    private static final int ACTIVITY_ADMIN = 0;
    private static final int ACTIVITY_CLIENTE = 1;
    private static final String INFO_USUARIO = "INFO_USUARIO";

    private EditText mCorreoText;         // Correo del usuario
    private EditText mPassText;         // Contraseña del usuario

    private String mRowNick;
    private int id_usuario=-1;
    private MetodosAuxiliares maux= new MetodosAuxiliares();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);

        TextView txt = (TextView) findViewById(R.id.Nemesis);

        Button botonRegistrar = (Button) findViewById(R.id.registrar);

        botonRegistrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(InicioActivity.this, RegistroActivity.class);
                InicioActivity.this.startActivityForResult(i, ACTIVITY_CLIENTE);
            }

        });

        mCorreoText = (EditText) findViewById(R.id.correo);
        mPassText = (EditText) findViewById(R.id.pass);

        Button botonIdentificar = (Button) findViewById(R.id.identificar);

        botonIdentificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InicioActivity.this.comprobarUsuario();
            }

        });

        //CODIGO DE TEST
        //AdaptadorUsuarios adUsuarios = new AdaptadorUsuarios(this);
        //adUsuarios.open();
        //adUsuarios.crearUsuario("test", "test", "test", "test", "test", 1234);
    }

    /*
     * Comprueba que el nick del usuario introducido se encuentra en la base de datos y verifica que
     * el nick y la contraseña introducidos se corresponden con los que se están alojados en la BD
     */
    public void comprobarUsuario() {



        String correoIntroducido = mCorreoText.getText().toString();
        String passIntroducido = mPassText.getText().toString();

        //Cursor usuario = dbHelper.listarUsuarioNick(nickIntroducido);
        Cursor usuario=maux.Consulta("SELECT * FROM Usuario WHERE correo = '"+correoIntroducido+"'");

        //usuario o diiscover.usuario?

        if (usuario != null) {

            id_usuario=Integer.parseInt(usuario.getString("id_usuario"));
            String nick = usuario.getString("nombre"); //no hay nombre
            String pass = usuario.getString("contrasena");
            String correo = usuario.getString("correo");

            if (correoIntroducido.equals(nick) && passIntroducido.equals(pass)) {
                mRowNick = nick;
                actualizarPrefsUsuario(correo);
                //Intent i = new Intent(this, MainActivity.class);
                //startActivityForResult(i, ACTIVITY_CLIENTE);

            }
            else {
                TextView pwd = (TextView) InicioActivity.this.findViewById(R.id.errorPass);
                pwd.setVisibility(View.VISIBLE);
                TextView user = (TextView) InicioActivity.this.findViewById(R.id.errorNick);
                user.setVisibility(View.INVISIBLE);
            }
        }
        else {
            TextView user = (TextView) InicioActivity.this.findViewById(R.id.errorNick);
            user.setVisibility(View.VISIBLE);
            TextView pwd = (TextView) InicioActivity.this.findViewById(R.id.errorPass);
            pwd.setVisibility(View.INVISIBLE);
        }
        //if (usuario != null && !usuario.isClosed()){
            //usuario.close();
        //}
    }

    private void actualizarPrefsUsuario(String correo) {
        SharedPreferences prefsCorreo = getSharedPreferences(INFO_USUARIO, 0);
        SharedPreferences.Editor editor = prefsCorreo.edit();
        editor.putInt("id_usuario",id_usuario);
        editor.commit();
    }

}