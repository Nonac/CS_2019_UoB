package uk.ac.bris.cs.databases.api;

import uk.ac.bris.cs.databases.util.Params;

/**
 * Simplified summary view of a topic. Just displays the title and ids.
 * @author csxdb
 */
public class SimpleTopicSummaryView {
    
    /* The id of this topic. */
    private final int topicId;
    
    /* The id of the forum that contains this topic. */
    private final int forumId;
    
    /* The title of this topic. */
    private final String title;
    
    public SimpleTopicSummaryView(int topicId, int forumId, String title) {
        
        Params.cannotBeEmpty(title);
        
        this.topicId = topicId;
        this.forumId = forumId;
        this.title = title;
    }

    /**
     * @return the topicId
     */
    public int getTopicId() {
        return topicId;
    }

    /**
     * @return the forumId
     */
    public int getForumId() {
        return forumId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

}
