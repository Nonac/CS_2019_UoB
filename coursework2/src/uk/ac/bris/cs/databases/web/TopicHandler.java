package uk.ac.bris.cs.databases.web;

import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.TopicView;

/**
 *
 * @author csxdb
 */
public class TopicHandler extends SimpleHandler {

    @Override
    public RenderPair simpleRender(String p) {
        
        int id = Integer.parseInt(p);
        APIProvider api = ApplicationContext.getInstance().getApi();
        Result<TopicView> r = api.getTopic(id);
        return new RenderPair("TopicView.ftl", r);
    }
}
