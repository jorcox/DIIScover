package nemesis.diiscover;

/**
 * Created by Jorge on 02/08/2015.
 */
public class Laboratorio {

    protected int id=0;
    protected byte []  imagen= null;
    protected String nombre="";
    protected String piso="";
    protected String numero="";
    //ImageView, Textview(nombre) Textview(coordinador)
    public Laboratorio (int id, String nombre, String piso, String numero, byte []  imagen){
        this.id=id;
        this.nombre=nombre;
        this.piso=piso;
        this.numero=numero;
        this.imagen=imagen;
    }


}
