package uk.ac.bris.cs.databases.api;

import uk.ac.bris.cs.databases.util.Params;

/**
 * Simplified summary of a single forum that does not display topics.
 * @author csxdb
 */
public class ForumSummaryView {
    
    /* The title of this forum. */
    private final String title;
    
    /* The id of this forum. */
    private final int id;
    
    public ForumSummaryView(int id, String title) {
        Params.cannotBeEmpty(title);
        
        this.id = id;
        this.title = title;
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }  
}
