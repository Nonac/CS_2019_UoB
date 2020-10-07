package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.Result;

/**
 *
 * @author csxdb
 */
public class PeopleHandler extends AbstractHandler {
    
    public class KV {
        private final String k;
        private final String v;

        public KV(String k, String v) {
            this.k = k;
            this.v = v;
        }

        /**
         * @return the k
         */
        public String getKey() {
            return k;
        }

        /**
         * @return the v
         */
        public String getValue() {
            return v;
        }
    }
    
    @Override
    public View render(RouterNanoHTTPD.UriResource uriResource,
                       Map<String,String> params,
                       NanoHTTPD.IHTTPSession session) {
         APIProvider api = ApplicationContext.getInstance().getApi();
         Result<Map<String, String>> r = api.getUsers();
         
         if (r.isSuccess()) {
             List<KV> people = new ArrayList<>(r.getValue().size());
             for (Map.Entry<String, String> entry : r.getValue().entrySet()) {
                 people.add(new KV(entry.getKey(), entry.getValue()));
             }
             
            NanoHTTPD.CookieHandler h = session.getCookies();
            String user = h.read("user");
             
             return renderView("PeopleView.ftl", wrap(people), user);
         } else {
             return new View(500, "Database error - " + r.getMessage());
         }
         
    }
    
}
