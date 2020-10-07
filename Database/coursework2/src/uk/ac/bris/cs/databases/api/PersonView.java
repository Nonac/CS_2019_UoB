package uk.ac.bris.cs.databases.api;

import uk.ac.bris.cs.databases.util.Params;

/**
 * @author csxdb
 */
public class PersonView {
    
    /* The name of the person. */
    private final String name;
    
    /* The username of the person. */
    private final String username;
    
    /* The studentId of the person or the empty string if the person does not
     * have a student Id.
    */
    private final String studentId;
    
    public PersonView(String name, String username, String studentId) {
        
        Params.cannotBeEmpty(name);
        Params.cannotBeEmpty(username);
        Params.cannotBeNull(studentId);
        
        this.name = name;
        this.username = username;
        this.studentId = studentId;
    }
        
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the studentId
     */
    public String getStudentId() {
        return studentId;
    }
}
