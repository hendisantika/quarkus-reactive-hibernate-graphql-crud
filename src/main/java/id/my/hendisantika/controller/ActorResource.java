package id.my.hendisantika.controller;

import id.my.hendisantika.dto.ActorDTO;
import id.my.hendisantika.dto.MovieDTO;
import id.my.hendisantika.entity.Actor;
import id.my.hendisantika.entity.ActorMovieEntity;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

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

    @Query
    @Description("Get an actor")
    public Uni<ActorDTO> getActor(@Name("actorId") long id) {
        return Actor.findByActorId(id).onItem().transform(ActorDTO::from);
    }

    public Uni<List<MovieDTO>> movies(@Source(name = "ActorResponse") ActorDTO actor) {
        return ActorMovieEntity.getMoviesByActorQuery(actor.id).onItem().transform(actorMovieEntity ->
                actorMovieEntity.movie).collect().asList().onItem().transform(MovieDTO::from);
    }
}
