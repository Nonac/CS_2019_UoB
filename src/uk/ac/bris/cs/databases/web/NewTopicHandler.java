package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import java.util.HashMap;
import java.util.Map;
import uk.ac.bris.cs.databases.api.Result;

/**
 *
 * @author csxdb
 */
public class NewTopicHandler extends RPHandler {

    @Override
    RenderPair doRender(String p,
                        NanoHTTPD.IHTTPSession session)
                        throws RenderException {
        
        NanoHTTPD.CookieHandler h = session.getCookies();
        String username = h.read("user");
        if (username == null || username.equals("")) {
            return new RenderPair(null, Result.failure(
                "You must log in to create new topics. " +
                "Select 'list of users' in the top bar."));
        }
        
        Map<String,Object> data = new HashMap<>();
        data.put("forum", p);
        
        return new RenderPair("NewTopic.ftl", Result.success(data));
    }
}
