package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.unitils.UnitilsJUnit4;

/**
 * The Class GeneralDaoTester.
 * All DAO-tests need to extend from this class.
 * By extending they will activate the 'initBeforeClass'-method which creates the db-test-script-file. 
 *
 */
public abstract class GeneralDaoTest extends UnitilsJUnit4 {

    private static File scriptFile;

    /**
     * Inits the scriptFile. This method is the 'magic' for creating the create-statements before the DAO-tests start.
     * 
     * 1) This method will load the properties of unitils to locate the scriptFile's location which can be found in
     * the property-file with as property-key: database.url.
     * 
     * 2) Next it loads the file 'manualTestDbfile.script' which is located in the same folder as the scriptfile. The
     * contents of this file 'll be included in the output directly after the create-statements. This can be used to
     * add some create-statements manually for native queries for example. (Used by the custom lists)
     * 
     * 3) Start the creation of the create-statements based on the classes in 'persistence-test.xml' and add
     * 'manualTestDbfile.script' to the scriptFile. (This does the actual write)
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @BeforeClass
    public static void initBeforeClass() throws IOException {
        scriptFile =
            DaoTestMethods.loadScriptFileLocationFromUnitilsProps(getUnitilsPropertiesStream());
        File manualFile = DaoTestMethods.loadManualScriptFileLocationFromUnitilsProps(getUnitilsPropertiesStream());
        FileOutputStream scriptOut = null;
        InputStream manual = null;
        try {
            scriptOut = new FileOutputStream(scriptFile);
            if (manualFile == null) {
                DaoTestMethods.createDBTestScript(GeneralDaoTest.class
                    .getResourceAsStream("/META-INF/persistence-test.xml"), scriptOut);            
            } else {
                manual = new FileInputStream(manualFile);
                DaoTestMethods.createDBTestScript(GeneralDaoTest.class
                    .getResourceAsStream("/META-INF/persistence-test.xml"), manual, scriptOut);            
            }
        } finally {
            IOUtils.closeQuietly(scriptOut);
            IOUtils.closeQuietly(manual);
        }        
    }

    /**
     * Load the inputstream to the unitils-properties file
     * 
     * @return the inputstream to the unitils.properties-file
     */
    private static InputStream getUnitilsPropertiesStream() {
        return GeneralDaoTest.class.getResourceAsStream("/unitils.properties");
    }

    /**
     * Inits
     */
    @Before
    public abstract void init();

    /**
     * Cleanup. This method deletes the scriptFile and places a placeholder.
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @AfterClass
    public static void cleanup() throws IOException {
        scriptFile.delete();
        // create placeholder as marker
        scriptFile.createNewFile();
    }

}
