package uk.ac.bris.cs.databases.web;

import fi.iki.elonen.NanoHTTPD;

/**
 * Handler for the simple case that we're dealing with a GET request, have a
 * single URI parameter of interest and want to render a view with some data
 * of complain if we get junk back.
 * @author csxdb
 */
public abstract class SimpleHandler extends RPHandler {

    abstract RenderPair simpleRender(String p) throws RenderException;

    @Override public RenderPair doRender(String p,
        NanoHTTPD.IHTTPSession session) throws RenderException {
        return simpleRender(p);
    }
}
