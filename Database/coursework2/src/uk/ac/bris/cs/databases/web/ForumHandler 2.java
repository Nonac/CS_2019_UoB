package uk.ac.bris.cs.databases.web;

import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.ForumView;
import uk.ac.bris.cs.databases.api.Result;

/**
 * Handler for the advanced view of a single forum.
 * path: /forum2/$id
 * 
 * @author csxdb
 */
public class ForumHandler extends SimpleHandler {

    @Override
    RenderPair simpleRender(String p) throws RenderException {
        int id = Integer.parseInt(p);
        APIProvider api = ApplicationContext.getInstance().getApi();
        Result<ForumView> r = api.getForum(id);
        return new RenderPair("ForumView.ftl", r);
    }
    
}
