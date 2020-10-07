package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.util.Map;
import uk.ac.bris.cs.databases.api.Result;

/**
 * Handler that allows the renderer access to the session.
 * It also provides the RenderPair abstraction, used primarily in the
 * SimpleHandler subclass.
 * @author csxdb
 */
public abstract class RPHandler extends AbstractHandler {
    
    public class RenderException extends Exception {
        int code;

        public RenderException(int code, String message) {
            super(message);
            this.code = code;
        }
        
    }
    
    public class RenderPair {
        final String template;
        final Result data;

        public RenderPair(String template, Result data) {
            this.template = template;
            this.data = data;
        }
    }
    
    abstract RenderPair doRender(String p,
        NanoHTTPD.IHTTPSession session) throws RenderException;

    // override if you don't need one.
    boolean needsParameter() { return true; }
    
    @Override
    public View render(RouterNanoHTTPD.UriResource uriResource,
                       Map<String,String> params,
                       NanoHTTPD.IHTTPSession session) {
        
        System.out.println("[RPHandler] render " + session.getUri());
        
        // Get the id or complain.
        
        String id = params.get("id");
        if (needsParameter()) {
            if (id == null || id.equals("")) {
                return new View(404, "Missing parameter.");
            }
        }
      
        try {
            RenderPair rp = doRender(id, session);

            NanoHTTPD.CookieHandler h = session.getCookies();
            String user = h.read("user");
            
            if (rp.data.isSuccess()) {
                System.out.println("[SimpleHandler] rendering " + rp.template);
                return renderView(rp.template, rp.data.getValue(), user);
            } else if (rp.data.isFatal()) {
                return new View(500, "Fatal error - " + rp.data.getMessage());
            } else {
                return new View(400, "Error - " + rp.data.getMessage());
            }
            
        } catch (RenderException e) {
            return new View(e.code, e.getMessage());
        }
            
    }
}
