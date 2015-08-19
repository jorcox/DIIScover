package nemesis.diiscover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Cursor;

public class LaboratorioPantalla extends AppCompatActivity {

    private Long id;
    private String nombre = "";
    private int piso = 0;
    private int numero = 0;
    private ArrayList<String> asignaturas = new ArrayList<>();
    private ImageView imagen;
    private TextView numeroView;
    private TextView numPcView;
    private TextView pisoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Cursor cursorLab = null;
        MetodosAuxiliares aux = new MetodosAuxiliares();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_pantalla);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("idLab", -1);
        if (id != -1){
            cursorLab = aux.Consulta("SELECT * FROM diiscover.laboratorio where id =" + id);
        } else {
            nombre = extras.getString("nombre");
            cursorLab = aux.Consulta("SELECT * FROM diiscover.laboratorio where nombre = '" + nombre + "'");
        }



        try{

            ResultSet result = cursorLab.getResultSet ();
            while(result.next()){
                nombre = result.getString("nombre");
                piso = result.getInt("piso");
                numero = result.getInt("numero");
                /* Imagen por si se pone en el futuro */
               /* byte [] bytes = result.getBytes("imagen");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagen= (ImageView)findViewById(R.id.imagenProfesor);
                imagen.setImageBitmap(getCircularBitmapFrom(bitmap));*/

            }
            setTitle(nombre);
            pisoView = (TextView)findViewById(R.id.textPiso);
            pisoView.setText(piso+"");
            numeroView = (TextView)findViewById(R.id.textNumero);
            numeroView.setText(numero+"");
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
}
