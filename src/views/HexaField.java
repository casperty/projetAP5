package views;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

public class HexaField extends JTextField {

    public HexaField() {
        super();
    }

    public HexaField( int cols ) {
        super( cols );
    }

    @Override
    protected Document createDefaultModel() {
        return new UpperCaseDocument();
    }

    static class UpperCaseDocument extends PlainDocument {

        @Override
        public void insertString( int offs, String str, AttributeSet a )
                throws BadLocationException {

            if ( str == null ) {
                return;
            }

            char[] chars = str.toCharArray();
            boolean ok = true;
            boolean ok2=true;

            for ( int i = 0; i < chars.length; i++ ) {
                try {
                    Integer.parseInt( String.valueOf( chars[i] ) );
                } catch ( NumberFormatException exc ) {
                    ok = false;
                    break;
                }
            	if(!Character.isLetter(chars[i])){
            		ok2=false;
            	}
            }

            if ( ok || ok2)
                super.insertString( offs, new String( chars ), a );

        }
    }

}
