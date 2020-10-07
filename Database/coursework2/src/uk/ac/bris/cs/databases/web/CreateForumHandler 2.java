package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import java.util.Map;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.Result;

/**
 *  Create a new forum.
 * path: POST /createforum [title]
 * 
 * @author csxdb
 */
public class CreateForumHandler extends AbstractPostHandler {

    @Override
    public RenderPair handlePost(Map<String, String> params,
        NanoHTTPD.IHTTPSession session) {
        
        String title = params.get("title");
        if (title == null) {
            return new RenderPair(null, Result.failure("Missing 'title'"));
        }
        
        APIProvider api = ApplicationContext.getInstance().getApi();
        
        Result r = api.createForum(title);
        
        if (r.isSuccess()) {
            return new RenderPair("Success.ftl",
                Result.success(new ValueHolder("Created new forum.")));
        } else {
            return new RenderPair(null, r);
        }
    }
}
