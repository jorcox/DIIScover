package nemesis.diiscover;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import nemesis.BD.Cursor;

/**
 * Contiene la utilidad de envio automatico de emails.
 */
public class MailProfesores {

    private Context context;
    private static final String FROM = "diiscover.soporte@gmail.com";
    private static final String NOMBRE = "Diiscover";
    private static  String PASSWORD = "";
    private  static String ASUNTO = "";
    private  static String to = "";
    private String mensaje;

    public MailProfesores(Context ctx, String asunto,  String descripcion,String to) {
        context = ctx;
        this.ASUNTO=asunto; this.to=to;
        mensaje =   descripcion + "\n";
    }

    /**
     * Lanza una tarea asincrona en otro hilo de ejecucion, que se encarga del envio del
     * correo electronico.
     */
    public void enviar(Activity actividad) {
        Session session = createSessionObject();

            try {
                MetodosAuxiliares Maux= new MetodosAuxiliares();

                Cursor cursor=Maux.Consulta("SELECT * from pswd where nombre='correo'",1500);
                if (cursor != null) {
                    ResultSet result= cursor.getResultSet ();
                    result.next();
                    PASSWORD=result.getString("pswd");
                    ;
                }

                Message message = createMessage(FROM, ASUNTO, mensaje, session);
            Toast toast1 =
                    Toast.makeText(context,
                            "Enviando..", Toast.LENGTH_SHORT);

            toast1.show();
            new SendMailTask(context).execute(message);

            actividad.finish();

        } catch (AddressException e) {
            Toast toast1 =
                    Toast.makeText(context,
                            "Ha ocurrido un error al enviar el mensaje", Toast.LENGTH_SHORT);

            toast1.show();
            e.printStackTrace();
        } catch (MessagingException e) {
            Toast toast1 =
                    Toast.makeText(context,
                            "Ha ocurrido un error al enviar el mensaje", Toast.LENGTH_SHORT);

            toast1.show();
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Toast toast1 =
                    Toast.makeText(context,
                            "Ha ocurrido un error al enviar el mensaje", Toast.LENGTH_SHORT);

            toast1.show();
            e.printStackTrace();
        }
            catch (Exception e) {
            }
    }

    /**
     * Crea un mensaje a partir de sus componentes
     */
    private Message createMessage(String email, String subject, String messageBody, Session session)
            throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM, NOMBRE));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to, to));

        message.addRecipient(Message.RecipientType.TO, new InternetAddress("inigol22zgz@gmail.com", "inigol22zgz@gmail.com"));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    /**
     * Crea un objeto de sesion que permite conectarse con el servidor de correo
     */
    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM, PASSWORD);
            }
        });
    }

    /**
     * Tarea asincrona que envia el email utilizando un hilo de ejecucion independiente.
     */
    public class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;
        private Context context;

        public SendMailTask(Context ctx) {
            context = ctx;
        }

        /*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, "Por favor, espera", "Enviando mail",
                            true, false);
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
        */

        /**
         * Mientras se ejecuta la tarea asincrona, se enviara el mensaje a traves de la red.
         */
        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);

            } catch (MessagingException e) {

                e.printStackTrace();
            }

            return null;
        }
    }
}
