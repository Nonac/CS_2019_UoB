package uk.ac.bris.cs.databases.api;

import java.util.List;
import java.util.Map;

/**
 * This is the main interface that you have to implement.
 * 
 * Methods have a difficulty from one to three stars that indicates
 * very roughly how challenging they are to implement.
 * 
 * @author csxdb
 */
public interface APIProvider {
    
    /* 
     * 1 "Person" methods
     */
    
    /**
     * Get a list of all users in the system as a map username -> name.
     * @return A map with one entry per user of the form username -> name
     * (note that usernames are unique).
     * 
     * Difficulty: *
     * Used by: /people (PeopleHandler)
     *
     * The implementation for this is provided.
     *
     */
    public Result<Map<String, String>> getUsers();
    
    /**
     * Get a PersonView for the person with the given username.
     * @param username - the username to search for, cannot be empty.
     * @return If a person with the given username exists, a fully populated
     * PersonView. Otherwise, failure (or fatal on a database error).
     * 
     * Difficulty: *
     * Used by: /person/:id (PersonHandler)
     */
    public Result<PersonView> getPersonView(String username);
       
    /**
     * Create a new person.
     * @param name - the person's name, cannot be empty.
     * @param username - the person's username, cannot be empty.
     * @param studentId - the person's student id. May be either NULL if the
     * person is not a student or a non-empty string if they are; can not be
     * an empty string.
     * @return Success if no person with this username existed yet and a new
     * one was created, failure if a person with this username already exists,
     * fatal if something else went wrong.
     * 
     * Difficulty: **
     * Used by: /newperson => /createperson (CreatePersonHandler)
     *
     * The implementation for this is provided.
     *
     */
    public Result addNewPerson(String name, String username, String studentId);
    
    /*
     * 2 Forums only (no topics needed yet).
     * Create some sample data in your database manually to test getForums.
     * Then you can implement createForum and check that the two work together.
     */
    
    /**
     * Get the "main page" containing a list of forums ordered alphabetically
     * by title. 
     * @return the list of all forums; an empty list if there are no forums.
     * 
     * Difficulty: *
     * Used by: /forums (ForumsHandler)
     */
     public Result<List<ForumSummaryView>> getForums();

    /**
     * Create a new forum.
     * @param title - the title of the forum. Must not be null or empty and
     * no forum with this name must exist yet.
     * @return success if the forum was created, failure if the title was
     * null, empty or such a forum already existed; fatal on other errors.
     * 
     * Difficulty: **
     * Used by: /newforum => /createforum (CreateForumHandler)
     */
    public Result createForum(String title);
    
    /*
     * 3 Forums, topics, posts.
     */
    
    /**
     * Get the detailed view of a single forum.
     * @param id - the id of the forum to get.
     * @return A view of this forum if it exists, otherwise failure.
     * 
     * Difficulty: **
     * Used by: /forum/:id (ForumHandler)
     */
    public Result<ForumView> getForum(int id);
    
    /**
     * Get a view of a topic.
     * @param topicId - the topic to get.
     * @return The topic view if one exists with the given id,
     * otherwise failure or fatal on database errors. 
     * 
     * Difficulty: **
     * Used by: /topic/:id (TopicHandler)
     */
    public Result<TopicView> getTopic(int topicId);

    /**
     * Create a post in an existing topic.
     * @param topicId - the id of the topic to post in. Must refer to
     * an existing topic.
     * @param username - the name under which to post; user must exist.
     * @param text - the content of the post, cannot be empty.
     * @return success if the post was made, failure if any of the preconditions
     * were not met and fatal if something else went wrong.
     * 
     * Difficulty: ** to *** depending on schema
     * Used by: /newpost/:id => /createpost (CreatePostHandler)
     */
    public Result createPost(int topicId, String username, String text);
    
    /**
     * Create a new topic in a forum.
     * @param forumId - the id of the forum in which to create the topic. This
     * forum must exist.
     * @param username - the username under which to make this post. Must refer
     * to an existing username.
     * @param title - the title of this topic. Cannot be empty.
     * @param text - the text of the initial post. Cannot be empty.
     * @return failure if any of the preconditions are not met (forum does not
     * exist, user does not exist, title or text empty);
     * success if the post was created and fatal if something else went wrong.
     * 
     * Difficulty: ***
     * Used by: /newtopic/:id => /createtopic (CreateTopicHandler)
     */
    public Result createTopic(int forumId, String username, String title, String text);
     
    /**
     * Count the number of posts in a topic (without fetching them all).
     * @param topicId - the topic to look at.
     * @return The number of posts in this topic if it exists, otherwise a
     * failure.
     * 
     * Difficulty: *
     * Not used in web interface.
     */
    public Result<Integer> countPostsInTopic(int topicId);
    
}
