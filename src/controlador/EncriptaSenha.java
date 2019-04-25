/*
 * EncriptaSenha.java
 *
 * Created on 4 de Setembro de 2007, 15:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package controlador;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Encoder;


/**
 *
 * @author luiz
 */
public class EncriptaSenha {
    
    public EncriptaSenha() {
        
    }
    
    public byte[][] Cifra(PublicKey chavePublica, byte[] textoClaro) 
    throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, 
    IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        
        byte[] chaveCifrada = null;
        byte[] textoCifrado = null;
        
        //-- A) Gerando uma chave sim�trica de 128 bits 
        KeyGenerator chaveGerada = KeyGenerator.getInstance("AES");
        chaveGerada.init(128);
        SecretKey chaveSecreta = chaveGerada.generateKey();
        byte[] chave = chaveSecreta.getEncoded();
        
        //-- B) Cifrando o texto com a chave sim�trica gerada   
        Cipher aesCifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivpSpec = new IvParameterSpec(new byte[16]);
        aesCifra.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(chave, "AES"), ivpSpec);
        textoCifrado = aesCifra.doFinal(textoClaro);
        
        //-- C) Cifrando a chave com a chave p�blica   
        Cipher rsaCifra = Cipher.getInstance("RSA");
        rsaCifra.init(Cipher.ENCRYPT_MODE, chavePublica);
        chaveCifrada = rsaCifra.doFinal(chave);
        
        //System.out.println(textoCifrado);
        //System.out.println(chaveCifrada);
        return new byte[][]{textoCifrado, chaveCifrada};
    }
    
    public static String ResumirTexto(String senha) {
        try {
            MessageDigest gerar = MessageDigest.getInstance("MD5");
            gerar.update(senha.getBytes());
            BASE64Encoder codificador = new BASE64Encoder();
            return codificador.encode(gerar.digest());
        } catch (NoSuchAlgorithmException ns) {
            ns.printStackTrace();
            return senha;
        }
    }
    
   // public static String Encripta (String senha) {
   //   return senha;
   // }
    
}

