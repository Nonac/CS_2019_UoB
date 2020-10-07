package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.util.Map;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.Result;

/**
 *
 * @author David
 */
public class LoginHandler extends AbstractHandler {

    String username = "";

    @Override
    public View render(RouterNanoHTTPD.UriResource uriResource,
                       Map<String,String> params,
                       NanoHTTPD.IHTTPSession session) {
        
        System.out.println("[LoginHandler] render " + session.getUri());
        
        String id = params.get("id");
        String template;
        Result data;
        
        if (id == null || id.equals("")) {
            username = "";
            template = "Success.ftl";
            data = Result.success(new ValueHolder("Logged out."));
        } else {
            APIProvider api = ApplicationContext.getInstance().getApi();
            Result<Map<String, String>> r = api.getUsers();
            if (!r.isSuccess()) {
                template = null;
                data = Result.fatal("API call failed.");
            } else {
                Map<String, String> users = r.getValue();
                String name = users.get(id);
                if (name == null) {
                    template = null;
                    data = Result.failure("No such user");
                } else {
                    username = id;
                    template = "Success.ftl";
                    data = Result.success(new ValueHolder("Logged in as " + name));
                }
            }
        }

        NanoHTTPD.CookieHandler h = session.getCookies();
        if (username.equals("")) {
            h.delete("user");
        } else {
            h.set("user", username + ";Path=/", 1);
        }

        if (data.isSuccess()) {
            System.out.println("[SimpleHandler] rendering " + template);
            return renderView(template, data.getValue(),
                username.equals("") ? null : username);
        } else if (data.isFatal()) {
            return new View(500, "Fatal error - " + data.getMessage());
        } else {
            return new View(400, "Error - " + data.getMessage());
        }
    }   
}
