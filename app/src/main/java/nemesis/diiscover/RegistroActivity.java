package nemesis.diiscover;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import nemesis.BD.*;
/**
 * Clase que se encarga de administrar los datos indroducidos por el usuario,
 * almacen�ndolos en la BD. En caso de que el registro del usuario se halla
 * realizado correctamente, se redirecciona a la clase MainActivity
 */
public class RegistroActivity extends AppCompatActivity {

    private static final int ACTIVITY_CLIENTE = 1;
    private static final String INFO_USUARIO = "INFO_USUARIO";

    private EditText mPassText;             // Contrasena del usuario
    private EditText mCorreoText;           // Correo del usuario
    private EditText mNipText;              // Nip del usuario
    private EditText mNombreText;           // Nombre del usuario

    private String correo="";
    private String pass="";
    private int id_usuario=-1;
    private String mRowCorreo;
    private String mRowNombre;
    private MetodosAuxiliares maux=new MetodosAuxiliares();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registro);

        mPassText = (EditText) findViewById(R.id.pass);
        mCorreoText = (EditText) findViewById(R.id.correo);
        mNombreText = (EditText) findViewById(R.id.nombre);
        mNipText = (EditText) findViewById(R.id.nip);

        //mTelefonoText.setRawInputType(Configuration.KEYBOARD_QWERTY);

        Button botonConfirmar = (Button) findViewById(R.id.confirmar);

        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                boolean registrado = RegistroActivity.this.registrarUsuario();
                // Si el usuario se ha registrado correctamente, se redirige a MainActivity
                if (registrado) {
                    actualizarPrefsUsuario();

                    Intent i = new Intent(RegistroActivity.this, MainActivity.class);
                    RegistroActivity.this.startActivity(i);
                }
            }

        });
    }

    private void actualizarPrefsUsuario() {
        SharedPreferences prefsCorreo = getSharedPreferences(INFO_USUARIO, 0);
        SharedPreferences.Editor editor = prefsCorreo.edit();
        editor.putInt("id_usuario", id_usuario);
        editor.putString("pass", pass);
        editor.putString("correo", correo);
        editor.commit();
    }

    /*
     * En caso de que mRowNick tenga un valor, rellena los campos del usuario correspondiente
     * con los datos almacenados en la BD
     */
    private void poblarCampos() {
        if (!mRowNombre.equals("")) {
            Cursor usuario=maux.Consulta("SELECT * FROM Usuario WHERE correo = '" + mCorreoText.getText().toString() + "'");
            mNombreText.setText(usuario.getString("nombre")); //no hay nombre
            mPassText.setText(usuario.getString("contrasena"));
            mCorreoText.setText(usuario.getString("correo"));
            mNipText.setText(usuario.getString("nip"));
        }

    }

    /*
     * Comprueba que los datos introducidos no son nulos o tienen un valor v�lido.
     * Posteriormente verifica si el correo y el nick introducidos no han sido utilizados
     * anteriormente en otra cuenta. En caso afirmativo, inserta los datos del nuevo usuario
     * en la base de datos
     */
    public boolean registrarUsuario() {


         pass = mPassText.getText().toString();
        correo = mCorreoText.getText().toString();
        String nombre = mNombreText.getText().toString();
        int nip;
        try {
            nip = Integer.parseInt(mNipText.getText().toString());
        } catch (NumberFormatException e) {
            nip = -1;
        }

        boolean registrado = false;

        //Cursor usuarioCorreo = dbHelper.listarUsuario(correo);
        //Cursor usuarioNick = dbHelper.listarUsuarioNick(nick);

        Cursor usuarioCorreo=maux.Consulta("SELECT * FROM Usuario WHERE correo = '" + correo + "'");


        if (usuarioCorreo == null && !correo.equals("") && correo.contains("@") && correo.contains(".")  &&
                 !nombre.equals("") && !pass.equals("") && nip != -1) {

            //insertar usuario
            //long resultado = dbHelper.crearUsuario(correo, nick, nombre, direccion, pass, telefono);
            Sentencia sentenciaRegistro= new Sentencia("Insert into usuario values(null,'"+ pass.hashCode() +"','"+ nip +"','"+ correo +"',null,'"+ nombre +"')");
            sentenciaRegistro.execute();
            registrado = true;
        }
        else {
            TextView userCorreo = (TextView) RegistroActivity.this.findViewById(R.id.usuarioError);
            TextView mail = (TextView) RegistroActivity.this.findViewById(R.id.correoError);
            TextView name = (TextView) RegistroActivity.this.findViewById(R.id.nombreError);
            TextView pwd = (TextView) RegistroActivity.this.findViewById(R.id.passError);
            TextView nipp = (TextView) RegistroActivity.this.findViewById(R.id.nipError);

           try {
               ResultSet res=usuarioCorreo.getResultSet();

               if (!res.next()) {
                   userCorreo.setVisibility(View.VISIBLE);
                   mail.setVisibility(View.INVISIBLE);
                   name.setVisibility(View.INVISIBLE);
                   pwd.setVisibility(View.INVISIBLE);
                   nipp.setVisibility(View.INVISIBLE);

               }
               else if(correo.equals("")) {
                   userCorreo.setVisibility(View.INVISIBLE);
                   mail.setVisibility(View.VISIBLE);
                   name.setVisibility(View.INVISIBLE);
                   pwd.setVisibility(View.INVISIBLE);
                   nipp.setVisibility(View.INVISIBLE);
               }
               else if(!correo.contains("@")) {
                   userCorreo.setVisibility(View.INVISIBLE);
                   mail.setVisibility(View.VISIBLE);
                   name.setVisibility(View.INVISIBLE);
                   pwd.setVisibility(View.INVISIBLE);
                   nipp.setVisibility(View.INVISIBLE);
               }
               else if(pass.equals("")) {
                   userCorreo.setVisibility(View.INVISIBLE);
                   mail.setVisibility(View.INVISIBLE);
                   name.setVisibility(View.INVISIBLE);
                   pwd.setVisibility(View.VISIBLE);
                   nipp.setVisibility(View.INVISIBLE);
               }
               else if(nombre.equals("")) {
                   userCorreo.setVisibility(View.INVISIBLE);
                   mail.setVisibility(View.INVISIBLE);
                   name.setVisibility(View.VISIBLE);
                   pwd.setVisibility(View.INVISIBLE);
                   nipp.setVisibility(View.INVISIBLE);
               }

               else if(nip == -1) {
                   userCorreo.setVisibility(View.INVISIBLE);
                   mail.setVisibility(View.INVISIBLE);
                   name.setVisibility(View.INVISIBLE);
                   pwd.setVisibility(View.INVISIBLE);
                   nipp.setVisibility(View.VISIBLE);
               }
           }catch(Exception a){


               Toast toast1 =
                       Toast.makeText(getApplicationContext(),
                               "No se pudo conectar con el servidor", Toast.LENGTH_SHORT);

               toast1.show();
           }

           }

        return registrado;
    }

}