package nemesis.diiscover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.Date;

import nemesis.BD.Cursor;

public class AsignaturaPantalla extends AppCompatActivity {
    private Long id;
    private String nombre="";
    private int creditos=0;
    private String criterios="";
    private String descripcion="";
    private String aulaExamen="";
    private String aula="";
    private String linkExterno="";
    private Date fechaExamen=null;
    private TextView aulaView;
    private TextView aulaExamenView;
    private TextView creditosView;
    private TextView criteriosView;
    private TextView fechaExamenView;
    private TextView descripcionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignatura_pantalla);
        //se obtiene la informacin de la anterior vista
        Bundle extras = getIntent().getExtras();
        boolean desdeBoton=true;

        /**
         * Aqui se ha podido venir desde el evento del boton (que tiene el cardview) o desde el onclick event del cardview
         * desde el boton se viene con el nombre de la asignatura, desde el evento viene el id de la asignaturaa
         */
            id = extras.getLong("idAsignatura", -1);
           if(id==-1) {
               desdeBoton=true;
               nombre = extras.getString("nombreAsignatura", "");
            }


        MetodosAuxiliares Maux= new MetodosAuxiliares();
        Cursor cursor=null;
        if(desdeBoton)cursor=Maux.Consulta("SELECT * FROM asignatura where nombre ='"+nombre+"'");
        else cursor=Maux.Consulta("SELECT * FROM asignatura where id ="+id);

        try{

            ResultSet result= cursor.getResultSet ();
            while(result.next()){
                 nombre=result.getString("nombre");
                 id=result.getLong("id");
                  creditos=result.getInt("creditos");
                  criterios=result.getString("criteriosEvaluacion");
                  descripcion=result.getString("descripcion");
                  aulaExamen=result.getString("aulaExamen");
                  linkExterno=result.getString("linkExterno");
                  fechaExamen=result.getDate("fechaExamen");
                  aula=result.getString("aula");

            }
        }
        catch(Exception a){
            String aa= a.toString();

        }
        try{
            setTitle(nombre);
            aulaView= (TextView)findViewById(R.id.textViewDespachoProfesor);
            aulaView.setText(aula);
            aulaExamenView= (TextView)findViewById(R.id.textViewAulaExamenAsignatura);
            aulaExamenView.setText(aulaExamen);
            creditosView= (TextView)findViewById(R.id.textViewCorreoProfesor);
            creditosView.setText(String.valueOf(creditos));
            criteriosView= (TextView)findViewById(R.id.textViewtutoriasProfesor);
            criteriosView.setText(criterios);
            fechaExamenView= (TextView)findViewById(R.id.textViewFechaExamenAsignatura);
            fechaExamenView.setText(fechaExamen.toString());
            descripcionView= (TextView)findViewById(R.id.textViewDescripcionAsignatura);
            descripcionView.setText(descripcion);
        }
        catch(Exception a){
            String aa= a.toString();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_asignatura_pantalla, menu);
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
        if (id == R.id.action_profesores) {

            Intent i = new Intent(AsignaturaPantalla.this, ProfesorListadoActivity.class);
            i.putExtra("id",  (this.id));
            i.putExtra("nombre",  (this.nombre));
            startActivity (i);
            return true;
        }
//
        return super.onOptionsItemSelected(item);
    }
}
