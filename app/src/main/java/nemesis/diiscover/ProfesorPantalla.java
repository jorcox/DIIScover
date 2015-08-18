package nemesis.diiscover;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;

import nemesis.BD.Cursor;

public class ProfesorPantalla extends AppCompatActivity {
    private Long id;
    private String nombre="";
    private String tutorias="";
    private String correo="";
    private String despacho="";
    private ArrayList <String> asignaturas = new ArrayList<>();
    private TextView despachoView;
    private ImageView imagen;
    private TextView asignaturasView;
    private TextView correoView;
    private TextView tutoriasView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor_pantalla);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("idProfesor", -1);
        MetodosAuxiliares Maux= new MetodosAuxiliares();
        Cursor cursorDatos=null;
        cursorDatos=Maux.Consulta("SELECT * FROM profesor where id ="+id);
        Cursor cursorAsignaturas=null;
        cursorAsignaturas=Maux.Consulta("SELECT * FROM rel_profesor_asignatura left join asignatura on  asignatura.id=rel_profesor_asignatura.id_asignatura where id_profesor ="+id);

        try{

            ResultSet result= cursorDatos.getResultSet ();
            while(result.next()){
                nombre=result.getString("nombre");
                tutorias=result.getString("tutorias");
                correo=result.getString("correo");
                despacho=result.getString("despacho");
                byte [] bytes = result.getBytes("imagen");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imagen= (ImageView)findViewById(R.id.imagenProfesor);
                imagen.setImageBitmap(getCircularBitmapFrom(bitmap));

            }
             result= cursorAsignaturas.getResultSet ();
            while(result.next()){
                asignaturas.add(result.getString("nombre"));
            }

        }
        catch(Exception a){
            String aa= a.toString();

        }

        try{
            setTitle(nombre);
            tutoriasView= (TextView)findViewById(R.id.textViewtutoriasProfesor);
            tutoriasView.setText(tutorias);
            despachoView= (TextView)findViewById(R.id.textViewDespachoProfesor);
            despachoView.setText(despacho);


            correoView= (TextView)findViewById(R.id.textViewCorreoProfesor);
            correoView.setText(correo);
            asignaturasView= (TextView)findViewById(R.id.textViewAsignaturasProfesor);
            for (int i=0;i<asignaturas.size();i++){

                if (i==0) {
                    asignaturasView.setText(asignaturasView.getText()+ asignaturas.get(i));
                }
                else{
                    asignaturasView.setText(asignaturasView.getText()+", " + asignaturas.get(i));
                }
            }

        }
        catch(Exception a){
            String aa= a.toString();

        }

    }
    public void irAEnviarCorreo(View vista) {
        Intent i = new Intent(this, CorreoProfesor.class);
        i.putExtra("profesor",nombre);
        i.putExtra("correo",correo);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profesor_pantalla, menu);
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
    public  static Bitmap getCircularBitmapFrom(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        float radius = bitmap.getWidth() > bitmap.getHeight() ? ((float) bitmap
                .getHeight()) / 2f : ((float) bitmap.getWidth()) / 2f;
        Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                radius, paint);

        return canvasBitmap;
    }


}
