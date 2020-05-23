package uk.ac.bris.cs.databases.cwk2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import uk.ac.bris.cs.databases.api.APIProvider;
import uk.ac.bris.cs.databases.api.ForumSummaryView;
import uk.ac.bris.cs.databases.api.ForumView;
import uk.ac.bris.cs.databases.api.Result;
import uk.ac.bris.cs.databases.api.PersonView;
import uk.ac.bris.cs.databases.api.SimplePostView;
import uk.ac.bris.cs.databases.api.SimpleTopicSummaryView;
import uk.ac.bris.cs.databases.api.TopicView;
import uk.ac.bris.cs.databases.web.AbstractHandler;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;



/**
 *
 * @author csxdb
 */
public class API implements APIProvider {

    private final Connection c;

    public API(Connection c) {
        this.c = c;
    }

    /* predefined methods */

    @Override
    public Result<Map<String, String>> getUsers() {
        try (Statement s = c.createStatement()) {
            ResultSet r = s.executeQuery("SELECT name, username FROM Person");

            Map<String, String> data = new HashMap<>();
            while (r.next()) {
                data.put(r.getString("username"), r.getString("name"));
            }
            return Result.success(data);
        } catch (SQLException ex) {
            return Result.fatal("database error - " + ex.getMessage());
        }
    }

    @Override
    public Result addNewPerson(String name, String username, String studentId) {
        if (studentId != null && studentId.equals("")) {
            return Result.failure("StudentId can be null, but cannot be the empty string.");
        }
        if (name == null || name.equals("")) {
            return Result.failure("Name cannot be empty.");
        }
        if (username == null || username.equals("")) {
            return Result.failure("Username cannot be empty.");
        }

        try (PreparedStatement p = c.prepareStatement(
                "SELECT count(1) AS c FROM Person WHERE username = ?"
        )) {
            p.setString(1, username);
            ResultSet r = p.executeQuery();

            if (r.next() && r.getInt("c") > 0) {
                return Result.failure("A user called " + username + " already exists.");
            }
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }

        try (PreparedStatement p = c.prepareStatement(
                "INSERT INTO Person (name, username, stuId) VALUES (?, ?, ?)"
        )) {
            p.setString(1, name);
            p.setString(2, username);
            p.setString(3, studentId);
            p.executeUpdate();

            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException f) {
                return Result.fatal("SQL error on rollback - [" + f +
                        "] from handling exception " + e);
            }
            return Result.fatal(e.getMessage());
        }

