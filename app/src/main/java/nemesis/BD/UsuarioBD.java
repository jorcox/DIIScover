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

        while   (consultaUsuario.cursor==null){
            try{
                Thread.sleep(50);
                consultaUsuario.cursor.getResultSet().next();
                usuario=consultaUsuario.cursor.getString("usuario");
            }catch (Exception a){}

    }
        return usuario;
    }

}
