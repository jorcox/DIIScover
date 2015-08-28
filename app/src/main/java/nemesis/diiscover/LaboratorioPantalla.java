package nemesis.diiscover;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Cursor;

public class LaboratorioPantalla extends AppCompatActivity {

    private String nombre = "";
    private int piso = 0;
    private int numero = 0;
    private long id = -1;
    private int libres;
    private ArrayList<String> asignaturas = new ArrayList<>();
    private ImageView imagen;
    private TextView numeroView;
    private TextView numPcView;
    private TextView pisoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Cursor cursorLab = null;
        Cursor cursorNum = null;
        MetodosAuxiliares aux = new MetodosAuxiliares();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_pantalla);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("idLab", -1);
        if (id != -1){
            cursorLab = aux.Consulta("SELECT * FROM diiscover.laboratorio where id =" + id,2500);
        } else {
            nombre = extras.getString("nombre");
            cursorLab = aux.Consulta("SELECT * FROM diiscover.laboratorio where nombre = '" + nombre + "'",2500);
        }

        try{

            ResultSet result = cursorLab.getResultSet ();
            while(result.next()){
                nombre = result.getString("nombre");
                piso = result.getInt("piso");
                numero = result.getInt("numero");
                id = result.getLong("id");
                /* Imagen por si se pone en el futuro */
               /* byte [] bytes = result.getBytes("imagen");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagen= (ImageView)findViewById(R.id.imagenProfesor);
                imagen.setImageBitmap(getCircularBitmapFrom(bitmap));*/

            }
            cursorNum = aux.Consulta("SELECT COUNT(*) FROM diiscover.ordenador where libre=1 AND id_lab="+id,2500);
            ResultSet resultNum = cursorNum.getResultSet ();
            resultNum.first();
            libres = resultNum.getInt(1);
            setTitle(nombre);
            pisoView = (TextView)findViewById(R.id.textPiso);
            pisoView.setText(piso+"");
            numeroView = (TextView)findViewById(R.id.textNumero);
            numeroView.setText(numero+"");
            numPcView = (TextView) findViewById(R.id.textPcLibre);
            numPcView.setText(libres+"");
        }
        catch(Exception a){
            a.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_laboratorio_pantalla, menu);

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

    public void actualizar(View v){
        Toast toast1 =
                Toast.makeText(LaboratorioPantalla.this,
                        "La información se obtiene através de Hendrix, puede tener un retraso de un par de minutos.", Toast.LENGTH_SHORT);

        toast1.show();
        MetodosAuxiliares aux = new MetodosAuxiliares();
        try{
            Cursor cursorNum = aux.Consulta("SELECT COUNT(*) FROM diiscover.ordenador where libre=1 AND id_lab="+id,2500);
            ResultSet resultNum = cursorNum.getResultSet();
            resultNum.first();
            libres = resultNum.getInt(1);
        }  catch(Exception a){
            a.printStackTrace();
        }


    }
}
