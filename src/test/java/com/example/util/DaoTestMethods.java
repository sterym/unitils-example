package com.example.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

/**
 * Helpermethods for a DAO-TestClass. The methods in this class assist in creating the testdatabase-scriptfile
 *
 */
public final class DaoTestMethods {

    /**
     * Instantiates a new dao test methods.
     */
    private DaoTestMethods() {
        super();
    }

    /**
     * Creates the db test script.
     * 
     * @param persistenceTestXml the persistence test xml
     * @param testDbFile the test db file
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void createDBTestScript(final InputStream persistenceTestXml, final OutputStream testDbFile)
        throws IOException {
        createDBTestScript(persistenceTestXml, null, testDbFile);
    }

    /**
     * Creates the db test script.
     * 
     * @param persistenceTestXml the persistence test xml
     * @param statementsToInclude the statements to include
     * @param testDbFile the test db file
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void createDBTestScript(final InputStream persistenceTestXml,
        final InputStream statementsToInclude, final OutputStream testDbFile) throws IOException {
        ScriptCreator creator = new ScriptCreator();

        PersistenceConfigLoader loader = new PersistenceConfigLoader(creator);
        loader.loadFromStream(persistenceTestXml);
        persistenceTestXml.close();
        loader = null;

        ScriptFileCreator fileCreator = new ScriptFileCreator(new OutputStreamWriter(testDbFile));
        try {
            fileCreator.addToScript(creator);
            if (statementsToInclude != null) {
                String line = null;
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(statementsToInclude));
                    while ((line = reader.readLine()) != null) {
                        fileCreator.addToScript(line);
                    }
                } catch (IOException ex) {
                    throw new IOException("Error reading manual statements: " + ex.getMessage() 
                        + "\nLast valid read line: " + line);
                } finally {
                    IOUtils.closeQuietly(reader);
                }
            }
        } finally {
            fileCreator.close();
        }
    }

    /**
     * Load script file location from unitils props.
     * 
     * @param unitilsProperties the unitils properties
     * 
     * @return the file
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static File loadScriptFileLocationFromUnitilsProps(final InputStream unitilsProperties) throws IOException {
        Properties props = new Properties();
        props.load(unitilsProperties);
        unitilsProperties.close();
        String url = props.getProperty("database.url");
        int index = url.indexOf("file:");
        url = url.substring(index + 5);
        return new File(url + ".script");
    }

    /**
     * Load manual script file location from unitils props.
     * 
     * @param unitilsProperties the unitils properties
     * 
     * @return the file || null if not set
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static File loadManualScriptFileLocationFromUnitilsProps(final InputStream unitilsProperties)
        throws IOException {
        Properties props = new Properties();
        props.load(unitilsProperties);
        unitilsProperties.close();
        String url = props.getProperty("database.manualFile");
        return url == null ? null : new File(url + ".script");
    }

}
