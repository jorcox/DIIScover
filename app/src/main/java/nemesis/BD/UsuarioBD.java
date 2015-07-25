package nemesis.BD;


/**
 * Created by inigo on 23/07/2015.
 */
public class UsuarioBD {
    String nombre="";
    JDBCTemplate con = null;
    public  String unUsuario(){
        String usuario="";
        Consulta consultaUsuario=new Consulta(con,"Select * from Usuario where id='1'");

        consultaUsuario.execute();
        return usuario;
    }

    public void finalizar(android.database.Cursor cursor){

        try{

            cursor.getResultSet().next();

            usuario=consultaUsuario.cursor.getString("usuario");

        }catch (Exception a){}
    }

}
