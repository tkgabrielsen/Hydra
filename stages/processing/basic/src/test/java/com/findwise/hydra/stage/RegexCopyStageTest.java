package com.findwise.hydra.stage;

import com.findwise.hydra.local.LocalDocument;
import static org.junit.Assert.*;
import org.junit.*;

public class RegexCopyStageTest {
    
    public RegexCopyStageTest() {
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
     * Testing a copy with unknown prefix.
     */
    @Test
    public void testCopyWithUnknownPrefix() throws Exception {
        LocalDocument doc = new LocalDocument();
        doc.putContentField("test_content", "Testing 1-2-3!");
        RegexCopyStage instance = new RegexCopyStage();
        instance.setInputRegex(".*content");
        instance.setOutputField("out");
        instance.process(doc);
        assertEquals("Testing 1-2-3!", doc.getContentField("out"));
    }
    
    /**
     * Testing a copy with unknown postfix.
     */
    @Test
    public void testCopyWithUnknownPostfix() throws Exception {
        LocalDocument doc = new LocalDocument();
        doc.putContentField("test_content", "Testing 1-2-3!");
        RegexCopyStage instance = new RegexCopyStage();
        instance.setInputRegex("test.*");
        instance.setOutputField("out");
        instance.process(doc);
        assertEquals("Testing 1-2-3!", doc.getContentField("out"));
    }
    
    /**
     * Testing a copy with unknown prefix and no matching postfix.
     */
    @Test
    public void testCopyWithUnknownPrefixNoMatch() throws Exception {
        LocalDocument doc = new LocalDocument();
        doc.putContentField("test_content_test", "Testing 1-2-3!");
        RegexCopyStage instance = new RegexCopyStage();
        instance.setInputRegex(".*content");
        instance.setOutputField("out");
        instance.process(doc);
        assertNull(doc.getContentField("out"));
    }
    
    /**
     * Testing a copy with unknown postfix and no matching prefix.
     */
    @Test
    public void testCopyWithUnknownPostfixNoMatch() throws Exception {
        LocalDocument doc = new LocalDocument();
        doc.putContentField("test_content_test", "Testing 1-2-3!");
        RegexCopyStage instance = new RegexCopyStage();
        instance.setInputRegex("content.*");
        instance.setOutputField("out");
        instance.process(doc);
        assertNull(doc.getContentField("out"));
    }
}
