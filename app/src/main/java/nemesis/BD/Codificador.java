package nemesis.BD;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by inigo on 04/09/2015.
 */
public class Codificador {
    public static String codificar(String palabra){
        String codificada="";
        for (int i=0; i<palabra.length();i++){
            codificada=codificada+palabra.charAt(i);
        }
        String codificada2="";
        for (int i=0; i<codificada.length();i++){
            codificada2=codificada2+codificada.charAt(i)+codificada.charAt((i*3)%palabra.length());
        }
        codificada2="p"+codificada2+"xe";

        return codificada2;
    }
    public static String decodificar(String palabra){
        String decodificada1="";
        palabra= palabra.substring(1,palabra.length()-2);
        for (int i=0; i<palabra.length();i=i+2){
            decodificada1=decodificada1+palabra.charAt(i);
        }


        String decodificada="";

        for (int i=0; i<decodificada1.length();i++){
            decodificada=decodificada+decodificada1.charAt(i);
        }
        return decodificada;
    }
}
