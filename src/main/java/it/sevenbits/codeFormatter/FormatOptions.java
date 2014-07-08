package it.sevenbits.codeFormatter;

/**
 * Class have information about format options
 * <p/>
 * if false - '\t' if true - ' '
 */
public class FormatOptions {
    private int indentSize;
    private char symbolEndOfString;
    private char tabSymbol;
    private static final int STANDART_INDENT_SIZE = 4;

    public FormatOptions() {
        setDefaultParam();
    }

    public FormatOptions(final int indentSize, final boolean useSpaces) {
        this.indentSize = indentSize;
        this.symbolEndOfString = '\n';
        if (useSpaces) {
            this.tabSymbol = ' ';
        } else {
            this.tabSymbol = '\t';
        }
    }

    private void setDefaultParam() {
        indentSize = STANDART_INDENT_SIZE;
        symbolEndOfString = '\n';
        tabSymbol = ' ';
    }

    public int getIndent() {
        return indentSize;
    }

    public char getTabSymbol() {
        return tabSymbol;
    }

    public char getSymbolEndOfString() {
        return symbolEndOfString;
    }

    public void useTabs() {
        tabSymbol = '\t';
    }

    public void useSpaces() {
        tabSymbol = ' ';
    }

    public void setIndentSize(final int count) {
        indentSize = count;
    }
}
