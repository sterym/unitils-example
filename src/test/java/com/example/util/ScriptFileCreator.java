package com.example.util;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * The Class TestScriptFileCreator. (Helperclass)
 * Writes a script to a writer
 */
public class ScriptFileCreator {
    
    private final PrintWriter out;
    
    /**
     * Instantiates a new test script file creator.
     * 
     * @param out the out
     */
    public ScriptFileCreator(final Writer out) {
        super();
        this.out = new PrintWriter(new BufferedWriter(out));
        writeHeading();
    }
    
    /**
     * Adds the to script.
     * 
     * @param creator the creator
     */
    public void addToScript(final ScriptCreator creator) {
        for (String str : creator.createScript()) {
            addToScript(str);
        }
    }
    
    /**
     * Adds the to script.
     * 
     * @param scriptLine the script line
     */
    public void addToScript(final String scriptLine) {
        out.println(scriptLine);
    }

    /**
     * Write heading.
     */
    private void writeHeading() {
        out.println("CREATE SCHEMA PUBLIC AUTHORIZATION DBA");
    }
    
    /**
     * Write end.
     */
    private void writeEnd() {
        out.println("CREATE USER SA PASSWORD \"\"");
        out.println("GRANT DBA TO SA");
        out.println("SET WRITE_DELAY 10");
    }
    
    /**
     * Close.
     */
    public void close() {
        writeEnd();
        out.close();
    }

}
