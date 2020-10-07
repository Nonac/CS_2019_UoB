package uk.ac.bris.cs.databases.api;

import uk.ac.bris.cs.databases.util.Params;

/**
 * View of a single post.
 * @author csxdb
 */
public class SimplePostView {
    
    /* The number of the post in the topic - the first post of each topic is
     * always number 1.
     */
    private final int postNumber;
    
    /* The name of the post author. */
    private final String author;

    /* The contents of this post. */
    private final String text;
    
    /* The date/time this post was made. */
    private final String postedAt;
    
    public SimplePostView(int postNumber, String author,
                          String text, String postedAt) {
        
        Params.cannotBeEmpty(author);
        Params.cannotBeEmpty(text);
        
        this.author = author;
        this.postNumber = postNumber;
        this.text = text;
        this.postedAt = postedAt;
    }

    /**
     * @return the postNumber
     */
    public int getPostNumber() {
        return postNumber;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the postedAt
     */
    public String getPostedAt() {
        return postedAt;
    }

    public String getAuthor() {
        return author;
    }
}
