package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.router.RouterNanoHTTPD;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author David
 */
public class StyleHandler extends AbstractHandler {

    @Override
    public View render(RouterNanoHTTPD.UriResource uriResource,
                       Map<String, String> params,
                       NanoHTTPD.IHTTPSession session) {
        
        String filename = uriResource.initParameter(String.class);
        
        try {
            File f = new File(filename);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            br.close();
            return new View(200, sb.toString());
        } catch (IOException e) {
            return new View(500, "Error reading file - " + e.getMessage());
        }
    }

    @Override
    public String getMimeType() {
        return "text/css";
    }
    
    
}
