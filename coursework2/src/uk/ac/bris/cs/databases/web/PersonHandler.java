package uk.ac.bris.cs.databases.web;

import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.PersonView;
import uk.ac.bris.cs.databases.api.Result;

/**
 * PATH /person/:id
 * @author csxdb
 */
public class PersonHandler extends SimpleHandler {

    @Override
    RenderPair simpleRender(String p) throws RenderException {
        APIProvider api = ApplicationContext.getInstance().getApi();
        Result<PersonView> r = api.getPersonView(p);
        return new RenderPair("PersonView.ftl", r);
    }
} 
