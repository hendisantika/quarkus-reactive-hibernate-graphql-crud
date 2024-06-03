package id.my.hendisantika.control.exception;

import io.smallrye.graphql.api.ErrorCode;

/**
 * Created by IntelliJ IDEA.
 * Project : quarkus-reactive-hibernate-graphql-crud
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/4/24
 * Time: 06:24
 * To change this template use File | Settings | File Templates.
 */
@ErrorCode("ALREADY_EXISTING")
public class AlreadyExistingException extends RuntimeException {
    public AlreadyExistingException(String id) {
        super("Relation: " + id + " was found.");
    }
}
