package com.findwise.hydra.stage.xml;

import com.findwise.hydra.common.Logger;
import com.findwise.hydra.local.LocalDocument;
import com.findwise.hydra.stage.*;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * A stage that uses an XSLT style sheet for transforming XML documents.
 * 
 * @author thomas.gabrielsen
 */
@Stage(description = "This stage will transform an XML document by a given XSLT style sheet.")
public class XSLTStage extends AbstractProcessStage {
    @Parameter(name = "inputField", description = "the field to populate from")
    private String inputField;
    @Parameter(name = "outputField", description = "the field to output to")
    private String outputField;
    @Parameter(name = "xslt", description = "the XSLT style sheet")
    private String xslt;
    @Parameter(name = "omitXMLDeclaration", description = "determines whether to omit the XML declaration in the input XML")
    private String omitXMLDeclaration = "no";
    @Parameter(name = "appendRoot", description = "determines whether to append root tag to input XML")
    private boolean appendRoot = false;
    
    @Override
    public void init() throws RequiredArgumentMissingException {
    }

    @Override
    public void process(LocalDocument doc) throws ProcessException {
        Logger.debug("Starting processing");
        
        // Append root to XML document (for cases where this is lacking)
        String xml = (appendRoot ? "<root>" : "") + (String) doc.getContentField(inputField)
            + (appendRoot ? "</root>" : "");
        
        // Transform XML document
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer;
        try
        {
            transformer = factory.newTransformer(new StreamSource(new StringReader(xslt)));
            Source text = new StreamSource(new StringReader(xml));
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            Result res = new StreamResult(outStream);
            
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXMLDeclaration);
            transformer.transform(text, res);
            doc.putContentField(outputField, outStream.toString());
        }
        catch (Exception e) { System.out.println(e); }
    }
    
    public void setInputField(String inputField) {
        this.inputField = inputField;
    }

    public void setOutputField(String outputField) {
        this.outputField = outputField;
    }

    public void setXslt(String xslt) {
        this.xslt = xslt;
    }
    
    public void setOmitXMLDeclaration(String omitXMLDeclaration) {
        this.omitXMLDeclaration = omitXMLDeclaration;
    }
    
    public void setAppendRoot(boolean appendRoot) {
        this.appendRoot = appendRoot;
    }
}
