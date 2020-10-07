package uk.ac.bris.cs.databases.web;

import uk.ac.bris.cs.databases.api.Result;

/**
 * Create a new person.
 * path: GET /newperson
 * 
 * @author csxdb
 */
public class NewPersonHandler extends SimpleHandler {

    @Override
    RenderPair simpleRender(String p) throws RenderException {
        
        return new RenderPair("NewPersonView.ftl", Result.success());
    }

    @Override boolean needsParameter() { return false; }
}
