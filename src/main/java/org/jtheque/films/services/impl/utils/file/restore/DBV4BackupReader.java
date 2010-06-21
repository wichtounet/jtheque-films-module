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
import org.jtheque.primary.od.able.Lending;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ILendingsService;
import org.jtheque.primary.services.able.IPersonService;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.utils.DatabaseException;
import org.jtheque.utils.DatabaseUtils;
import org.jtheque.utils.bean.IntDate;

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
public final class DBV4BackupReader implements BackupReader {
    @Resource
    private INotesService notesService;

    @Resource
    private IPersonService borrowersService;

    @Resource
    private ISimpleDataService countriesService;

    @Resource
    private ILendingsService lendingsService;

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
     * Construct a new DBV4BackupReader.
     */
    public DBV4BackupReader() {
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
            Collection<Person> borrowers = importBorrowers(connection);
            Collection<Lending> lendings = importLendings(connection);

            ImporterUtils.persistDataOfImport(films, actors, realizers, kinds, types,
                    languages, countries, borrowers, lendings);
        } catch (DatabaseException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
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
            result = statement.executeQuery("SELECT * FROM t_films");

            while (result.next()) {
                Film film = filmsService.getEmptyFilm();

                importValuesOfFilm(result, film);
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
     * Import the informations of the film from the result set.
     *
     * @param result The result set.
     * @param film   The film to fill.
     *
     * @throws SQLException If an error occurs during the JDBC operations.
     */
    private void importValuesOfFilm(ResultSet result, Film film) throws SQLException {
        film.getTemporaryContext().setId(result.getInt("ID"));
        film.setTitle(result.getString("title"));
        film.getTemporaryContext().setKinds(Arrays.asList(result.getInt("kind")));
        film.getTemporaryContext().setType(result.getInt("type"));
        film.setYear(result.getInt("year"));
        film.setDuration(result.getInt("duration"));
        film.getTemporaryContext().setRealizer(result.getInt("realizer"));
        film.getTemporaryContext().setLanguage(result.getInt("language"));
        film.setNote(notesService.getDefaultNote());
        film.setResume(result.getString("resume"));
        film.setComment(result.getString("comment"));
    }

    /**
     * Import the actors of the film from the result set.
     *
     * @param connection The JDBC connection.
     * @param film       The film to fill.
     *
     * @throws DatabaseException If an error occurs during the JDBC operations.
     */
    private static void importActorsOfFilm(Connection connection, Film film) throws DatabaseException {
        PreparedStatement statement2 = null;
        ResultSet resultActors = null;
        try {
            statement2 = connection.prepareStatement("SELECT * FROM t_films_actors WHERE film = ?");

            statement2.setInt(1, film.getTemporaryContext().getId());

            resultActors = statement2.executeQuery();

            film.getTemporaryContext().setActors(DatabaseUtils.getAllIntResults(resultActors, "actor"));
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
            result = statement.executeQuery("SELECT * FROM t_actors");

            while (result.next()) {
                Person actor = actorService.getEmptyPerson();

                actor.getTemporaryContext().setId(result.getInt("ID"));
                actor.setName(result.getString("name"));
                actor.setFirstName(result.getString("firstname"));
                actor.getTemporaryContext().setCountry(result.getInt("country"));
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
        Collection<Person> realizers = new ArrayList<Person>(15);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM t_realizers");

            while (result.next()) {
                Person realizer = realizersService.getEmptyRealizer();

                realizer.getTemporaryContext().setId(result.getInt("ID"));
                realizer.setName(result.getString("name"));
                realizer.setFirstName(result.getString("firstname"));
                realizer.getTemporaryContext().setCountry(result.getInt("country"));
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
            result = statement.executeQuery("SELECT * FROM t_kinds");

            while (result.next()) {
                SimpleData kind = kindsService.getEmptySimpleData();

                kind.getTemporaryContext().setId(result.getInt("ID"));
                kind.setName(result.getString("name"));

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
            result = statement.executeQuery("SELECT * FROM t_types");

            while (result.next()) {
                SimpleData type = typesService.getEmptySimpleData();

                type.getTemporaryContext().setId(result.getInt("ID"));
                type.setName(result.getString("name"));

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
            result = statement.executeQuery("SELECT * FROM t_languages");

            while (result.next()) {
                SimpleData language = languagesService.getEmptySimpleData();

                language.getTemporaryContext().setId(result.getInt("ID"));
                language.setName(result.getString("name"));

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
        Collection<SimpleData> countries = new ArrayList<SimpleData>(10);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM t_countries");

            while (result.next()) {
                SimpleData country = countriesService.getEmptySimpleData();

                country.getTemporaryContext().setId(result.getInt("ID"));
                country.setName(result.getString("name"));

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

    /**
     * Import the borrowers.
     *
     * @param connection The connection.
     *
     * @return The imported borrowers.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<Person> importBorrowers(Connection connection) throws DatabaseException {
        Collection<Person> borrowers = new ArrayList<Person>(10);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM t_borrowers");

            while (result.next()) {
                Person borrower = borrowersService.getEmptyPerson();

                borrower.setName(result.getString("name"));
                borrower.setFirstName(result.getString("firstname"));
                borrower.setEmail(result.getString("mail"));

                borrowers.add(borrower);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return borrowers;
    }

    /**
     * Import the lendings.
     *
     * @param connection The connection.
     *
     * @return The imported lendings.
     *
     * @throws DatabaseException Thrown when an error occurs during the database data getting process.
     */
    private Collection<Lending> importLendings(Connection connection) throws DatabaseException {
        Collection<Lending> lendings = new ArrayList<Lending>(10);

        Statement statement = null;
        ResultSet result = null;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM t_lendings");

            while (result.next()) {
                Lending lending = lendingsService.getEmptyLending();

                lending.setDate(new IntDate(result.getInt("date")));
                lending.getTemporaryContext().setFilm(result.getInt("film"));
                lending.getTemporaryContext().setBorrower(result.getInt("borrower"));

                lendings.add(lending);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e);
        } finally {
            DatabaseUtils.close(result);
            DatabaseUtils.close(statement);
        }

        return lendings;
    }
}