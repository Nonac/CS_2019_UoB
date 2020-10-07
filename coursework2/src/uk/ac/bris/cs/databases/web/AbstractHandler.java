package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import uk.ac.bris.cs.databases.api.Result;

/**
 * Code shared across web handlers.
 * @author csxdb
 */
public abstract class AbstractHandler extends RouterNanoHTTPD.DefaultHandler {

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

    public abstract View render(RouterNanoHTTPD.UriResource uriResource,
                                Map<String,String> params,
                                NanoHTTPD.IHTTPSession session);
    
    @Override public String getMimeType() {
        return "text/html";
    }

    @Override public NanoHTTPD.Response.IStatus getStatus() {
        throw new RuntimeException("Should not happen.");
        // return NanoHTTPD.Response.Status.OK;
    }

    @Override
    public String getText() {
        throw new RuntimeException("Should not happen - using get/post");
    }

    
    
    class Status implements NanoHTTPD.Response.IStatus {

        private final int code;

        public Status(int code) {
            this.code = code;
        }
        
        @Override public String getDescription() {
            switch (code) {
                case 200: return "OK";
                case 404: return "Not found";
                case 500: return "Internal error";
                default:  return "OTHER"; // naughty
            }
        }

        @Override public int getRequestStatus() {
            return code;
        }
        
    }
    
    /** Implement this to work with cookies. */
    void handleCookies(NanoHTTPD.IHTTPSession session) {}
    
    private NanoHTTPD.Response handle(RouterNanoHTTPD.UriResource uriResource,
                                      Map<String, String> urlParams,
                                      NanoHTTPD.IHTTPSession session) {
        View v = render(uriResource, urlParams, session);
        handleCookies(session);
        NanoHTTPD.Response r = NanoHTTPD.newFixedLengthResponse(
                               new Status(v.getCode()),
                               getMimeType(),
                               v.getContents());
        return r;
    }
    
    @Override
    public NanoHTTPD.Response get(RouterNanoHTTPD.UriResource uriResource,
                                  Map<String, String> urlParams,
                                  NanoHTTPD.IHTTPSession session) {
        
        return handle(uriResource, urlParams, session);
    }
    
    @Override
    public NanoHTTPD.Response post(RouterNanoHTTPD.UriResource uriResource,
                                   Map<String, String> urlParams,
                                   NanoHTTPD.IHTTPSession session) {
        
        return handle(uriResource, urlParams, session);
    }
            
    Map<String, String> parseQuery(String URIParams) {
        if (URIParams == null) {
            return new HashMap<>();
        }
        
        // stackoverflow 11640025
        Map<String, String> result = new HashMap<>();
        for (String param : URIParams.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }
    
    View renderView(String template, Object data, Object state) {
        Map <String, Object> viewdata = new HashMap<>();
        viewdata.put("data", data);
        viewdata.put("session", state);
        
        Configuration c = ApplicationContext.getInstance().getTemplateConfiguration();
        
        Template t;
        try {
            t = c.getTemplate(template);
        } catch (Exception e) {
            return new View(500, "Template error - " + e.getMessage());
        }
        
        StringWriter w = new StringWriter();
        try {
            t.process(viewdata, w);
        } catch (TemplateException | IOException e) {
            return new View(500, "Rendering error - " + e.getMessage());
        }
        
        return new View(200, w.toString());
    }
    
    public static class ListWrapper<T> {
        private final List<T> l;

        public static <U> Result<ListWrapper<U>> wrap(Result<List<U>> r) {
            if (r.isSuccess()) {
                return Result.success(new ListWrapper<U>(r.getValue()));
            } else {
                // throw new RuntimeException("Trying to list-wrap an error.");
                
                /* 
                 * The wonderful thing about monads is
                 * that monads are a wonderful thing.
                 */
                if (r.isFatal()) {
                    return Result.fatal(r.getMessage());
                } else {
                    return Result.failure(r.getMessage());
                }
            }
        }
        
        public ListWrapper(List<T> l) {
            this.l = l;
        }
        
        public List<T> getData() { return l; }
    }
    
    ListWrapper wrap(List l) {
        return new ListWrapper(l);
    }

}
