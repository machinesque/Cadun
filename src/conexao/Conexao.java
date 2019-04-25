/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexao;

import excecoes.ExcCadastro;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luiz
 */
public class Conexao {

    static ConexaoPool conexaoPool = new ConexaoPool();
    private static Connection con;
    private static Connection conBkp;

    /** Creates a new instance of Conexao */
    public Conexao() {
    }

    public static Connection retornaConexao() {

        String driver = "org.postgresql.Driver";
        //String url = "jdbc:postgresql://127.0.0.1:5432/semedcadun";
        String url = "jdbc:postgresql://semed-infosrv01:5432/semedcadun";
        //String url = "jdbc:postgresql://ntm.cascavel.pr.gov.br:5434/semedcadun";
        //String url = "jdbc:postgresql://200.138.60.30:5432/semedcadun";
        //String url = "jdbc:postgresql://10.1.1.210:5432/semedcadun";
        String login = "postgres";
        //String senha = "Semed.10";
        String senha = "B5ld86bCsdvu718";

        try {
            if (con == null || con.isClosed()) {

                Class.forName(driver);
                con = DriverManager.getConnection(url, login, senha);
            }

        } catch (SQLException ex) {
            //ex.printStackTrace();
//            throw new ExcCadastro("Erro abrindo conexão: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
//            throw new ExcCadastro("Driver não encontrado: " + ex.getMessage());
        }
        return con;
    }
    
    public static Connection getConexao() throws ExcCadastro {

        //Banco PostgreSql
        String driver = "org.postgresql.Driver";
        //String url = "jdbc:postgresql://127.0.0.1:5432/semedcadun";
        String url = "jdbc:postgresql://semed-infosrv01:5432/semedcadun";
        //String url = "jdbc:postgresql://ntm.cascavel.pr.gov.br:5434/semedcadun";
        //String url = "jdbc:postgresql://200.138.60.30:5432/semedcadun";
        //String url = "jdbc:postgresql://10.1.1.210:5432/semedcadun";
        String login = "postgres";
        //String senha = "Semed.10";
        String senha = "B5ld86bCsdvu718";

        try {
            if (con == null || con.isClosed()) {

                Class.forName(driver);
                con = DriverManager.getConnection(url, login, senha);
            }

        } catch (SQLException ex) {
            //ex.printStackTrace();
            throw new ExcCadastro("Erro abrindo conexão: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
            throw new ExcCadastro("Driver não encontrado: " + ex.getMessage());
        }
        return con;
    }

    public static Connection getConexaoBackup() throws ExcCadastro {

        //Banco PostgreSql
        String driver = "org.postgresql.Driver";
        //String url = "jdbc:postgresql://127.0.0.1:5432/semedcadunbackup";
        String url = "jdbc:postgresql://semed-infosrv01:5432/semedcadunbackup";
        //String url = "jdbc:postgresql://ntm.cascavel.pr.gov.br:5434/semedcadunbackup";
        //String url = "jdbc:postgresql://200.138.60.30:5432/semedcadunbackup";
        //String url = "jdbc:postgresql://10.1.1.210:5432/semedcadunbackup";
        String login = "postgres";
        //String senha = "Semed.10";
        String senha = "B5ld86bCsdvu718";

        try {
            if (conBkp == null || conBkp.isClosed()) {

                Class.forName(driver);
                conBkp = DriverManager.getConnection(url, login, senha);
            }

        } catch (SQLException ex) {
            //ex.printStackTrace();
            throw new ExcCadastro("Erro abrindo conexão: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            //ex.printStackTrace();
            throw new ExcCadastro("Driver não encontrado: " + ex.getMessage());
        }
        return conBkp;
    }

}
