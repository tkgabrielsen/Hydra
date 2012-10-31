package com.findwise.hydra.stage.xml;

import com.findwise.hydra.local.LocalDocument;
import com.findwise.hydra.stage.xml.XSLTStage;
import static org.junit.Assert.assertEquals;
import org.junit.*;

public class XSLTStageTest {
    
    public XSLTStageTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of process method, of class XSLTStage.
     */
    @Test
    public void testProcess() throws Exception {
        LocalDocument doc = new LocalDocument();
        doc.putContentField("in", "Testing <b>1-<div>2-3</div></b>!");
        XSLTStage instance = new XSLTStage();
        instance.setInputField("in");
        instance.setOutputField("out");
        instance.setOmitXMLDeclaration("yes");
        instance.setAppendRoot(true);
        instance.setXslt("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\">"
            + "<xsl:template match=\"//text()\">"
            + "<xsl:value-of select=\".\"/>"
            + "</xsl:template>"
            + "</xsl:stylesheet>");
        instance.process(doc);
        assertEquals("Testing 1-2-3!", doc.getContentField("out"));
    }
}
