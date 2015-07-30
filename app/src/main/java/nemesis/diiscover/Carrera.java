package nemesis.diiscover;

/**
 * Created by inigo on 28/07/2015.
 */
public class Carrera {
    protected int id=0;
    protected String nombre="";
    protected String coordinador="";
    protected String descripcion="";
    protected String linkExterno="";
    protected String fotoURL="";
    protected int cuatrimestres=0;
//ImageView, Textview(nombre) Textview(coordinador)
    public Carrera (int id, String nombre,String coordinador,String link, String descripcion, String url, int cuatrimestres){
        this.id=id;
        this.nombre=nombre;
        this.coordinador=coordinador;
        linkExterno=link;
        this.descripcion=descripcion;
        fotoURL=url;
        this.cuatrimestres=cuatrimestres;
    }
}
