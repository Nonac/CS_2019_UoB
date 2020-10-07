package uk.ac.bris.cs.databases.api;

import java.util.List;

/**
 * Detail view of a single forum.
 * @author csxdb
 */
public class ForumView {
    
    /* The id of this forum. */
    private final int id;
    
    /* The title of this forum. */
    private final String title;
    
    /* The topics in this forum, ordered by title. */
    private final List<SimpleTopicSummaryView> topics;

    public ForumView(int id,
                     String title,
                     List<SimpleTopicSummaryView> topics) {
        this.id = id;
        this.title = title;
        this.topics = topics;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the topics
     */
    public List<SimpleTopicSummaryView> getTopics() {
        return topics;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    
}
