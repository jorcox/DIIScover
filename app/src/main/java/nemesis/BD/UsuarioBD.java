package nemesis.BD;



/**
 * Created by inigo on 23/07/2015.
 */
public class UsuarioBD {
    String nombre="";
    JDBCTemplate con = null;
    public  String unUsuario(){
        String usuario="";
        Consulta consultaUsuario=new Consulta("Select * from Usuario where id='1'");

        consultaUsuario.execute();
        //ATENCIÓN ESTO LO HAGO PORQUE ME TIENE QUE DEVOLVER ALGO, SINO NO HABRÍA QUE ESPERAR NADA
        while   (consultaUsuario.cursor==null){
            try{
                Thread.sleep(50);
            }catch (Exception a){}
    }
        try{

            consultaUsuario.cursor.getResultSet().next();
            usuario=consultaUsuario.cursor.getString("usuario");
        }catch (Exception a){}
        return usuario;
    }

}
