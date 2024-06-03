INSERT INTO movie(title, director, releasedate)
VALUES ('Reservoir Dogs', 'Quentin Tarantino', '1993-02-26');
INSERT INTO movie(title, director, releasedate)
VALUES ('Pulp Fiction', 'Quentin Tarantino', '1994-11-25');
INSERT INTO movie(title, director, releasedate)
VALUES ('The Mask', 'Chuck Russel', '1994-12-25');

INSERT INTO actor(name)
VALUES ('Quentin Tarantino');
INSERT INTO actor(name)
VALUES ('John Travolta');
INSERT INTO actor(name)
VALUES ('Samuel L Jackson');
INSERT INTO actor(name)
VALUES ('Jim Carey');

INSERT INTO actormovieentity(movie_id, actor_id)
VALUES (2, 1);
INSERT INTO actormovieentity(movie_id, actor_id)
VALUES (2, 2);
INSERT INTO actormovieentity(movie_id, actor_id)
VALUES (2, 3);
INSERT INTO actormovieentity(movie_id, actor_id)
VALUES (1, 2);
INSERT INTO actormovieentity(movie_id, actor_id)
VALUES (3, 4);
