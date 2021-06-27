package org.semanticweb.owlapi6.apitest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLClass;
import org.semanticweb.owlapi6.model.OWLOntology;
import org.semanticweb.owlapi6.utility.SimpleRenderer;

class SimpleRendererTestCase extends TestBase {

    private static final String RESULT = "test:t";
    private static final String TEST = "test";
    private static final String NS = "urn:test#";
    private static final OWLClass T = df.getOWLClass(NS, "t");
    private final SimpleRenderer testSubject = new SimpleRenderer();

    @Test
    void shouldSetPrefixes() {
        testSubject.setPrefix(TEST, NS);
        assertEquals(RESULT, testSubject.render(T));
    }

    @Test
    void shouldCopyPrefixesFromFormat() {
        RDFXMLDocumentFormat f = new RDFXMLDocumentFormat();
        OWLOntology o = getOWLOntology();
        o.getOWLOntologyManager().setOntologyFormat(o, f);
        o.getPrefixManager().withPrefix(TEST, NS);
        testSubject.setPrefixesFromOntologyFormat(o, true);
        assertEquals(RESULT, testSubject.render(T));
    }
}
