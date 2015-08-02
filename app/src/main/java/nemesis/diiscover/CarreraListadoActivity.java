package nemesis.diiscover;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;


public class CarreraListadoActivity extends AppCompatActivity {
    Consulta consultaUsarios=null; RecyclerView recList=null;
    ArrayList<Carrera> listaCarreras= new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_carrera);

        MetodosAuxiliares Maux= new MetodosAuxiliares();
        Cursor cursor=Maux.Consulta("SELECT Carrera.nombre,Carrera.imagen,Carrera.descripcion,Carrera.linkExterno,Carrera.cuatrimestres ,Carrera.id,Carrera.coordinador, " +
                "tipo_carrera.nombre as tipoCarrera FROM diiscover.carrera inner join tipo_carrera where id_tipo_carrera=tipo_carrera.id");

        try{

            ResultSet result= cursor.getResultSet ();
            while(result.next()){
                int id=result.getInt("id");
                String nombre=result.getString("nombre");
                String coordinador=result.getString("coordinador");
                String descripcion=result.getString("descripcion");
                String linkExterno=result.getString("linkExterno");
                String tipoCarrera=result.getString("tipoCarrera");
                int cuatrimestres=result.getInt("cuatrimestres");

               // InputStream is = result.getBinaryStream("imagen");
              //  BufferedInputStream  bufferedInputStream = new BufferedInputStream(is);

               // Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                byte [] bytes = result.getBytes("imagen");

                Carrera carrera= new Carrera ( id,  nombre, coordinador, linkExterno,  descripcion,  cuatrimestres,tipoCarrera,bytes);
                listaCarreras.add(carrera);
            }


        }
        catch(Exception a){
            String aa= a.toString();

        }

        recList = (RecyclerView) findViewById(R.id.carreralist);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
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
