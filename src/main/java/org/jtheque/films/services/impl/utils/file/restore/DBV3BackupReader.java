package org.jtheque.films.services.impl.utils.file.restore;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.file.able.BackupReader;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.able.INotesService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.file.imports.ImporterUtils;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.DatabaseException;
import org.jtheque.utils.DatabaseUtils;

import javax.annotation.Resource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * A reader for a database V4 backup.
 *
 * @author Baptiste Wicht
 */
public final class DBV3BackupReader implements BackupReader {
    @Resource
    private INotesService notesService;

    @Resource
    private ISimpleDataService countriesService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private IActorService actorService;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IRealizersService realizersService;

    /**
     * Construct a new DBV3BackupReader.
     */
    public DBV3BackupReader() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void readBackup(Object object) {
        Connection connection = (Connection) object;

        try {
            Collection<Film> films = importFilms(connection);
            Collection<Person> actors = importActors(connection);
            Collection<Person> realizers = importRealizers(connection);
            Collection<SimpleData> kinds = importKinds(connection);
            Collection<SimpleData> types = importTypes(connection);
            Collection<SimpleData> languages = importLanguages(connection);
            Collection<SimpleData> countries = importCountries(connection);

            ImporterUtils.persistDataOfImport(films, actors, realizers, kinds, types, languages, countries, null, null);
        } catch (DatabaseException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e, "Unable to import data");
        }

    }

    @Override
    public void persistTheData() {
    }

    /**
     * Import the films.
     *
     * @param connection The connection.
     *
     * @return The imported films.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<Film> importFilms(Connection connection) throws DatabaseException {
        Collection<Film> films = new ArrayList<Film>(25);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM film");

            while (result.next()) {
                Film film = filmsService.getEmptyFilm();

                importInformationsOfFilm(result, film);
                importActorsOfFilm(connection, film);

                films.add(film);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return films;
    }

    /**
     * Import the informations of the films from the result set.
     *
     * @param result The result set.
     * @param film   The film to fill.
     *
     * @throws SQLException If an error occurs during the result set read.
     */
    private void importInformationsOfFilm(ResultSet result, Film film) throws SQLException {
        film.getTemporaryContext().setId(result.getInt("ID"));
        film.setTitle(result.getString("nom"));
        film.getTemporaryContext().setKinds(Arrays.asList(result.getInt("genre")));
        film.getTemporaryContext().setType(result.getInt("type"));
        film.setYear(result.getInt("annee"));
        film.setDuration(result.getInt("duree"));
        film.getTemporaryContext().setRealizer(result.getInt("realisateur"));
        film.getTemporaryContext().setLanguage(result.getInt("langue"));
        film.setNote(notesService.getDefaultNote());
    }

    /**
     * Import the actors of the film.
     *
     * @param connection The connection to get information from.
     * @param film       The film to get the informations for.
     *
     * @throws DatabaseException If a database exception occurs during JDBC operations.
     */
    private static void importActorsOfFilm(Connection connection, Film film) throws DatabaseException {
        PreparedStatement statement2 = null;
        ResultSet resultActors = null;
        try {
            statement2 = connection.prepareStatement("SELECT * FROM film_acteur WHERE ID_film = ?");

            statement2.setInt(1, film.getTemporaryContext().getId());

            resultActors = statement2.executeQuery();

            film.getTemporaryContext().setActors(DatabaseUtils.getAllIntResults(resultActors, "ID_acteur"));
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(resultActors);
            DatabaseUtils.close(statement2);
        }
    }

    /**
     * Import the actors.
     *
     * @param connection The connection.
     *
     * @return The imported actors.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<Person> importActors(Connection connection) throws DatabaseException {
        Collection<Person> actors = new ArrayList<Person>(50);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM acteur");

            while (result.next()) {
                Person actor = actorService.getEmptyPerson();

                actor.getTemporaryContext().setId(result.getInt("ID"));
                actor.setName(result.getString("nom"));
                actor.setFirstName(result.getString("prenom"));
                actor.getTemporaryContext().setCountry(result.getInt("pays"));
                actor.setNote(notesService.getDefaultNote());

                actors.add(actor);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return actors;
    }

    /**
     * Import the realizers.
     *
     * @param connection The connection.
     *
     * @return The imported realizers.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<Person> importRealizers(Connection connection) throws DatabaseException {
        Collection<Person> realizers = new ArrayList<Person>(25);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM realisateur");

            while (result.next()) {
                Person realizer = realizersService.getEmptyRealizer();

                realizer.getTemporaryContext().setId(result.getInt("ID"));
                realizer.setName(result.getString("nom"));
                realizer.setFirstName(result.getString("prenom"));
                realizer.getTemporaryContext().setCountry(result.getInt("pays"));
                realizer.setNote(notesService.getDefaultNote());

                realizers.add(realizer);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return realizers;
    }

    /**
     * Import the kinds.
     *
     * @param connection The connection.
     *
     * @return The imported kinds.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<SimpleData> importKinds(Connection connection) throws DatabaseException {
        Collection<SimpleData> kinds = new ArrayList<SimpleData>(10);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM genre");

            while (result.next()) {
                SimpleData kind = kindsService.getEmptySimpleData();

                kind.getTemporaryContext().setId(result.getInt("ID"));
                kind.setName(result.getString("nom"));

                kinds.add(kind);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return kinds;
    }

    /**
     * Import the types.
     *
     * @param connection The connection.
     *
     * @return The imported types.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<SimpleData> importTypes(Connection connection) throws DatabaseException {
        Collection<SimpleData> types = new ArrayList<SimpleData>(10);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM type");

            while (result.next()) {
                SimpleData type = typesService.getEmptySimpleData();

                type.getTemporaryContext().setId(result.getInt("ID"));
                type.setName(result.getString("nom"));

                types.add(type);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return types;
    }

    /**
     * Import the languages.
     *
     * @param connection The connection.
     *
     * @return The imported languages.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<SimpleData> importLanguages(Connection connection) throws DatabaseException {
        Collection<SimpleData> languages = new ArrayList<SimpleData>(10);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM langue");

            while (result.next()) {
                SimpleData language = languagesService.getEmptySimpleData();

                language.getTemporaryContext().setId(result.getInt("ID"));
                language.setName(result.getString("nom"));

                languages.add(language);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return languages;
    }

    /**
     * Import the countries.
     *
     * @param connection The connection.
     *
     * @return The imported countries.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<SimpleData> importCountries(Connection connection) throws DatabaseException {
        Collection<SimpleData> countries = new ArrayList<SimpleData>(20);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM pays");

            while (result.next()) {
                SimpleData country = countriesService.getEmptySimpleData();

                country.getTemporaryContext().setId(result.getInt("ID"));
                country.setName(result.getString("nom"));

                countries.add(country);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return countries;
    }
}