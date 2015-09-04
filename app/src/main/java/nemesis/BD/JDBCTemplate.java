package nemesis.BD;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Gestiona una conexión a una base de datos MySQL
 */
public class JDBCTemplate {
    
    /**
     * Sólo una conexión por usuario
     */
    private static JDBCTemplate singleton = null;
    
    /**
     * Cadena de caracteres con el nombre de usuario, o login, a emplear para
     * conectarse a la BD
     */
    private String user = "";

    /**
     * Cadena de caracteres con el password, o contraseña, a emplear para
     * conectarse a la BD
     */
    private String password = "";

    /**
     * Conexión con la BD
     */
    private Connection connection = null;

    /**
     * Configuración del driver
     */
    private MySQLConfiguration config = null;

    /**
     * Asigna los valores de usuario, password, host, puerto y nombre de la
     * base de datos, para que posteriormente pueda hacerse la conexión
     */
    private JDBCTemplate(MySQLConfiguration config, String user, String password)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        this.config = config;
        this.user = user;
        this.password = password;
        Class.forName(config.getDriver()).newInstance();
    }
    
    /**
     * Conecta con la base de datos si no había conexión previa
     * @return Conexión con la base de datos
     */
    public static JDBCTemplate getJDBCTemplate(){
        return getJDBCTemplate(false);
    }
    
    /**
     * Conecta con la base de datos si no había conexión previa o reinicia la
     * conexión
     * @param forceReload true para reiniciar la conexión, false en caso contrario
     * @return Conexión con la base de datos
     */
    public static JDBCTemplate getJDBCTemplate(boolean forceReload){
        if (forceReload){
            singleton.close();
            singleton = null;
        }
        if (singleton == null){
            Properties prop = new Properties();
            try {
                InputStream in = JDBCTemplate.class.getClassLoader().getResourceAsStream("database");
                prop.load(in);
                singleton = configureMySQL(prop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return singleton;
    }
    
    /**
     * Establece una conexión con la base de datos MySQL a partir de un
     * fichero de configuración
     * @param prop Propiedades del fichero de configuración
     * @return Conexión con la base de datos
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     */
   private static JDBCTemplate configureMySQL(Properties prop)
       throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        JDBCTemplate mysql = new JDBCTemplate(new MySQLConfiguration(prop.getProperty("database.mysql.host"),
                        prop.getProperty("database.mysql.port"),
                        prop.getProperty("database.mysql.dbname")),
                Codificador.decodificar(prop.getProperty("database.mysql.user")),
                               Codificador.decodificar(prop.getProperty("database.mysql.password")));
        mysql.connect();
        return mysql;
   }

    /**
     * Establece la conexión con la BD
     * @throws java.sql.SQLException
     */
    private void connect() throws SQLException {

        connection = DriverManager.getConnection(config.getURL(), user, password);
    }

    /**
     * Cierra la conexión con la BD para liberar recursos
     */
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlE) {}
        connection = null;
        singleton = null;
    }
    
    /**
     * Realiza una consulta SQL a la BD (una sentencia SELECT)
     * @param sql sentencia SQL
     * @return Cursor sobre el resultado de la consulta
     */
    public Cursor executeQuery(String sql) {
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            return new Cursor(stmt.executeQuery(sql));
        } catch (SQLException e1) {
            System.err.println(e1.getMessage());
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e2) {}
            }
        }
        return new Cursor(null);
    }

    /**
     * Ejecuta una sentencia SQL que no sea una consulta, es decir, que no
     * devuelva una tabla como resultado (INSERT, UPDATE, DELETE, ...)
     * @param sql sentencia SQL
     * @return -1 si ha habido algún error, y otro en caso contrario
     */
    public int executeSentence(String sql) {
        Statement stmt = null;
        int res = -1;
        try {
            stmt = connection.createStatement();
            res = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {}
            }
        }
        return res;
    }
    
    /**
     * Obtiene la conexión con la base de datos
     * @return Conexión con la base de datos
     */
    public Connection getConnection(){
    	return connection;
    }

    @Override
    public String toString() {
        return config.getURL();
    }
}