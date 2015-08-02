package nemesis.diiscover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;

public class LaboratorioListado_Activity extends AppCompatActivity {

    Consulta consultaLaboratorios = null;
    RecyclerView recList = null;
    ArrayList<Laboratorio> listaLaboratorios = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_listado_);

        MetodosAuxiliares aux = new MetodosAuxiliares();
        Cursor cursor = aux.Consulta("SELECT Laboratorio.id, Laboratorio.nombre, Laboratorio.imagen, Laboratorio.piso, Laboratorio.numero FROM diiscover.laboratorio ");

        try{

            ResultSet result = cursor.getResultSet();
            while(result.next()){
                int id = result.getInt("id");
                String nombre = result.getString("nombre");
                String piso = result.getString("piso");
                String numero = result.getString("numero");

                //InputStream is = result.getBinaryStream("imagen");
                //BufferedInputStream  bufferedInputStream = new BufferedInputStream(is);

                //Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                byte [] bytes = result.getBytes("imagen");

                Laboratorio lab = new Laboratorio ( id,  nombre, piso, numero,  bytes);
                listaLaboratorios.add(lab);
            }


        }
        catch(Exception a){
            a.printStackTrace();
        }

        recList = (RecyclerView) findViewById(R.id.laboratoriolist);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);
        recList.setItemAnimator(new DefaultItemAnimator());
        LaboratorioAdapter ca = new LaboratorioAdapter(listaLaboratorios);
        recList.setAdapter(ca);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_laboratorio_listado_, menu);
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