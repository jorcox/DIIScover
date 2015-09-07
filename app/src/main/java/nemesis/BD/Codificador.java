package nemesis.BD;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
/**
 * Created by inigo on 04/09/2015.
 */
public class Codificador {
    public static String codificar(String palabra){
        byte[] encodedBytes = Base64.encodeBase64(palabra.getBytes());
        String X= new String(encodedBytes);
        return X;
    }
    public static String decodificar(String palabra){


        byte[] decodedBytes = Base64.decodeBase64(palabra.getBytes());
        System.out.println("decodedBytes " + new String(decodedBytes));

        return new String(decodedBytes);
    }
}
