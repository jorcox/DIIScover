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

import java.sql.ResultSet;

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
        mPassText.setText(getPass());
        mCorreoText.setText(getcorreo());

        Button botonIdentificar = (Button) findViewById(R.id.identificar);

        botonIdentificar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InicioActivity.this.comprobarUsuario();
            }

        });


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
        try{
            ResultSet resul= usuario.getResultSet();
            if(resul.next()){


                id_usuario=Integer.parseInt(resul.getString("id"));
                String nick = resul.getString("nombre"); //no hay nombre
                String pass = resul.getString("contrasena");
                String correo = resul.getString("correo");
//hash
                if ( String.valueOf(passIntroducido.hashCode()).equals(pass)) {
                    mRowNick = nick;
                    actualizarPrefsUsuario(correo);
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);

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
        }catch (Exception a){}


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

    public String getcorreo(){
        SharedPreferences settings = getSharedPreferences(INFO_USUARIO, 0);
        String pass = settings.getString("pass", "");
        return pass;
    }
    public String getPass(){
        SharedPreferences settings = getSharedPreferences(INFO_USUARIO, 0);
        String correo = settings.getString("correo", "");
        return correo;
    }
}