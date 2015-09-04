package nemesis.diiscover;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Contiene la utilidad de envio automatico de emails.
 */
public class Mail {

    private Context context;
    private static final String FROM = "diiscover.soporte@gmail.com";
    private static final String NOMBRE = "Detector de Incidencias";
    private static final String PASSWORD = "jamarro11";
    private static final String ASUNTO = "Detectada incidencia en DIIScover";
    private String mensaje;

    public Mail(Context ctx, int usuario, String fecha, String descripcion) {
        context = ctx;
        String inicio = "Incidencia detectada:\n";
        String _usuario = "\nId usuario: ";
        String _fecha = "\nFecha: ";
        mensaje = inicio + _usuario + usuario + _fecha + fecha + "\n\n" + descripcion + "\n";
    }

    /**
     * Lanza una tarea asincrona en otro hilo de ejecucion, que se encarga del envio del
     * correo electronico.
     */
    public void enviar() {
        Session session = createSessionObject();

        try {
            Message message = createMessage(FROM, ASUNTO, mensaje, session);
            new SendMailTask(context).execute(message);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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
