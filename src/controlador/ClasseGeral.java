package controlador;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class ClasseGeral {

    private java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    Dimension scrSize = toolkit.getScreenSize();
 
        DecimalFormat df = new DecimalFormat("#,##0.00");

    public java.sql.Date DataToSql(Date dataSolicitacao) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
//*********************************************************************************************************************************
    public void msgAtencao(String msg) {

        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.WARNING_MESSAGE);

    }
    
//*********************************************************************************************************************************
    public void msgAtencao(int msg) {

        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.WARNING_MESSAGE);

    }

//*********************************************************************************************************************************
    public void msgAtencao(Double msg) {

        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.WARNING_MESSAGE);

    }
//*********************************************************************************************************************************
    
    public boolean msgConfirma(String msg) {
        boolean bRet = false;
        Object[] options = {"Sim", "Não"};
        int i = JOptionPane.showOptionDialog(null,
                msg, "Exclusão",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (i == 0) {
            bRet = true;
        }
        return bRet;
    }  

//*********************************************************************************************************************************
    
    public boolean msgConfirmaAtualizacao(String msg) {
        boolean bRet = false;
        Object[] options = {"Sim", "Não"};
        int i = JOptionPane.showOptionDialog(null,
                msg, "Atualização",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        if (i == 0) {
            bRet = true;
        }
        return bRet;
    }

//*********************************************************************************************************************************
    
    public void msgErro(String msg) {

        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.ERROR_MESSAGE);

    }
    
//*********************************************************************************************************************************

    public void msgErro(int msg) {

        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.ERROR_MESSAGE);

    }

//*********************************************************************************************************************************

    public void msgErro(Double msg) {

        JOptionPane.showMessageDialog(null, msg, "Atenção", JOptionPane.ERROR_MESSAGE);

    }

//*********************************************************************************************************************************
    
    public void msgInforma(String msg) {

        JOptionPane.showMessageDialog(null, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);

    }
    
//*********************************************************************************************************************************

    public void msgInforma(int msg) {

        JOptionPane.showMessageDialog(null, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);

    }

//*********************************************************************************************************************************

    public void msgInforma(Double msg) {

        JOptionPane.showMessageDialog(null, msg, "Informação", JOptionPane.INFORMATION_MESSAGE);

    }

//*********************************************************************************************************************************
    
    public void centralizaForm(JInternalFrame jif) {
        
        int h = (int) scrSize.getWidth() / 2;
        int w = ((int) scrSize.getHeight() / 2) - 50;
        jif.setLocation(h - jif.getWidth() / 2, w - jif.getHeight() / 2);
    }
    
//*********************************************************************************************************************************
    
    public String lpad(String valueToPad, String filler, int size) {
        
        while (valueToPad.length() < size) {
            valueToPad = filler + valueToPad;
        }
        return valueToPad;
    }
    
//*********************************************************************************************************************************
    
    public String rpad(String valueToPad, String filler, int size) {
        
        while (valueToPad.length() < size) {
            valueToPad = valueToPad + filler;
        }
        return valueToPad;
    }

//*********************************************************************************************************************************
    public String Crypt(String texto) {
        
        String sRet = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger hash = new BigInteger(1, md.digest(texto.getBytes()));
            sRet = hash.toString(16);
            if (sRet.length() % 2 != 0) {
                sRet = "0" + sRet;
            }
        } catch (NoSuchAlgorithmException nsae) {
        }
        return sRet;
    }
//*********************************************************************************************************************************  
    public String getExtension(File f) {
        
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
//********************************************************************************************************************************* 
    
    public int getWidth(){
        return (int) scrSize.getWidth();
    }

//*********************************************************************************************************************************

    public String getXmlByFile(String arq) {

        StringBuffer sb = new StringBuffer();
        int count = 0;
        try {

            File arquivo;
            arquivo = new File(arq);
            BufferedReader brOrigem = new BufferedReader(new FileReader(arquivo));
            String linha = null;
            String campo = null;
            StringTokenizer tk = null;

            while ((linha = brOrigem.readLine()) != null) {

                tk = new StringTokenizer(linha, "\n");
                while (tk.hasMoreTokens()) {
                    campo = tk.nextToken();
                    sb.append(campo);
                }
            }

        } catch (Exception e) {
            System.out.println(" Erro " + e.getMessage());
        }
        return sb.toString();
    }


//*********************************************************************************************************************************

    public int IntegerToSql(String s){

        int iRet = 0;

        if(s.equals("")|| s == null){
            return iRet;
        }else{
            iRet = Integer.valueOf(s);
        }

        return iRet;
    }

//******************************************************************************************************

   public Double DoubleToSql(String s){

        Double dRet = 0.0;

        if(s.equals("")|| s == null){
            return dRet;
        }else{
            dRet = Double.valueOf(s);
        }

        return dRet;
   }

//******************************************************************************************************

    public Date DataToSql(String sData) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date sDataRet = null;
        try {

            if (!(sData == null)) {

                if (!sData.trim().equals("")) {

                    sDataRet = format.parse(sData);
                }
            }

        } catch (Exception e) {

            System.err.println("Erro DataToSql " + e);

        }

        return sDataRet;
    }

