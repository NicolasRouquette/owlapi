package org.semanticweb.owlapi6.apitest.syntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.semanticweb.owlapi6.apitest.TestFiles;
import org.semanticweb.owlapi6.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi6.documents.StringDocumentSource;
import org.semanticweb.owlapi6.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi6.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.RDFXMLDocumentFormat;
import org.semanticweb.owlapi6.formats.TurtleDocumentFormat;
import org.semanticweb.owlapi6.model.OWLDocumentFormat;
import org.semanticweb.owlapi6.model.OWLOntology;

class OutputSyntaxSortTestCase extends TestBase {

    static Collection<Object[]> getData() {
        return Arrays.<Object[]>asList(new Object[] {new ManchesterSyntaxDocumentFormat()},
            new Object[] {new FunctionalSyntaxDocumentFormat()},
            new Object[] {new TurtleDocumentFormat()}, new Object[] {new RDFXMLDocumentFormat()},
            new Object[] {new OWLXMLDocumentFormat()});
    }

    @ParameterizedTest
    @MethodSource("getData")
    void shouldOutputAllInSameOrder(OWLDocumentFormat format) {
        masterConfigurator.withRemapAllAnonymousIndividualsIds(false);
        try {
            List<OWLOntology> ontologies = new ArrayList<>();
            List<String> set = new ArrayList<>();
            for (String s : TestFiles.inputSorting) {
                OWLOntology o = loadOntologyFromString(new StringDocumentSource(s,
                    "uri:owlapi:ontology", new FunctionalSyntaxDocumentFormat(), null));
                set.add(saveOntology(o, format).toString());
                ontologies.add(o);
            }
            for (int i = 0; i < ontologies.size() - 1; i++) {
                equal(ontologies.get(i), ontologies.get(i + 1));
            }
            for (int i = 0; i < set.size() - 1; i++) {
                assertEquals(set.get(i), set.get(i + 1));
            }
        } finally {
            masterConfigurator.withRemapAllAnonymousIndividualsIds(true);
        }
    }
}
