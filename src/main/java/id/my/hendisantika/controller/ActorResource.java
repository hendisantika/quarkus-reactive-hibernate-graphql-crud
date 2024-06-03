package id.my.hendisantika.controller;

import id.my.hendisantika.dto.ActorDTO;
import id.my.hendisantika.entity.Actor;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

import java.util.List;

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
@GraphQLApi
public class ActorResource {
    @Query("allActors")
    @Description("Get all Actors")
    public Uni<List<ActorDTO>> getAllActors() {
        return Actor.getAllActors()
                .onItem().transform(ActorDTO::from);
    }
}
