package nemesis.BD;


/**
 * Created by inigo on 23/07/2015.
 */
public class UsuarioBD {
    Consulta consulta;
    String usuario = "";
    Cursor cursor;
    JDBCTemplate con = null;
    public  String unUsuario(){
        consulta = new Consulta(con, "Select * from Usuario");
        consulta.execute();
        return usuario;
    }



}
