package org.javaproteam27.socialnetwork.handler.exception;

public class PostNotAddedException extends Exception{
    public PostNotAddedException(String message) {
        super("Post not added, because: " + message);
    }
}
