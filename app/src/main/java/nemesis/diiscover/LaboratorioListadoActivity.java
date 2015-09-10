package nemesis.diiscover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Consulta;
import nemesis.BD.Cursor;

public class LaboratorioListadoActivity extends AppCompatActivity {

    Consulta consultaLaboratorios = null;
    RecyclerView recList = null;
    ArrayList<Laboratorio> listaLaboratorios = new ArrayList();

    static View.OnClickListener myOnClickListener;
    Long idLaboratorio = new Long(-1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_listado_);

        myOnClickListener = new MyOnClickListenerLaboratorio(this);

        MetodosAuxiliares aux = new MetodosAuxiliares();
        Cursor cursor = aux.Consulta("SELECT Laboratorio.id, Laboratorio.nombre, Laboratorio.piso, Laboratorio.numero FROM diiscover.laboratorio ",2500);
        if (cursor == null) {

            Toast toast1 =
                    Toast.makeText(getApplicationContext(),
                            "No se pudo conectar con el servidor", Toast.LENGTH_SHORT);

            toast1.show();
        }
        try{

            ResultSet result = cursor.getResultSet();
            while(result.next()){
                int id = result.getInt("id");
                String nombre = result.getString("nombre");
                String piso = result.getString("piso");
                String numero = result.getString("numero");

                Laboratorio lab = new Laboratorio ( id,  nombre, piso, numero);
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
    protected void onRestoreInstanceState(Bundle recEstado) {
        super.onRestoreInstanceState(recEstado);
    }

    protected void onSaveInstanceState(Bundle guardarEstado) {
        super.onSaveInstanceState(guardarEstado);


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

    public void botonSelecionado(View v){
        Button b = (Button)v;
        String buttonText = b.getText().toString();
        Intent i = new Intent(LaboratorioListadoActivity.this, LaboratorioPantalla.class);
        i.putExtra("nombre", buttonText);
        startActivity (i);
    }

    public class MyOnClickListenerLaboratorio implements View.OnClickListener  {

        private final Context context;

        private MyOnClickListenerLaboratorio(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            int selectedItemPosition = recList.getChildAdapterPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recList.findViewHolderForLayoutPosition(selectedItemPosition);

            TextView TextViewId
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewLaboratorioID);
            String selectedId = (String) TextViewId.getText();
            long idLab = Long.parseLong(selectedId);


            Intent i = new Intent(LaboratorioListadoActivity.this, LaboratorioPantalla.class);
            i.putExtra("idLab", idLab);

            startActivity (i);


        }

    }


}
