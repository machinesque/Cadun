/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import javax.swing.*;
import javax.swing.text.*;

/**
 *
 * @author luiz
 */
public class FixedLengthDocument extends PlainDocument {

    private int iMaxLength;

    public FixedLengthDocument(int maxlen) {
        super();
        iMaxLength = maxlen;
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {

        if (str == null) {
            return;
        }

        if (iMaxLength <= 1) { // aceitara qualquer no. de caracteres

            super.insertString(offset, str, attr);
            return;

        }

        int ilen = (getLength() + str.length());
        if (ilen <= iMaxLength) { // se o comprimento final for menor...

            super.insertString(offset, str, attr); // aceita str

        } else {

            if (getLength() == iMaxLength) {
                return;
            }
            String newStr = str.substring(0, (iMaxLength - getLength()));

            super.insertString(offset, str, attr);
        }

    }

}
