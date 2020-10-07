package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Method;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.io.IOException;
import java.util.Map;
import uk.ac.bris.cs.databases.api.Result;

/**
 * Base class for POST (new topic / forum / post) handlers.
 * 
 * @author David
 */
public abstract class AbstractPostHandler extends AbstractHandler {
    
    public class ValueHolder {
        private final String value;

        public ValueHolder(String value) {
            this.value = value;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
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
    
    public abstract RenderPair handlePost(Map<String,String> params,
        NanoHTTPD.IHTTPSession session);
    
    @Override
    public View render(RouterNanoHTTPD.UriResource uriResource,
                       Map<String,String> params,
                       NanoHTTPD.IHTTPSession session) {
        
        Method method = session.getMethod();
        
        if (!method.equals(Method.POST)) {
            return new View(400, "Error - expected POST request, got " + method);
        }
        
        Map<String,String> m = session.getParms();
        try {
            session.parseBody(m);
        } catch (NanoHTTPD.ResponseException | IOException e) {
            return new View(500, "Exception handling POST - " + e.getMessage());
        }
   
        System.out.println("[AbstractPostHandler] render " + uriResource.getUri());
        for (Map.Entry<String,String> e : m.entrySet()) {
            System.out.println("param " + e.getKey() + " => " + e.getValue());
        }
        RenderPair rp = handlePost(m, session);
        
        NanoHTTPD.CookieHandler h = session.getCookies();
        String user = h.read("user");
        
        if (rp.data.isSuccess()) {
            return renderView(rp.template, rp.data.getValue(), user);
        } else if (rp.data.isFatal()) {
            return new View(500, "Fatal error - " + rp.data.getMessage());
        } else {
            return new View(400, "Error - " + rp.data.getMessage());
        }
    }

}
