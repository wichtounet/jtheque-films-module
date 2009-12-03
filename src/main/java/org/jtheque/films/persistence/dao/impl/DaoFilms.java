package org.jtheque.films.persistence.dao.impl;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.persistence.GenericDao;
import org.jtheque.core.managers.persistence.Query;
import org.jtheque.core.managers.persistence.QueryMapper;
import org.jtheque.core.managers.persistence.able.Entity;
import org.jtheque.core.managers.persistence.context.IDaoPersistenceContext;
import org.jtheque.core.utils.db.DaoNotes;
import org.jtheque.core.utils.db.DaoNotes.NoteType;
import org.jtheque.films.persistence.dao.able.IDaoFilms;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.persistence.od.impl.FilmActorRelation;
import org.jtheque.films.persistence.od.impl.FilmImpl;
import org.jtheque.films.persistence.od.impl.FilmKindRelation;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.dao.able.IDaoKinds;
import org.jtheque.primary.dao.able.IDaoLanguages;
import org.jtheque.primary.dao.able.IDaoLendings;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.dao.able.IDaoSagas;
import org.jtheque.primary.dao.able.IDaoTypes;
import org.jtheque.primary.od.able.Collection;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.od.able.Person;
import org.jtheque.utils.collections.CollectionUtils;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A DAO implementation for films.
 *
 * @author Baptiste Wicht
 */
public final class DaoFilms extends GenericDao<Film> implements IDaoFilms {
    private final ParameterizedRowMapper<Film> rowMapper = new FilmRowMapper();
    private final ParameterizedRowMapper<FilmKindRelation> relationKindRowMapper = new RelationKindRowMapper();
    private final ParameterizedRowMapper<FilmActorRelation> relationActorRowMapper = new RelationActorRowMapper();
    private final QueryMapper queryMapper = new FilmQueryMapper();

    private java.util.Collection<FilmActorRelation> relationsToActors;
    private java.util.Collection<FilmKindRelation> relationsToKinds;

    @Resource
    private IDaoPersistenceContext persistenceContext;

    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    @Resource
    private IDaoCollections daoCollections;

    @Resource
    private IDaoKinds daoKinds;

    @Resource
    private IDaoTypes daoTypes;

    @Resource
    private IDaoSagas daoSagas;

    @Resource
    private IDaoLendings daoLendings;

    @Resource
    private IDaoPersons daoPersons;

    @Resource
    private IDaoLanguages daoLanguages;

    /**
     * Construct a new DaoFilms.
     */
    public DaoFilms() {
        super(TABLE);
    }

    @Override
    @SuppressWarnings("unchecked")
    public java.util.Collection<Film> getFilms() {
        List<Film> films = CollectionUtils.copyOf(getFilms(daoCollections.getCurrentCollection()));

        Collections.sort(films);

        return films;
    }

    /**
     * Return all the films of the collection.
     *
     * @param collection The collection.
     * @return A List containing all the films of the collections.
     */
    private java.util.Collection<Film> getFilms(Collection collection) {
        if (collection == null || !collection.isSaved()) {
            return getAll();
        }

        load();

        java.util.Collection<Film> films = new ArrayList<Film>(getCache().size() / 2);

        for (Film film : getCache().values()) {
            if (film.getTheCollection().getId() == collection.getId()) {
                films.add(film);
            }
        }

        return films;
    }

    @Override
    public Film getFilm(int id) {
        return get(id);
    }

    @Override
    public void createAll(Iterable<Film> films) {
        for (Film film : films) {
            create(film);
        }
    }

    @Override
    public Film createFilm() {
        return new FilmImpl();
    }

    @Override
    public boolean delete(Film film) {
        boolean deleted = super.delete(film);

        jdbcTemplate.update("DELETE FROM " + ACTORS_FILMS_TABLE + " WHERE THE_FILM_FK = ?", film.getId());
        jdbcTemplate.update("DELETE FROM " + KINDS_FILMS_TABLE + " WHERE THE_FILM_FK = ?", film.getId());

        return deleted;
    }

    @Override
    public void save(Film film) {
        super.save(film);

        jdbcTemplate.update("DELETE FROM " + ACTORS_FILMS_TABLE + " WHERE THE_FILM_FK = ?", film.getId());
        jdbcTemplate.update("DELETE FROM " + KINDS_FILMS_TABLE + " WHERE THE_FILM_FK = ?", film.getId());

        updateLinksBetweenTable(film);
    }

    @Override
    public void create(Film film) {
        film.setTheCollection(daoCollections.getCurrentCollection());

        super.create(film);

        updateLinksBetweenTable(film);
    }

