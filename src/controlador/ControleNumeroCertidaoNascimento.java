/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author luizam
 */
public class ControleNumeroCertidaoNascimento {
    
    private String cartorio;
    private String anoRegistro;
    private String livro;
    private String folha;
    private String termo;
    private String digitoVerificador;
    
    public void setNumeroCertidaoNascimento(String numeroCertidaoNascimento) {
        
        anoRegistro = numeroCertidaoNascimento.substring(13, 17);
        livro = numeroCertidaoNascimento.substring(20, 25);
        folha = numeroCertidaoNascimento.substring(26, 29);
        termo = numeroCertidaoNascimento.substring(30, 37);
        digitoVerificador = numeroCertidaoNascimento.substring(38, 40);
        
    }

    public String getCartorio() {
        return cartorio;
    }

    public void setCartorio(String cartorio) {
        this.cartorio = cartorio;
    }

    public String getAnoRegistro() {
        return anoRegistro;
    }

    public void setAnoRegistro(String anoRegistro) {
        this.anoRegistro = anoRegistro;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getFolha() {
        return folha;
    }

    public void setFolha(String folha) {
        this.folha = folha;
    }

    public String getTermo() {
        return termo;
    }

    public void setTermo(String termo) {
        this.termo = termo;
    }

    public String getDigitoVerificador() {
        return digitoVerificador;
    }

    public void setDigitoVerificador(String digitoVerificador) {
        this.digitoVerificador = digitoVerificador;
    }
    
}
