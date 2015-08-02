package nemesis.diiscover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;

public class AsignaturaListadoActivity extends AppCompatActivity {
    Consulta consultaUsarios=null; RecyclerView recList=null;
    ArrayList<Asignatura> listaAsignaturas= new ArrayList();
    private Spinner cuatrimestres;
    private ArrayAdapter adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_listado);

/*
        //AQUI FALTA CARGAR DESDE LA VIEW EL SPINNER
        int numeroCuatrimestresCarrera=8;//CARGAR DE BD




        String[] cuatris=new String[numeroCuatrimestresCarrera];
        for (int i=1;i<=numeroCuatrimestresCarrera;i++){
            cuatris[i-1]=String.valueOf(i);
        }
        adaptador = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, cuatris);
        adaptador.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        cuatrimestres.setAdapter(adaptador);
        cuatrimestres.setSelection(1);

*/
        MetodosAuxiliares Maux= new MetodosAuxiliares();

        Bundle extras = getIntent().getExtras();
        Long idCar = extras.getLong("CarreraId", -1);
        //necesitas filtrar cuatrimestre y especialidad.

        Cursor cursor=Maux.Consulta("SELECT * FROM asignatura where id_carrera="+idCar);

        try{

            ResultSet result= cursor.getResultSet ();
            while(result.next()){
                int id=result.getInt("id");
                int idCarrera=result.getInt("id_carrera");
                int creditos=result.getInt("creditos");
                int cuatrimestre=result.getInt("cuatrimestre");
                Date fechaExamen=result.getDate("fechaExamen");
                String nombre=result.getString("nombre");
                String descripcion=result.getString("descripcion");
                String criteros=result.getString("criteriosEvaluacion");
                String aula=result.getString("aula");
                String aulaExamen=result.getString("aulaExamen");
                String link=result.getString("linkExterno");
                byte [] bytes = result.getBytes("imagen");

                // InputStream is = result.getBinaryStream("imagen");
                //  BufferedInputStream  bufferedInputStream = new BufferedInputStream(is);

                // Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                Asignatura asignatura= new Asignatura (  id, idCarrera,  nombre,  criteros,  aula,  aulaExamen, creditos,  cuatrimestre, fechaExamen,
                         link,  descripcion, bytes);
                listaAsignaturas.add(asignatura);
            }


        }
        catch(Exception a){
            String aa= a.toString();

        }

        recList = (RecyclerView) findViewById(R.id.asignaturalist);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
        AsignaturaAdapter ca = new AsignaturaAdapter(listaAsignaturas);
        recList.setAdapter(ca);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asignatura_listado, menu);
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
