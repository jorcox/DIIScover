package nemesis.diiscover;

import java.util.Date;

/**
 * Created by inigo on 28/07/2015.
 */
public class Asignatura {
    protected int id=0;
    protected int idCarrera=0;
    protected byte []  imagen= null;
    protected String nombre="";
    protected String criteriosEvaluacion="";
    protected String descripcion="";
    protected String linkExterno="";
    protected int creditos=0;
    protected int cuatrimestre=0;
    protected String aula="";
    protected String aulaExamen="";
    protected Date fechaExamen=null;
    //protected String fechaExamen="";

    public Asignatura(int id,int idCarrera, String nombre, String criteros, String aula, String aulaExamen,int creditos, int cuatrimestre,Date fechaExamen,
                     String link, String descripcion, byte[] imagen){
        this.id=id;
        this.nombre=nombre;
        linkExterno=link;
        this.descripcion=descripcion;
        this.imagen=imagen;
        this.idCarrera=idCarrera;
        this.criteriosEvaluacion=criteros;
        this.aula=aula;
        this.aulaExamen=aulaExamen;
        this.creditos=creditos;
        this.cuatrimestre=cuatrimestre;
        this.fechaExamen=fechaExamen;

    }
}
