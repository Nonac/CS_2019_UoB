package uk.ac.bris.cs.databases.web;

/**
 *
 * @author csxdb
 */
public class View {
    private int code;
    private String contents;

    public View() {
    }

    public View(int code, String contents) {
        this.code = code;
        this.contents = contents;
    }
    
    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the contents
     */
    public String getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