    /**
     * Update the links between table.
     *
     * @param film The film.
     */
    private void updateLinksBetweenTable(Film film) {
        for (Person actor : film.getActors()) {
            jdbcTemplate.update("INSERT INTO " + ACTORS_FILMS_TABLE + " (THE_FILM_FK, THE_ACTOR_FK) VALUES(?,?)", film.getId(), actor.getId());
        }

        for (Kind kind : film.getKinds()) {
            jdbcTemplate.update("INSERT INTO " + KINDS_FILMS_TABLE + " (THE_FILM_FK, THE_KIND_FK) VALUES(?,?)", film.getId(), kind.getId());
        }
    }

    @Override
    protected ParameterizedRowMapper<Film> getRowMapper() {
        return rowMapper;
    }

    @Override
    protected QueryMapper getQueryMapper() {
        return queryMapper;
    }

    @Override
    protected void loadCache() {
        relationsToActors = jdbcTemplate.query("SELECT * FROM " + ACTORS_FILMS_TABLE, relationActorRowMapper);
        relationsToKinds = jdbcTemplate.query("SELECT * FROM " + KINDS_FILMS_TABLE, relationKindRowMapper);

        java.util.Collection<Film> movies = persistenceContext.getSortedList(TABLE, rowMapper);

        for (Film film : movies) {
            getCache().put(film.getId(), film);
        }

        setCacheEntirelyLoaded();

        relationsToActors.clear();
        relationsToKinds.clear();
    }

    @Override
    protected void load(int i) {
        Film film = persistenceContext.getDataByID(TABLE, i, rowMapper);

        getCache().put(i, film);
    }

    /**
     * A row mapper to map resultset to relation between film and kind.
     *
     * @author Baptiste Wicht
     */
    private static final class RelationKindRowMapper implements ParameterizedRowMapper<FilmKindRelation> {
        @Override
        public FilmKindRelation mapRow(ResultSet rs, int i) throws SQLException {
            FilmKindRelation relation = new FilmKindRelation();

            relation.setTheFilm(rs.getInt("THE_FILM_FK"));
            relation.setTheKind(rs.getInt("THE_KIND_FK"));

            return relation;
        }
    }

    /**
     * A row mapper to map resultset to relation between film and actor.
     *
     * @author Baptiste Wicht
     */
    private static final class RelationActorRowMapper implements ParameterizedRowMapper<FilmActorRelation> {
        @Override
        public FilmActorRelation mapRow(ResultSet rs, int i) throws SQLException {
            FilmActorRelation relation = new FilmActorRelation();

            relation.setTheFilm(rs.getInt("THE_FILM_FK"));
            relation.setTheActor(rs.getInt("THE_ACTOR_FK"));

            return relation;
        }
    }

