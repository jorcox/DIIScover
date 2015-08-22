package nemesis.diiscover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import nemesis.BD.Sentencia;

public class AyudanosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ayuda, menu);
        return true;
    }
    public void EnviarSugerencia(View vista) {
        String fecha="";
        String texto="";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        fecha= DateFormat.getDateTimeInstance(
                DateFormat.SHORT, DateFormat.SHORT).format(date);
        EditText textoEditable=(EditText) findViewById(R.id.editText);
        texto+= textoEditable.getText();
        CheckBox ch1=(CheckBox) findViewById(R.id.checkBox);
        if (ch1.isChecked())texto+=", " + ch1.getText();
        CheckBox ch2=(CheckBox) findViewById(R.id.checkBox2);
        if (ch2.isChecked())texto+=", " + ch2.getText();
        CheckBox ch3=(CheckBox) findViewById(R.id.checkBox3);
        if (ch3.isChecked())texto+=", " + ch3.getText();
        CheckBox ch4=(CheckBox) findViewById(R.id.checkBox4);
        if (ch4.isChecked())texto+=", " + ch4.getText();
        CheckBox ch5=(CheckBox) findViewById(R.id.checkBox5);
        if (ch5.isChecked())texto+=", " + ch5.getText();
        CheckBox ch6=(CheckBox) findViewById(R.id.checkBox6);
        if (ch6.isChecked())texto+=", " + ch6.getText();
        CheckBox ch7=(CheckBox) findViewById(R.id.checkBox7);
        if (ch7.isChecked())texto+=", " + ch7.getText();
        CheckBox ch8=(CheckBox) findViewById(R.id.checkBox8);
        if (ch8.isChecked())texto+=", " + ch8.getText();
        Sentencia insertar= new Sentencia("Insert into ayudar values(null,'"+ fecha + "','"+ texto +"')");
        insertar.execute();
          /* Enviar correo */
        MailProfesores mail = new MailProfesores(AyudanosActivity.this, "Ayudános", texto,"diiscover.soporte@gmail.com");
        mail.enviar(AyudanosActivity.this);
        Toast toast1 =
                Toast.makeText(this,
                        "¡Gracias por ayudarnos a mejorar!", Toast.LENGTH_SHORT);

        toast1.show();
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
