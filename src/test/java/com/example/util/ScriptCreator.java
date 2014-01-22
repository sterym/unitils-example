package com.example.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Settings;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.HSQLDialect;

/**
 * The Class TestScriptCreator. This class is responsible for the creation of the create-statements
 * and will strip all the constraints ({@link DialectForTests} will be used to strip most of
 * the constraints but some can't be removed by manipulation of the dialect and those will
 * be removed here).
 */
public class ScriptCreator {

    private final AnnotationConfiguration cfg;

    /**
     * Instantiates a new test script creator.
     */
    public ScriptCreator() {
        super();
        cfg = new AnnotationConfiguration();
        setDialect(HSQLDialect.class);
    }

    /**
     * Sets the dialect.
     * 
     * @param dialect the new dialect
     */
    public final void setDialect(final Class<? extends Dialect> dialect) {
        cfg.setProperty(Environment.DIALECT, dialect.getName());
    }

    /**
     * Adds the class.
     * 
     * @param cl the cl
     */
    public void addClass(final Class<?> cl) {
        cfg.addAnnotatedClass(cl);
    }

    /**
     * Creates the script (and removes the constraints)
     * 
     * @return the list of create statements without constraints.
     */
    public List<String> createScript() {
        Settings set = cfg.buildSettings();

        Dialect dialect = new DialectForTests(set.getDialect());

        String[] res = cfg.generateSchemaCreationScript(dialect);
        List<String> result = new ArrayList<String>(res.length);

        for (String str : res) {
            String s = str.replaceAll(" not null", "").replaceAll(", primary key (.+)", ")");
            s = s.replace(" ,", ",");
            result.add(s);
        }

        return result;
    }

}