        return Result.success();
    }

    /* level 1 */

    @Override
    public Result<PersonView> getPersonView(String username) {
        try (Statement s = c.createStatement()) {
            ResultSet r = s.executeQuery("SELECT name, username, stuId FROM Person WHERE username='"+username+'\'');
            PersonView personView=null;
            while (r.next()) {
                if(r.getString("username").equals(username)){

                    personView=new PersonView(r.getString("name"),r.getString("username"),
                            r.getString("stuId"));
                }
            }
            if(personView!=null){
                return Result.success(personView);
            }else {
                return Result.failure("A user called " + username + " does not exist.");
            }

        } catch (SQLException ex) {
            return Result.fatal("database error - " + ex.getMessage());
        }
    }

    @Override
    public Result<List<ForumSummaryView>> getForums() {
        try (Statement s = c.createStatement()) {
            ResultSet r = s.executeQuery("SELECT id, title FROM Forum order by title");
            List<ForumSummaryView> list = new ArrayList<>();
            while (r.next()) {
                ForumSummaryView forumSummaryView=new ForumSummaryView(r.getInt("id"),
                        r.getString("title"));
                list.add(forumSummaryView);
            }
            return Result.success(list);
        } catch (SQLException ex) {
            return Result.fatal("database error - " + ex.getMessage());
        }
    }

    @Override
    public Result<Integer> countPostsInTopic(int topicId) {
        try (Statement s = c.createStatement()){
            if(!isTopicExist(topicId)){
                return Result.failure("The topicID does not exist.");
            }
            ResultSet r = s.executeQuery(
                    "SELECT count(1) as c FROM Post WHERE T.id="+topicId);
            if(r.next()){
                return Result.success(r.getInt("c"));
            }
        } catch (SQLException ex) {
        return Result.fatal("database error - " + ex.getMessage());
    }
        return Result.failure("Unexpect database error.");
    }

    @Override
    public Result<TopicView> getTopic(int topicId) {
        try (Statement s = c.createStatement()) {
            if(!isTopicExist(topicId)){
                return Result.failure("The topicID does not exist.");
            }
            ResultSet r = s.executeQuery(
                    "SELECT T.id as id,T.title as title,P2.username as username," +
                            "P.text as text,P.time as time From Topic T\n" +
                    "INNER JOIN Post P on T.id = P.topicId\n" +
                    "INNER JOIN Person P2 on P.personId = P2.id\n" +
                    "WHERE T.id="+topicId+" order by P.time");
            String title=null;
            List<SimplePostView> list = new ArrayList<>();
            int cnt=1;
            while (r.next()) {
                if(title==null){
                    title=r.getString("title");
                }
                SimplePostView simplePostView=new SimplePostView(
                        cnt,r.getString("username"),r.getString("text"),r.getString("time"));
                list.add(simplePostView);
                cnt++;
            }
            return Result.success(new TopicView(topicId,title,list));
        } catch (SQLException ex) {
            return Result.fatal("database error - " + ex.getMessage());
        }
    }

    /* level 2 */

    @Override
    public Result createForum(String title) {
        if (title == null || title.equals("")) {
            return Result.failure("Title cannot be empty.");
        }
        try (PreparedStatement p = c.prepareStatement(
                "SELECT count(1) AS c FROM Forum WHERE title = ?"
        )) {
            p.setString(1, title);
            ResultSet r = p.executeQuery();

            if (r.next() && r.getInt("c") > 0) {
                return Result.failure("A forum called " + title + " already exists.");
            }
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }

        try (PreparedStatement p = c.prepareStatement(
                "INSERT INTO Forum (title) VALUES ( ? )"
        )) {
            p.setString(1, title);
            p.executeUpdate();

            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException f) {
                return Result.fatal("SQL error on rollback - [" + f +
                        "] from handling exception " + e);
            }
            return Result.fatal(e.getMessage());
        }
        return Result.success();
    }

    @Override
    public Result<ForumView> getForum(int id) {
        try (Statement s = c.createStatement()) {
            if(!isForumExist(id)){
                return Result.failure("The forumID does not exist.");
            }
            ResultSet r = s.executeQuery(
                    "SELECT F.id as id,F.title as title, T.id as topicId,T.title as topicTitle From Forum F\n" +
                            "INNER JOIN Topic T on F.id = T.forumId\n" +
                            "WHERE F.id="+id+" order by T.title");
            String title=null;
            List<SimpleTopicSummaryView> list = new ArrayList<>();
            if(r.getRow()==0){
                title=getForumTitleForForumID(id);
            }
            while (r.next()) {
                if(title==null){
                    title=r.getString("title");
                }
                SimpleTopicSummaryView simpleTopicSummaryView=new SimpleTopicSummaryView(
                        r.getInt("topicId"),id,r.getString("topicTitle")
                );
                list.add(simpleTopicSummaryView);
            }
            ForumView forumView=new ForumView(id,title,list);
            return Result.success(forumView);
        } catch (SQLException ex) {
            return Result.fatal("database error - " + ex.getMessage());
        }
    }

    @Override
    public Result createPost(int topicId, String username, String text) {
        if(topicId<=0) {
            return Result.failure("TopicId is invalid.");
        }
        if (username == null || username.equals("")) {
            return Result.failure("Username cannot be empty.");
        }
        if (text == null || text.equals("")) {
            return Result.failure("Text cannot be empty.");
        }
        int personId;
        try {
            if(!isTopicExist(topicId)) {
                return Result.failure("The topicID does not exist.");
            }
            if(!isUserExist(username)){
                return Result.failure("The username does not exist.");
            }
            personId=getUserIdFormUsername(username);
        }catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }

        try (PreparedStatement p = c.prepareStatement(
                "INSERT INTO Post (topicId,personId,text,time) VALUES ( ?,?,?,? )"
        )) {
            p.setInt(1, topicId);
            p.setInt(2, personId);
            p.setString(3, text);
            p.setString(4, currentTime());
            p.executeUpdate();

            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException f) {
                return Result.fatal("SQL error on rollback - [" + f +
                        "] from handling exception " + e);
            }
            return Result.fatal(e.getMessage());
        }
        return Result.success();
    }


    /* level 3 */

    @Override
    public Result createTopic(int forumId, String username, String title, String text) {
        if (username == null || username.equals("")) {
            return Result.failure("Username cannot be empty.");
        }
        if (title == null || title.equals("")) {
            return Result.failure("Title cannot be empty.");
        }
        if (text == null || text.equals("")) {
            return Result.failure("Text cannot be empty.");
        }

        int personId;
        int topicId;
        try{
            if(!isForumExist(forumId)) {
                return Result.failure("The forumID does not exist.");
            }
            if(!isUserExist(username)){
                return Result.failure("The username does not exist.");
            }
            topicId=countTopic()+1;
            personId=getUserIdFormUsername(username);
        } catch (SQLException e) {
            return Result.fatal(e.getMessage());
        }

        try {
            Statement p = c.createStatement();
            p.addBatch("INSERT INTO Topic (forumId,personId,title) VALUES ( "
                    +forumId+","+personId+",\'"+title+"\' )");
            p.executeBatch();
            p.addBatch("INSERT INTO Post (topicId,personId,text,time) VALUES (" +
                    +topicId+","+personId+",\'"+text+"',\'"+currentTime()+"\' )");
            p.executeBatch();

            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException f) {
                return Result.fatal("SQL error on rollback - [" + f +
                        "] from handling exception " + e);
            }
            return Result.fatal(e.getMessage());
        }
        return Result.success();
    }

    public int getUserIdFormUsername(String username) throws SQLException{
        PreparedStatement p = c.prepareStatement(
                "SELECT count(1) AS c,id FROM Person WHERE username = ?"
        );
        p.setString(1, username);
        ResultSet r = p.executeQuery();

        if (r.next() && r.getInt("c") == 0) {
            return 0;
        }
        return r.getInt("id");
    }

    public boolean isTopicExist(int topicId) throws SQLException{
        PreparedStatement p = c.prepareStatement(
                "SELECT count(1) AS c FROM Topic WHERE id = ?"
        );
        p.setInt(1, topicId);
        ResultSet r = p.executeQuery();
        return !r.next() || r.getInt("c") != 0;
    }

    public boolean isForumExist(int forumId) throws SQLException{
        PreparedStatement p = c.prepareStatement(
                "SELECT count(1) AS c FROM Forum WHERE id = ?"
        );
        p.setInt(1, forumId);
        ResultSet r = p.executeQuery();
        return !r.next() || r.getInt("c") != 0;
    }

    public int countTopic() throws SQLException{
        PreparedStatement p = c.prepareStatement(
                "SELECT count(1) AS c FROM Topic"
        );
        ResultSet r = p.executeQuery();
        r.next();
        return r.getInt("c");
    }

    public static String currentTime()
    {
        SimpleDateFormat simpleDateFormat =   new SimpleDateFormat(
                " yyyy-MM-dd HH:mm:ss " );
        return simpleDateFormat.format(new Date());
    }

    public String getForumTitleForForumID(int id) throws SQLException{
        PreparedStatement p = c.prepareStatement(
                "SELECT title FROM Forum WHERE id = ?"
        );
        p.setInt(1, id);
        ResultSet r = p.executeQuery();
        r.next();
        return r.getString("title");
    }

    public boolean isUserExist(String username) throws SQLException{
        PreparedStatement p = c.prepareStatement(
                "SELECT count(1) as c FROM Person WHERE username = ?"
        );
        p.setString(1, username);
        ResultSet r = p.executeQuery();
        r.next();
        return !r.next() || r.getInt("c") != 0;
    }
}
