/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author luizam
 */
public class VerificadorModalidades {

    private String tipoCadastro;
    private int idadeAno;
    private int idadeMes;
    private int idadeDia;
    private int anoAtual = getDateAnoHoje();
    private int mesAtual = getDateMesHoje() + 1;
    private int diaAtual = getDateDiaHoje();
    
    public String verificaIdadeModalidade(Date dataNascimento) {

        String modalidade = "";
        
        int anoNasc = dataNascimento.getYear() + 1900;
        int mesNasc = dataNascimento.getMonth() + 1;
        int diaNasc = dataNascimento.getDate();
        
        /*if (diaAtual < diaNasc) {
            mesAtual -= 1;
            diaAtual += 30;
            idadeDia = diaAtual - diaNasc;
        } else {
            idadeDia = diaAtual - diaNasc;
        }
        
        if (mesAtual < mesNasc) {
            anoAtual -= 1;
            mesAtual += 12;
            idadeMes = mesAtual - mesNasc;
        } else {
            idadeMes = mesAtual - mesNasc;
        }*/
        
        idadeAno = anoAtual - anoNasc;
        
        if (idadeAno == 1) {
            modalidade = "BERÇÁRIO";
        } else if (idadeAno == 2) {
            modalidade = "MATERNAL I";
        } else if (idadeAno == 3) {
            modalidade = "MATERNAL II";
        }  else if (idadeAno == 4) {
            modalidade = "PRÉ I";
        } else if (idadeAno == 5) {
            modalidade = "PRÉ II";
        } else if (idadeAno >= 6) {
            
            tipoCadastro = "EGRESSO";
            modalidade = "ANO";
            
        }

        /*if (idadeAno < 1) {
            modalidade = "BERÇÁRIO";
        } else if (idadeAno < 2) {
            modalidade = "MATERNAL I";
        } else if (idadeAno < 3) {
            modalidade = "MATERNAL II";
        } else if (idadeAno <= 4 && mesNasc <= 3 && diaNasc <= 31) {
            modalidade = "PRÉ I";
        } else if (idadeAno <= 5 && mesNasc <= 12 && diaNasc <= 31) {
            modalidade = "PRÉ II";
        } else if (idadeAno <= 6 && mesNasc <= 3 && diaNasc <= 31) {
            modalidade = "PRÉ II";
        } else if (idadeAno >= 6) {
            
            tipoCadastro = "EGRESSO";
            modalidade = "1º ANO";
            
        }*/

        return modalidade;

    }

    public Date getDateHoje() {

        GregorianCalendar calendar = new GregorianCalendar();
        Date data = calendar.getTime();

        return data;

    }
    
    public int getDateAnoHoje() {
        
        GregorianCalendar calendar = new GregorianCalendar();
        int dataAno = calendar.get(Calendar.YEAR);

        System.out.println("" + dataAno);
        return dataAno;
        
    }
    
    public int getDateMesHoje() {
        
        GregorianCalendar calendar = new GregorianCalendar();
        int dataAno = calendar.get(Calendar.MONTH);

        return dataAno;
        
    }
    
    public int getDateDiaHoje() {
        
        GregorianCalendar calendar = new GregorianCalendar();
        int dataAno = calendar.get(Calendar.DAY_OF_MONTH);

        return dataAno;
        
    }
    
    
}
