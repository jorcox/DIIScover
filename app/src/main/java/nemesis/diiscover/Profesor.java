package nemesis.diiscover;

/**
 * Created by inigo on 28/07/2015.
 */
public class Profesor {
    protected int id=0;
    protected    byte []  imagen= null;
    protected String correo="";
    protected String nombre="";
    protected String tutorias="";
    protected String url="";
    protected String despacho="";
//ImageView, Textview(nombre) Textview(coordinador)
    public Profesor(int id, String nombre, String correo, String tutorias, String despacho ,byte[] imagen){
        this.id=id;
        this.nombre=nombre;
        this.correo=correo;
        this.despacho=despacho;
        this.tutorias=tutorias;
        this.imagen=imagen;
    }
}
