package uk.ac.bris.cs.databases.api;

import uk.ac.bris.cs.databases.util.Params;

/**
 * The result of an operation that may or may not return a value, but may return
 * an error message in case of failure.
 * Result.success(v) - the operation completed successfully with value v.
 * Result.failure(m) - the operation failed in a way that is to be expected
 * (such as trying to create a new person with an username that already exists)
 * i.e. the end user made a mistake.
 * Result.fatal(m) - a problem that is not the user's fault, such as a broken
 * database connection.
 * @param <T> The result type if the operation was successful.
 * @author csxdb
 */
public class Result<T> {
    
    private enum Outcome { SUCCESS, FAILURE, FATAL };
    
    private final T value;
    private final Outcome success;
    private final String message;
    
    private Result(Outcome success, String message, T value) {
        this.success = success;
        this.message = message;
        this.value = value;
    }
    
    public static Result success() {
        return new Result(Outcome.SUCCESS, null, null);
    }
    
    public static <U> Result<U> success(U value) {
        return new Result(Outcome.SUCCESS, null, value);
    }
    
    public static Result failure(String message) {
        Params.cannotBeNull(message);
        return new Result(Outcome.FAILURE, message, null);
    }
    
    public static Result fatal(String message) {
        Params.cannotBeNull(message);
        return new Result(Outcome.FATAL, message, null);
    }
    
    public boolean isSuccess() {
        return this.success == Outcome.SUCCESS;
    }
    
    public T getValue() {
        if (this.success != Outcome.SUCCESS) {
            throw new RuntimeException();
        }
        return value;
    }
    
    public boolean isFatal() {
        switch (this.success) {
            case SUCCESS:
                throw new RuntimeException();
            case FAILURE:
                return false;
            case FATAL:
                return true;
        }
        throw new Error();
    }
    
    public String getMessage() {
        if (this.success == Outcome.SUCCESS) {
            throw new RuntimeException();
        } else {
            return this.message;
        }
    }
}
