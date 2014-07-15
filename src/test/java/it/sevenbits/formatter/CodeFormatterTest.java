package it.sevenbits.formatter;

import it.sevenbits.exceptions.FormatterException;
import it.sevenbits.stringstream.StringInStream;
import it.sevenbits.stringstream.StringOutStream;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test for format method CodeFormatter.
 */

public class CodeFormatterTest {

    /**
     * Logger for tester
     */
    private Logger logger = Logger.getLogger(CodeFormatterTest.class);

    @Test(expected = FormatterException.class)
    public final void testFormatNullInput() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream();
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions();

        formatter.format(inStream, outStream, formatOptions);
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("\ntestFormatNullInput : " +
                "We try to read from null string and catch exception - cool.\n");
        }
    }

    @Test(expected = FormatterException.class)
    public final void testFormatTooManyBrackets() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("{{{{}");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions();

        formatter.format(inStream, outStream, formatOptions);
        if (logger.isEnabledFor(Level.FATAL)) {
            logger.fatal("\ntestFormatNullInput : " +
                "We can't read from null string, but we did it.\n");
        }
    }

    @Test
    public final void testFormatEmptyString() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions();
        String expectedString = "";

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(outStream.getString(), expectedString);
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("\ntestFormatEmptyString :" +
                " Correct output with empty string.\n");
        }
    }

    @Test
    public final void testFormatCorrectInput() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("{      a                   b     c  }");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions();
        String expectedString = "{\n    abc\n}";

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(expectedString, outStream.getString());
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("\ntestFormatCorrectInput :" +
                " Correct output with this current string.\n");
        }
    }

    @Test
    public final void testFormatPointComma() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("a;");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions();
        String expectedString = "a;\n";

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(expectedString, outStream.getString());
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("\ntestFormatPointComma : " +
                "Correct output with current input string.\n");
        }
    }

//    @Test
//    public final void testFormatSignsOperations() throws Exception {
//        CodeFormatter formatter = new CodeFormatter();
//        StringInStream inStream = new StringInStream("a++;a* b*c/x");
//        StringOutStream outStream = new StringOutStream("");
//        FormatOptions formatOptions = new FormatOptions();
//        String expectedString = "a;\n";
//
//        formatter.format(inStream, outStream, formatOptions);
//        Assert.assertEquals(expectedString, outStream.getString());
//        if (logger.isEnabledFor(Level.DEBUG)) {
//            logger.debug("\ntestFormatSignsOperations : " +
//                    "Correct output with current input string.\n");
//        }
//    }

    @Test
    public final void testFormatSomeCode() throws Exception {
        CodeFormatter formatter = new CodeFormatter();
        StringInStream inStream = new StringInStream("{do(){while()}          }\n\n\n\n");
        StringOutStream outStream = new StringOutStream("");
        FormatOptions formatOptions = new FormatOptions();
        String expectedString = "{\n" +
            "    do() {\n" +
            "        while()\n" +
            "    }\n" +
            "}";

        formatter.format(inStream, outStream, formatOptions);
        Assert.assertEquals(expectedString, outStream.getString());
        if (logger.isEnabledFor(Level.DEBUG)) {
            logger.debug("\ntestFormatSomeCode : " +
                "Correct output with current input string.\n");
        }
    }
}