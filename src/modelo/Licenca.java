/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author luizam
 */
public class Licenca {
    
    private int codigoLicenca;
    private String tipoLicenca;//120
    private String descricaoLicenca;//400

    public int getCodigoLicenca() {
        return codigoLicenca;
    }

    public void setCodigoLicenca(int codigoLicenca) {
        this.codigoLicenca = codigoLicenca;
    }

    public String getTipoLicenca() {
        return tipoLicenca;
    }

    public void setTipoLicenca(String tipoLicenca) {
        this.tipoLicenca = tipoLicenca;
    }

    public String getDescricaoLicenca() {
        return descricaoLicenca;
    }

    public void setDescricaoLicenca(String descricaoLicenca) {
        this.descricaoLicenca = descricaoLicenca;
    }
    
}