//******************************************************************************************************

    public String DateToSql(String sData) {

        String sDataRet = null;
        try {

            if (!(sData == null)) {

                if (!sData.replaceAll("/", "").trim().equals("")) {
                     sDataRet = "'" + sData + "'";
                }
            }

        } catch (Exception e) {

            System.err.println("Erro DateToSql " + e);

        }

        return sDataRet;
    }

//******************************************************************************************************

    public Date StrToDate(String sData) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date sDataRet = null;
        try {

            if (sData.trim().equals("")) {

                sDataRet = null;

            } else {

                sDataRet = format.parse(sData);

            }

        } catch (Exception e) {

            System.err.println("Erro StrToDate " + e);

        }

        return sDataRet;
    }

//******************************************************************************************************

    public Date StrToDate(String sData, String formato) {

        SimpleDateFormat format = new SimpleDateFormat(formato);
        Date sDataRet = null;
        try {

            if (sData.trim().equals("")) {

                sDataRet = null;

            } else {

                sDataRet = format.parse(sData);

            }

        } catch (Exception e) {

            System.err.println("Erro StrToDate " + e);

        }

        return sDataRet;
    }

//******************************************************************************************************

    public String StrToSql2(String sCampo) {

        String sRet = (sCampo.trim()).replaceAll("\'", "\'\'");
        sRet = sRet.replace("\\", "");
        return sRet;
    }

//******************************************************************************************************

    public String StrToSql(String sCampo) {

        String sRet = null;
        String aux = null;

        if (!(sCampo == null)) {

            aux = (sCampo.trim()).replaceAll("\'", "\'\'");
            aux = aux.replace("\\", "");
            sRet = "'" + aux + "'";
        }
        return sRet;
    }

//******************************************************************************************************

    public String VirgulaPorPonto(String sCampo) {

        String sRet = null;
        if (sCampo.trim().equals("")) {
            sRet = "0";
        } else {
            sRet = (sCampo.trim()).replaceAll(",", ".");
        }

        return sRet;

    }

//******************************************************************************************************

    public String PontoPorVirgula(String sCampo) {

        String sRet = (sCampo.trim()).replace(".", ",");

        return sRet;

    }

//******************************************************************************************************

    public Double validaCampo(String campo, int casas) {

        String tem = campo.replaceAll("[^0-9]", "");

        Double dRet = Double.parseDouble(tem) / casas;

        return dRet;

    }

//******************************************************************************************************

    public String somenteNumero(String s) {

        return s.replaceAll("[^0-9]", "");

    }

//******************************************************************************************************

    public String removeAcentos(String s) {

        String sRet = null;
        CharSequence cs = new StringBuilder(s);
        sRet = Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        String[] caracteresEspeciais = {"\\.", ",", "-", ":", "\\(", "\\)", "ª", "\\|", "\\\\", "°"};

        for (int i = 0; i < caracteresEspeciais.length; i++) {
            sRet = sRet.replaceAll(caracteresEspeciais[i], "");
        }
        sRet = sRet.replaceAll("&", "E");

        return sRet;
    }

//******************************************************************************************************

    public String DateToString(Date d) {

        String sRet = null;
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            if (d == null) {
                sRet = "";
            } else {

                sRet = (String) format.format(d);
            }

        } catch (Exception e) {
            System.err.println("Erro DateToString " + e.getMessage());
        }

        return sRet;
    }

//******************************************************************************************************

    public String DoubleToDecimal(Double d) {

        String sRet = "";
        try {

            sRet = df.format(d);

        } catch (Exception e) {
            System.err.println("Erro DoubleToDecimal " + e.getMessage());
        }

        return sRet;
    }

//******************************************************************************************************

    public Double DecimalToDouble(String s) { //verificar monitorar

        Double dRet = 0.0;

        try {
            if (s.equals("") || s == null) {
                return dRet;
            } else {
                dRet = df.parse(s).doubleValue();
            }

        } catch (ParseException pse) {
            System.err.println("Erro DecimalToDouble " + pse.getMessage());
        }

        return dRet;

    }

//********************************************************************************************************************************************************

    public Double Trunca(Double d){

        return DecimalToDouble(DoubleToDecimal(d));

    }

//********************************************************************************************************************************************************

    public String BooleanSQL(boolean b) {

        String sRet = null;
        if (b == false) {
            sRet = "N";
        } else {
            sRet = "S";
        }
        return sRet;
    }

//********************************************************************************************************************************************************

   public boolean  SQLBoolean(String s){

       boolean bRet = false;
       if(s.toLowerCase().equals("s")){
           bRet = true;
       }else{
           bRet = false;
       }
       return bRet;
   }

//********************************************************************************************************************************************************

    public int StringToInt(String s) {

        int valor = 9999999; //7 números

        try {
            if (s.isEmpty()) {
                valor = 0;
            } else {
                valor = Integer.valueOf(s);
            }
        } catch (Exception e) {
            System.err.println("Erro StringToInt " + e.getMessage());
        }

        return valor;

    }

//********************************************************************************************************************************************************

    public long StringToLong(String s) {

        long valor = 9999999; //7 números

        try {
            if (s.isEmpty()) {
                valor = 0;
            } else {
                valor = Long.valueOf(s);
            }
        } catch (Exception e) {
            System.err.println("Erro StringToInt " + e.getMessage());
        }

        return valor;

    }

}
