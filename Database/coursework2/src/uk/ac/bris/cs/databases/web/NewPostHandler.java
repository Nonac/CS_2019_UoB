package uk.ac.bris.cs.databases.web;

import java.util.HashMap;
import java.util.Map;
import uk.ac.bris.cs.databases.api.Result;

/**
 *
 * @author csxdb
 */
public class NewPostHandler extends SimpleHandler {

    @Override
    RenderPair simpleRender(String p) throws RenderException {
        Map<String,Object> data = new HashMap<>();
        data.put("topic", p);
        return new RenderPair("NewPostView.ftl", Result.success(data));
    }    
}
