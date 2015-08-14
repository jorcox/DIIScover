package nemesis.diiscover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;

public class AsignaturaListadoActivity extends AppCompatActivity {
    Consulta consultaUsarios=null; RecyclerView recList=null;
    ArrayList<Asignatura> listaAsignaturas= new ArrayList();
    private Spinner cuatrimestres;
    private ArrayAdapter adaptadorCuatrimestres;
    private Spinner especialidad;
    private Long idCar;
    private String nombreCarrera="";
    private ArrayAdapter adaptadorEspecialidad;
    static View.OnClickListener myOnClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_listado);
        Bundle extras = getIntent().getExtras();
        //obtienes los atributos de la anteiorr pantalla
         idCar = extras.getLong("CarreraId", -1);
        nombreCarrera = extras.getString("nombre", "Lista asignaturas");
        Integer numCuatris = extras.getInt("CarreraCuatris", 0);
        setTitle(nombreCarrera);

        //enlazas el onclick de los cardviews que va a contener (se define esta clase mas abajo)
        myOnClickListener = new MyOnClickListenerAsignatura(this);
        //ahora falta en su adapter en onCreateViewHolder activarlo
        MetodosAuxiliares Maux= new MetodosAuxiliares();

        cuatrimestres = (Spinner) findViewById(R.id.spinnercuatri);
        especialidad = (Spinner) findViewById(R.id.spinnerespecialidad);
        /*
        Inicializas los Spinners (Este es el de los cautrimestres)
         */
        String[] cuatris=new String[numCuatris];
        for (int i=1;i<=numCuatris;i++){
            cuatris[i-1]="     "+String.valueOf(i)+"     ";
        }
        //creas el adaptador
        adaptadorCuatrimestres = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, cuatris);

        adaptadorCuatrimestres.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);

        cuatrimestres.setAdapter(adaptadorCuatrimestres);
        cuatrimestres.setSelection(0);
        ArrayList<String> cuatri= new ArrayList<String>();
        /*
        Inicializas los Spinners (Este es el de los especialidad)
         */
        Cursor cursorEspec=Maux.Consulta("SELECT * FROM especialidad");
        try{

            ResultSet result= cursorEspec.getResultSet ();
            while(result.next()){
                String nombre=result.getString("nombre");
                cuatri.add(nombre);
            }
        }
        catch(Exception a){
            String aa= a.toString();

        }


        adaptadorEspecialidad = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, cuatri);
        adaptadorEspecialidad.setDropDownViewResource(android.R.layout.
                simple_spinner_dropdown_item);
        especialidad.setAdapter(adaptadorEspecialidad);
        especialidad.setSelection(0);


        //obtienes los datos d necesarios para las asignaturas

        String consulta="Select especialidad.nombre as especialidad, asignatura.aula, asignatura.aulaExamen,asignatura.creditos," +
                " asignatura.criteriosEvaluacion, asignatura.id_carrera,asignatura.cuatrimestre,asignatura.descripcion,asignatura.fechaExamen," +
                " asignatura.id,asignatura.linkExterno,asignatura.nombre from asignatura_especialidad ases" +
                " left join asignatura asignatura on asignatura.id=ases.id_asignatura" +
                " left join especialidad especialidad on especialidad.id=ases.id_especialidad"+
                " where id_carrera="+idCar+" and especialidad.nombre  like '"+  especialidad.getSelectedItem().toString()
                +"' and asignatura.cuatrimestre="+ cuatrimestres.getSelectedItem().toString();
        Cursor cursor=Maux.Consulta(consulta);
        if (cursor == null) {

            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "No se pudo conectar con el servidor", Toast.LENGTH_SHORT);

            toast1.show();
        }
        listaAsignaturas= new ArrayList();
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


                // InputStream is = result.getBinaryStream("imagen");
                //  BufferedInputStream  bufferedInputStream = new BufferedInputStream(is);

                // Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                Asignatura asignatura= new Asignatura (  id, idCarrera,  nombre,  criteros,  aula,  aulaExamen, creditos,  cuatrimestre, fechaExamen,
                         link,  descripcion);
                listaAsignaturas.add(asignatura);
            }
        //activas los listeners de los spinners
            addListenerOnSpinnerItemSelection();
            addListenerOnSpinnerItemSelectioncuatri();
        }
        catch(Exception a){
            String aa= a.toString();

        }
        //terminas de inicializar el recicleList
        recList = (RecyclerView) findViewById(R.id.asignaturalist);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
        AsignaturaAdapter ca = new AsignaturaAdapter(listaAsignaturas);
        recList.setAdapter(ca);
    }


    /**
     * listeners de los spinners
     */
    public void addListenerOnSpinnerItemSelection() {
        especialidad = (Spinner) findViewById(R.id.spinnerespecialidad);
        especialidad.setOnItemSelectedListener(new AsignaturaCustomOnItemSelectedListener());
    }
    public void addListenerOnSpinnerItemSelectioncuatri() {
        cuatrimestres = (Spinner) findViewById(R.id.spinnercuatri);
        cuatrimestres.setOnItemSelectedListener(new AsignaturaCustomOnItemSelectedListener());
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
        if (id == R.id.action_incidencia) {
            Intent i = new Intent(this, IncidenciaActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.action_desconexion) {
            Intent i = new Intent(this, InicioActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Listeners de los spinners para actualizar lo que se muesra
     */
    public class AsignaturaCustomOnItemSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

            listaAsignaturas= new ArrayList();
            MetodosAuxiliares Maux= new MetodosAuxiliares();
            String consulta="Select especialidad.nombre as especialidad, asignatura.aula, asignatura.aulaExamen,asignatura.creditos," +
                    " asignatura.criteriosEvaluacion, asignatura.id_carrera,asignatura.cuatrimestre,asignatura.descripcion,asignatura.fechaExamen," +
                    " asignatura.id,asignatura.linkExterno,asignatura.nombre from asignatura_especialidad ases" +
                    " left join asignatura asignatura on asignatura.id=ases.id_asignatura" +
                    " left join especialidad especialidad on especialidad.id=ases.id_especialidad"+
                    " where id_carrera="+idCar+" and especialidad.nombre  like '"+  especialidad.getSelectedItem().toString()
                    +"' and asignatura.cuatrimestre="+ cuatrimestres.getSelectedItem().toString();
            Cursor cursor=Maux.Consulta(consulta);
            if (cursor == null) {

                Toast toast1 =
                        Toast.makeText(getApplicationContext(),
                                "No se pudo conectar con el servidor", Toast.LENGTH_SHORT);

                toast1.show();
            }
            try{

                ResultSet result= cursor.getResultSet ();
                while(result.next()){
                    int idPropio=result.getInt("id");
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


                    // InputStream is = result.getBinaryStream("imagen");
                    //  BufferedInputStream  bufferedInputStream = new BufferedInputStream(is);

                    // Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                    Asignatura asignatura= new Asignatura (  idPropio, idCarrera,  nombre,  criteros,  aula,  aulaExamen, creditos,  cuatrimestre, fechaExamen,
                            link,  descripcion);
                    listaAsignaturas.add(asignatura);
                }
                AsignaturaAdapter ca = new AsignaturaAdapter(listaAsignaturas);
                recList.setAdapter(ca);
                addListenerOnSpinnerItemSelection();
                addListenerOnSpinnerItemSelectioncuatri();
            }
            catch(Exception a){
                String aa= a.toString();

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }
    /**
     * Listeners de los cardviews para cuadno los clican
     */
    public class MyOnClickListenerAsignatura implements View.OnClickListener  {

        private final Context context;

        private MyOnClickListenerAsignatura(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int selectedItemPosition = recList.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recList.findViewHolderForLayoutPosition(selectedItemPosition);

            TextView TextViewId
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewAsignaturaID);
            String selectedId = (String) TextViewId.getText();
            long idAsignatura = Long.parseLong(selectedId);
            Intent i = new Intent(AsignaturaListadoActivity.this, AsignaturaPantalla.class);
            i.putExtra("idAsignatura", idAsignatura);
            startActivity (i);


        }

    }    /**
     * Listeners de los botones de los cardviws para cuadno los clican
     */
public void botonSelecionado(View v){
    Button b = (Button)v;
    String buttonText = b.getText().toString();
    Intent i = new Intent(AsignaturaListadoActivity.this, AsignaturaPantalla.class);
    i.putExtra("nombreAsignatura", buttonText);
    startActivity (i);
}

}
