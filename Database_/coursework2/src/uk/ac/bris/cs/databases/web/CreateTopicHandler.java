package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import java.util.Map;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.Result;

/**
 *
 * @author csxdb
 */
public class CreateTopicHandler extends AbstractPostHandler {
    
    @Override
    public RenderPair handlePost(Map<String,String> params,
        NanoHTTPD.IHTTPSession session) {
        
        NanoHTTPD.CookieHandler h = session.getCookies();
        String name = h.read("user");
        
        if (name == null || name.equals("")) {
            return new RenderPair(null, Result.failure("Missing 'name'"));
        }
        
        int forumId = Integer.parseInt(params.get("forum"));
        if (forumId == 0) {
            return new RenderPair(null, Result.failure("Got zero forum id."));
        }
        
        String title = params.get("title");
        if (title == null || title.equals("")) {
            return new RenderPair(null, Result.failure("Missing 'title'"));
        }
        
        String text = params.get("text");
        if (text == null || text.equals("")) {
            return new RenderPair(null, Result.failure("Missing 'text'"));
        }
        
        APIProvider api = ApplicationContext.getInstance().getApi();
        Result r = api.createTopic(forumId, name, title, text);
        
        if (!r.isSuccess()) {
            return new RenderPair(null, Result.failure(
                "Failed to create topic - " + r.getMessage()));
        }

        return new RenderPair("Success.ftl",
            Result.success(new ValueHolder("Created a new topic.")));
    }
}
