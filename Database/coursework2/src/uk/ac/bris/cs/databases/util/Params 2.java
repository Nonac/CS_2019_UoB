package uk.ac.bris.cs.databases.util;

import java.util.Collection;

/**
 *
 * @author csxdb
 */
public class Params {

    public static void cannotBeNull(Object o) {
        if (o == null) {
            throw new ParameterCannotBeNullException();
        }
    }
    
    public static void cannotBeEmpty(String s) {
        Params.cannotBeNull(s);
        if (s.equals("")) {
            throw new ParameterCannotBeEmptyException();
        }
    }
    
    public static void cannotBeEmpty(Collection c) {
        Params.cannotBeNull(c);
        if (c.isEmpty()) {
            throw new ParameterCannotBeEmptyException();
        }
    }
}
