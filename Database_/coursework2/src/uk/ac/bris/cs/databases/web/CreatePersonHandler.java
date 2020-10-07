package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import java.util.Map;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.Result;

/**
 *  Create a new person.
 * path: POST /createperson [name,username,stuid]
 * 
 * @author csxdb
 */
public class CreatePersonHandler extends AbstractPostHandler {

    @Override
    public RenderPair handlePost(Map<String, String> params,
        NanoHTTPD.IHTTPSession session) {
        
        String name = params.get("name");
        if (name == null || name.equals("")) {
            return new RenderPair(null, Result.failure("Missing or empty name."));
        }
        String username = params.get("username");
        if (username == null || username.equals("")) {
            return new RenderPair(null, Result.failure("Missing or empty username."));
        }
        String sid = params.get("stuid");
        
        APIProvider api = ApplicationContext.getInstance().getApi();
        
        Result r = api.addNewPerson(name, username, sid.equals("") ? null : sid);
        
        if (r.isSuccess()) {
            return new RenderPair("Success.ftl",
                Result.success(new ValueHolder("Created new person.")));
        } else {
            return new RenderPair(null, r);
        }
    }
}
