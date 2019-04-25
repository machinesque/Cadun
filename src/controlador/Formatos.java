/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.text.ParseException;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author luizam
 */
public class Formatos {

    public DefaultFormatterFactory getFormatoCpf() throws ParseException {

        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("###.###.###-##");
        } catch (ParseException pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco);
        return factory;

    }

    public DefaultFormatterFactory getFormatoTelefone() throws ParseException {

        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("(##)####-####"); // passa telefone para o formato [(45)9999-9999]
        } catch (ParseException pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco);
        return factory;

    }

    public DefaultFormatterFactory getFormatoRg() throws ParseException {

        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("##.###.###-#"); // passa telefone para o formato [(45)9999-9999]
        } catch (ParseException pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco);
        return factory;

    }

    public DefaultFormatterFactory getFormatoNumeroIdentificadorCopel() throws ParseException {

        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("##.###.###-#"); // passa telefone para o formato [(45)9999-9999]
        } catch (ParseException pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco);
        return factory;

    }

    public DefaultFormatterFactory getFormatoCep() throws ParseException {

        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("##.###-###"); // passa telefone para o formato [(45)9999-9999]
        } catch (ParseException pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco);
        return factory;

    }

    /*
     * Os 6 primeiros dígitos ###### é o Código Nacional da Serventia;
     * Os próximos 2 dígitos ## correspondem ao Código do Acervo;
     * Os outros 2 ## é o número relativo ao Serviço de Registro Civil;
     * Os outros 4 #### é o Ano do Registro;
     * O próximo número sozinho # é o Tipo do Livro de Registro sendo:
     * -1: Livro A (Nascimento)
     * -2: Livro B (Casamento)
     * -3: Livro B Auxiliar (Casamento Religioso com efeito civil)
     * -4: Livro C (Óbito)
     * -5: Livro C Auxiliar (Natimorto)
     * -6: Livro D (Registro de Proclamas)
     * -7: Livro E (Demais atos relativos ao registro civil ou livro E único);
     * -8: Livro E (Desdobrado para registro especifico das Emancipações);
     * -9: Livro E (Desdobrado para registro especifico das Interdições);
     * Os próximos 5 dígitos ##### corresponde ao Número do Livro;
     * Os outros 3 dígitos ### é a Folha do Registro;
     * Os outros 7 ####### é o Número do Termo;
     * E os 2 últimos ## é o Número do Dígito Verificador;
     */
    public DefaultFormatterFactory getFormatoCertidaoNascimento() throws ParseException {

        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("###### ## ## #### # ##### ### ####### ##");
        } catch (ParseException pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco);
        return factory;

    }

    public DefaultFormatterFactory getFormatoHora() throws ParseException {

        MaskFormatter comFoco = null;

        try {
            comFoco = new MaskFormatter("##:##"); // passa telefone para o formato [(45)9999-9999]
        } catch (ParseException pe) {
        }

        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco);
        return factory;

    }
}
