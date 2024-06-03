package id.my.hendisantika.entity;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : quarkus-reactive-hibernate-graphql-crud
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 6/4/24
 * Time: 06:17
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Cacheable
@Getter
public class Movie extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;
    public String director;
    public LocalDate releaseDate;


    public static Uni<List<Movie>> getAllMovies() {
        return Movie
                .listAll(Sort.by("releaseDate"))
                .ifNoItem()
                .after(Duration.ofMillis(10000))
                .fail()
                .onFailure()
                .recoverWithUni(Uni.createFrom().<List<PanacheEntityBase>>item(Collections.EMPTY_LIST));

    }

    public static Uni<Movie> findByMovieId(Long id) {
        return findById(id);
    }

    public static Uni<Movie> updateMovie(Long id, Movie movie) {
        return Panache
                .withTransaction(() -> findByMovieId(id)
                        .onItem().ifNotNull()
                        .transform(entity -> {
                            entity.title = movie.title;
                            return entity;
                        })
                        .onFailure().recoverWithNull());
    }

    public static Uni<Movie> addMovie(Movie movie) {
        return Panache
                .withTransaction(movie::persist)
                .replaceWith(movie)
                .ifNoItem()
                .after(Duration.ofMillis(10000))
                .fail()
                .onFailure()
                .transform(t -> new IllegalStateException(t));
    }


    public static Uni<Boolean> deleteMovie(Long id) {
        return Panache.withTransaction(() -> deleteById(id));
    }


    public static Uni<Movie> addActorToMovie(Long movieId, Long actorId) {

        Uni<Movie> movie = findById(movieId);
        Uni<Actor> actor = Actor.findByActorId(actorId);

        Uni<Tuple2<Actor, Movie>> movieActorUni = Uni.combine()
                .all().unis(actor, movie).asTuple();

        return Panache
                .withTransaction(() -> movieActorUni
                        .onItem().ifNotNull()
                        .transform(entity -> {

                            if (entity.getItem2() == null || entity.getItem1() == null) {
                                return null;
                            }
                            return ActorMovieEntity.builder()
                                    .actor(entity.getItem1())
                                    .movie(entity.getItem2()).build();

                        })
                        .onItem().call(actorMovieEntity -> actorMovieEntity.persist())
                        .onItem().transform(actorMovieEntity -> actorMovieEntity.movie));

    }

    public String toString() {
        return this.getClass().getSimpleName() + "<" + this.id + ">";
    }
}
