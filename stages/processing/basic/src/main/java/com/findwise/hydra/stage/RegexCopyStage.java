package com.findwise.hydra.stage;

import com.findwise.hydra.common.Logger;
import com.findwise.hydra.local.LocalDocument;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author thomas.gabrielsen
 */
@Stage(description = "This stage will copy the content from any field matching a given "
    + "regular expression into another field.")
public class RegexCopyStage extends AbstractProcessStage {
    @Parameter(name = "inputRegex", description = "the regular expression to match the fields to populate from")
    private String inputRegex;
    @Parameter(name = "outputField", description = "the field to output to")
    private String outputField;
    
    @Override
    public void init() throws RequiredArgumentMissingException {
    }

    @Override
    public void process(LocalDocument doc) throws ProcessException {
        Logger.debug("Starting processing");
        
        Pattern p = Pattern.compile(inputRegex);
        Set<String> fieldSet = doc.getContentFields();
        for (String field : fieldSet)
        {
            Matcher m = p.matcher(field);
            if (m.matches())
                doc.putContentField(outputField, (String) doc.getContentField(field));
        }
    }
    
    public void setInputRegex(String inputRegex) {
        this.inputRegex = inputRegex;
    }

    public void setOutputField(String outputField) {
        this.outputField = outputField;
    }
}
