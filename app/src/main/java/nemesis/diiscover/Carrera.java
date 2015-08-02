package nemesis.diiscover;

import android.graphics.Bitmap;

import java.sql.Blob;

/**
 * Created by inigo on 28/07/2015.
 */
public class Carrera {
    protected int id=0;
    protected    byte []  imagen= null;
    protected String nombre="";
    protected String coordinador="";
    protected String descripcion="";
    protected String linkExterno="";
    protected String tipoCarrera="";
    protected int cuatrimestres=0;
//ImageView, Textview(nombre) Textview(coordinador)
    public Carrera (int id, String nombre,String coordinador,String link, String descripcion , int cuatrimestres, String tipoCarrera,   byte []  imagen){
        this.id=id;
        this.nombre=nombre;
        this.coordinador=coordinador;
        linkExterno=link;
        this.descripcion=descripcion;
        this.cuatrimestres=cuatrimestres;
        this.tipoCarrera=tipoCarrera;
        this.imagen=imagen;
    }
}
