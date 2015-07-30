package nemesis.diiscover;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;


public class CarreraListadoActivity extends ActionBarActivity  {
    Consulta consultaUsarios=null; RecyclerView recList=null;
    ArrayList<Carrera> listaCarreras= new ArrayList<Carrera>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_carrera);

        MetodosAuxiliares Maux= new MetodosAuxiliares();
        Cursor cursor=Maux.Consulta("Select * from Carrera");

        try{

            ResultSet result= cursor.getResultSet ();
            while(result.next()){
                int id=result.getInt("id");
                String nombre=result.getString("nombre");
                String coordinador=result.getString("coordinador");
                String descripcion=result.getString("descripcion");
                String linkExterno=result.getString("linkExterno");
                String fotoURL=result.getString("URL");
                int cuatrimestres=result.getInt("cuatrimestres");
                Carrera carrera= new Carrera ( id,  nombre, coordinador, linkExterno,  descripcion,  fotoURL,  cuatrimestres);
                listaCarreras.add(carrera);
            }


        }  catch(Exception a){

        }

        recList = (RecyclerView) findViewById(R.id.carreralist);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
        listaCarreras.add (new Carrera ( 1,  "Inforatica", "Coord", "link",  "Descrip",  "http://salud-bucal-b5.wikispaces.com/file/view/informatica.jpg/502178332/informatica.jpg",  1));
        CarreraAdapter ca = new CarreraAdapter(listaCarreras);
        recList.setAdapter(ca);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listado_grado, menu);
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
