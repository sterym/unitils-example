package com.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * The Class PersistenceConfigLoader. Loads the persistence-test.xml
 * and locates the classes mapped in it. Only the classes found in this file
 * will be used to create the database-test-script.
 */
public class PersistenceConfigLoader {

    private static final Logger LOG = Logger.getLogger(PersistenceConfigLoader.class);

    private final ScriptCreator creator;

    /**
     * Instantiates a new persistence config loader.
     * 
     * @param creator the creator
     */
    public PersistenceConfigLoader(final ScriptCreator creator) {
        this.creator = creator;
    }

    /**
     * Load config from stream.
     * 
     * @param in the inputstream to load from
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void loadFromStream(final InputStream in) throws IOException {
        loadFromReader(new InputStreamReader(in));
    }

    /**
     * Load config from reader.
     * 
     * @param reader the input stream reader
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @SuppressWarnings("unchecked")
    public void loadFromReader(final Reader reader) throws IOException {
        Document doc;
        SAXBuilder sb = new SAXBuilder();
        try {
            doc = sb.build(reader);
        } catch (JDOMException e) {
            throw new IOException("Error reading xml-file: " + e.getMessage());
        }
        Element root = doc.getRootElement();
        List<Element> units = root.getChildren("persistence-unit", root.getNamespace());
        for (Element unit : units) {
            LOG.info("Load unit " + unit);
            loadUnit(unit);
        }
    }

    /**
     * Load a unit.
     * 
     * @param unit the unit to load
     * 
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @SuppressWarnings("unchecked")
    private void loadUnit(final Element unit) throws IOException {
        List<Element> classes = unit.getChildren("class", unit.getNamespace());
        for (Element e : classes) {
            String classToLoad = e.getTextTrim();
            LOG.info("  Load class: " + classToLoad);
            Class<?> cl;
            try {
                cl = Class.forName(classToLoad);
            } catch (ClassNotFoundException e1) {
                throw new IOException("Could not load class: " + classToLoad);
            }
            creator.addClass(cl);
        }
    }

}