    /**
     * A row mapper to map resultset to film.
     *
     * @author Baptiste Wicht
     */
    private final class FilmRowMapper implements ParameterizedRowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int i) throws SQLException {
            Film film = createFilm();

            film.setId(rs.getInt("ID"));
            film.setTitle(rs.getString("TITLE"));
            film.setYear(rs.getInt("YEAR"));
            film.setNote(DaoNotes.getInstance().getNote(NoteType.getEnum(rs.getInt("NOTE"))));
            film.setComment(rs.getString("COMMENT"));
            film.setDuration(rs.getInt("DURATION"));
            film.setFilePath(rs.getString("FILEPATH"));
            film.setImage(rs.getString("IMAGE"));
            film.setResume(rs.getString("RESUME"));
            film.setTheCollection(daoCollections.getCollection(rs.getInt("THE_COLLECTION_FK")));
            film.setTheLanguage(daoLanguages.getLanguage(rs.getInt("THE_LANGUAGE_FK")));
            film.setTheLending(daoLendings.getLending(rs.getInt("THE_LENDING_FK")));
            film.setTheRealizer(daoPersons.getPerson(rs.getInt("THE_REALIZER_FK")));
            film.setTheSaga(daoSagas.getSaga(rs.getInt("THE_SAGA_FK")));
            film.setTheType(daoTypes.getType(rs.getInt("THE_TYPE_FK")));

            mapRelations(film);

            return film;
        }

        /**
         * Map the relations.
         *
         * @param film The film to map the relations for.
         */
        private void mapRelations(Film film) {
            mapKindsRelations(film);
            mapActorsRelations(film);
        }

        /**
         * Map the kind relations.
         *
         * @param film The film to map the relations for.
         */
        private void mapKindsRelations(Film film) {
            if (relationsToKinds != null && !relationsToKinds.isEmpty()) {
                for (FilmKindRelation relation : relationsToKinds) {
                    if (relation.getTheFilm() == film.getId()) {
                        film.addKind(daoKinds.getKind(relation.getTheKind()));
                    }
                }
            } else {
                relationsToKinds = jdbcTemplate.query("SELECT * FROM " + KINDS_FILMS_TABLE + " WHERE THE_FILM_FK = ?", relationKindRowMapper, film.getId());

                for (FilmKindRelation relation : relationsToKinds) {
                    film.addKind(daoKinds.getKind(relation.getTheKind()));
                }

                relationsToKinds.clear();
            }
        }

        /**
         * Map the actor relations.
         *
         * @param film The film to map the relations for.
         */
        private void mapActorsRelations(Film film) {
            if (relationsToActors != null && !relationsToActors.isEmpty()) {
                for (FilmActorRelation relation : relationsToActors) {
                    if (relation.getTheFilm() == film.getId()) {
                        Person actor = daoPersons.getPerson(relation.getTheActor());

                        if (actor == null) {
                            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error("Film ({}) references a null actor id = {}",
                                    film.getId(), relation.getTheActor());
                        } else {
                            film.addActor(actor);
                        }
                    }
                }
            } else {
                relationsToActors = jdbcTemplate.query("SELECT * FROM " + ACTORS_FILMS_TABLE + " WHERE THE_FILM_FK = ?", relationActorRowMapper, film.getId());

                for (FilmActorRelation relation : relationsToActors) {
                    Person actor = daoPersons.getPerson(relation.getTheActor());

                    if (actor == null) {
                        Managers.getManager(ILoggingManager.class).getLogger(getClass()).error("Film ({}) references a null actor id = {}",
                                film.getId(), relation.getTheActor());
                    } else {
                        film.addActor(actor);
                    }
                }

                relationsToActors.clear();
            }
        }
    }

    /**
     * A query mapper to map film to sql query.
     *
     * @author Baptiste Wicht
     */
    private static final class FilmQueryMapper implements QueryMapper {
        @Override
        public Query constructInsertQuery(Entity entity) {
            Film film = (Film) entity;

            String query = "INSERT INTO " + TABLE + " (TITLE, NOTE, COMMENT, DURATION, FILEPATH, IMAGE, RESUME, THE_COLLECTION_FK, THE_LANGUAGE_FK, THE_LENDING_FK, " +
                    "THE_REALIZER_FK, THE_SAGA_FK, THE_TYPE_FK) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

            Object[] parameters = {
                    film.getTitle(),
                    film.getNote().getValue().intValue(),
                    film.getComment(),
                    film.getDuration(),
                    film.getFilePath(),
                    film.getImage(),
                    film.getResume(),
                    film.getTheCollection().getId(),
                    film.getTheLanguage() == null ? null : film.getTheLanguage().getId(),
                    film.getTheLending() == null ? null : film.getTheLending().getId(),
                    film.getTheRealizer() == null ? null : film.getTheRealizer().getId(),
                    film.getTheSaga() == null ? null : film.getTheSaga().getId(),
                    film.getTheType() == null ? null : film.getTheType().getId(),
            };

            return new Query(query, parameters);
        }

        @Override
        public Query constructUpdateQuery(Entity entity) {
            Film film = (Film) entity;

            String query = "UPDATE " + TABLE + " SET TITLE = ?, NOTE = ?, COMMENT = ?, DURATION = ?, FILEPATH = ?, IMAGE = ?, RESUME = ?, THE_COLLECTION_FK = ?, " +
                    "THE_LANGUAGE_FK = ?, THE_LENDING_FK = ?, THE_REALIZER_FK = ?, THE_SAGA_FK = ?, THE_TYPE_FK = ? WHERE ID = ?";

            Object[] parameters = {
                    film.getTitle(),
                    film.getNote().getValue().intValue(),
                    film.getComment(),
                    film.getDuration(),
                    film.getFilePath(),
                    film.getImage(),
                    film.getResume(),
                    film.getTheCollection().getId(),
                    film.getTheLanguage() == null ? null : film.getTheLanguage().getId(),
                    film.getTheLending() == null ? null : film.getTheLending().getId(),
                    film.getTheRealizer() == null ? null : film.getTheRealizer().getId(),
                    film.getTheSaga() == null ? null : film.getTheSaga().getId(),
                    film.getTheType() == null ? null : film.getTheType().getId(),
                    film.getId()
            };

            return new Query(query, parameters);
        }
    }
}