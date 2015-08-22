package nemesis.diiscover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;

public class ProfesorListadoActivity extends AppCompatActivity {
    Consulta consulta=null; RecyclerView recList=null;
    EditText profesor=null;
    ArrayList<Profesor> listaProfesores= new ArrayList();
    ArrayList<Profesor> listaProfesorestotal= new ArrayList();
    static View.OnClickListener myOnClickListener;
    Long idAsignatura=new Long(-1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_listado);
        Bundle extras = getIntent().getExtras();
        try{
             idAsignatura = extras.getLong("id", -1);
        }catch(Exception a){}

        myOnClickListener = new MyOnClickListenerProfesor(this);
        MetodosAuxiliares Maux= new MetodosAuxiliares();

        recList = (RecyclerView) findViewById(R.id.profesorlist);
        profesor = (EditText) findViewById(R.id.profesorText);
        //recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
        Cursor cursor=null;
        if (idAsignatura==-1){

             cursor=Maux.Consulta("SELECT * from profesor");
        }
        else{

            cursor=Maux.Consulta("SELECT * FROM profesor left join  diiscover.rel_profesor_asignatura on profesor.id= rel_profesor_asignatura.id_profesor where id_asignatura ="+idAsignatura);
            String nombreAsignatura=extras.getString("nombre", "");
            if (nombreAsignatura!=null && !nombreAsignatura.equals("")) setTitle(getTitle() + " de " +nombreAsignatura );
        }
        if (cursor == null) {

            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "No se pudo conectar con el servidor", Toast.LENGTH_SHORT);

            toast1.show();
        }
        try{

            ResultSet result= cursor.getResultSet ();
            while(result.next()){
                int id=result.getInt("id");
                String nombre=result.getString("nombre");
                String correo=result.getString("correo");
                String despacho=result.getString("despacho");
                String tutorias=result.getString("tutorias");
                byte [] bytes = result.getBytes("imagen");

                Profesor profesor= new Profesor ( id,  nombre, correo, tutorias,  despacho ,bytes);
                listaProfesorestotal.add(profesor);
            }


        }
        catch(Exception a){
            String aa= a.toString();

        }
        // evento del editText del profesor

        profesor.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                      try{
                          if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                  actionId == EditorInfo.IME_ACTION_DONE ||
                                  event.getAction() == KeyEvent.ACTION_DOWN &&
                                          event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                             if (!event.isShiftPressed()) {
                                  //
                                  filtradoProfesor( );


                                  return true; // consume.
                              }
                          }

                          return false; // pass on to other listeners.
                      }catch(Exception A){}
                        return false; // pass on to other listeners.
                    }
                });
        filtradoProfesor( );
        ProfesorAdapter ca = new ProfesorAdapter(listaProfesores);
        recList.setAdapter(ca);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profesor_lisatdo, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * Mirar El liestado de asignaturas para ver comentarios
     */
    public class MyOnClickListenerProfesor implements View.OnClickListener  {

        private final Context context;

        private MyOnClickListenerProfesor(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
           int selectedItemPosition = recList.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recList.findViewHolderForLayoutPosition(selectedItemPosition);

            TextView TextViewId
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewProfesorID);
            String selectedId = (String) TextViewId.getText();
            long idProfesor = Long.parseLong(selectedId);


            Intent i = new Intent(ProfesorListadoActivity.this, ProfesorPantalla.class);
            i.putExtra("idProfesor", idProfesor);

            startActivity (i);


        }

    }
    public void filtradoProfesor (){
        String filtroNombre=profesor.getText().toString();
        listaProfesores=new ArrayList();
        for (int i=0; i< listaProfesorestotal.size();i++){
            if( listaProfesorestotal.get(i).nombre.contains(filtroNombre)){
                listaProfesores.add(listaProfesorestotal.get(i));
            }
        }
        ProfesorAdapter ca = new ProfesorAdapter(listaProfesores);
        recList.setAdapter(ca);
    }


    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);
        guardarEstado.putString("textoProfe", profesor.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
        profesor.setText(recEstado.getString("textoProfe"));
        filtradoProfesor();
    }



}
