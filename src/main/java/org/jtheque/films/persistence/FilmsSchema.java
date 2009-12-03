package org.jtheque.films.persistence;

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
import org.jtheque.core.managers.schema.AbstractSchema;
import org.jtheque.core.managers.schema.HSQLImporter;
import org.jtheque.core.managers.schema.Insert;
import org.jtheque.films.persistence.dao.able.IDaoFilms;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.primary.dao.able.IDaoCollections;
import org.jtheque.primary.dao.able.IDaoKinds;
import org.jtheque.primary.dao.able.IDaoLanguages;
import org.jtheque.primary.dao.able.IDaoLendings;
import org.jtheque.primary.dao.able.IDaoPersons;
import org.jtheque.primary.dao.able.IDaoSagas;
import org.jtheque.primary.dao.able.IDaoTypes;
import org.jtheque.utils.bean.Version;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The database schema for the Movies Module.
 *
 * @author Baptiste Wicht
 */
public final class FilmsSchema extends AbstractSchema {
    @Resource
    private SimpleJdbcTemplate jdbcTemplate;

    /**
     * Construct a new MoviesSchema.
     */
    public FilmsSchema() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public Version getVersion() {
        return new Version("1.1");
    }

    @Override
    public String getId() {
        return "Films-Schema";
    }

    @Override
    public String[] getDependencies() {
        return new String[]{"PrimaryUtils-schema"};
    }

    @Override
    public void install() {
        createDataTable();
        createReferentialIntegrityConstraints();
    }

    @Override
    public void update(Version from) {
        if ("1.0".equals(from.getVersion())) {
            createReferentialIntegrityConstraints();
            convertToPersons("T_ACTORS", IActorService.PERSON_TYPE);
            convertToPersons("T_REALIZERS", IRealizersService.PERSON_TYPE);
        }
    }

    /**
     * Create the tables for the data.
     */
    private void createDataTable() {
        jdbcTemplate.update("CREATE TABLE " + IDaoFilms.TABLE + " (ID INT IDENTITY PRIMARY KEY, TITLE VARCHAR(150) NOT NULL UNIQUE, YEAR INT, COMMENT VARCHAR(2000),DURATION INT,IMAGE VARCHAR(200),NOTE INT, FILEPATH VARCHAR(250), RESUME VARCHAR(2000),THE_REALIZER_FK INT,THE_COLLECTION_FK INT NOT NULL, THE_KIND_FK INT, THE_LANGUAGE_FK INT, THE_LENDING_FK INT, THE_SAGA_FK INT, THE_TYPE_FK INT)");
        jdbcTemplate.update("CREATE TABLE " + IDaoFilms.ACTORS_FILMS_TABLE + " (THE_FILM_FK INT NOT NULL, THE_ACTOR_FK INT NOT NULL)");
        jdbcTemplate.update("CREATE TABLE " + IDaoFilms.KINDS_FILMS_TABLE + " (THE_FILM_FK INT NOT NULL, THE_KIND_FK INT NOT NULL)");

        jdbcTemplate.update("CREATE INDEX FILM_IDX ON " + IDaoFilms.TABLE + "(ID)");
    }

    /**
     * Create the constraints of the referential integrity.
     */
    private void createReferentialIntegrityConstraints() {
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.ACTORS_FILMS_TABLE + " ADD FOREIGN KEY (THE_FILM_FK) REFERENCES  " + IDaoFilms.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.ACTORS_FILMS_TABLE + " ADD FOREIGN KEY (THE_ACTOR_FK) REFERENCES  " + IDaoPersons.TABLE + "  (ID) ON UPDATE SET NULL");

        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.KINDS_FILMS_TABLE + " ADD FOREIGN KEY (THE_FILM_FK) REFERENCES  " + IDaoFilms.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.KINDS_FILMS_TABLE + " ADD FOREIGN KEY (THE_KIND_FK) REFERENCES  " + IDaoKinds.TABLE + "  (ID) ON UPDATE SET NULL");

        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.TABLE + " ADD FOREIGN KEY (THE_KIND_FK) REFERENCES  " + IDaoKinds.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.TABLE + " ADD FOREIGN KEY (THE_REALIZER_FK) REFERENCES  " + IDaoPersons.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.TABLE + " ADD FOREIGN KEY (THE_COLLECTION_FK) REFERENCES  " + IDaoCollections.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.TABLE + " ADD FOREIGN KEY (THE_LANGUAGE_FK) REFERENCES  " + IDaoLanguages.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.TABLE + " ADD FOREIGN KEY (THE_LENDING_FK) REFERENCES  " + IDaoLendings.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.TABLE + " ADD FOREIGN KEY (THE_SAGA_FK) REFERENCES  " + IDaoSagas.TABLE + "  (ID) ON UPDATE SET NULL");
        jdbcTemplate.update("ALTER TABLE " + IDaoFilms.TABLE + " ADD FOREIGN KEY (THE_TYPE_FK) REFERENCES  " + IDaoTypes.TABLE + "  (ID) ON UPDATE SET NULL");
    }

    /**
     * Convert an old table of persons with the new person table.
     *
     * @param table The table to replace. This table will be dropped.
     * @param type  The type of persons.
     */
    private void convertToPersons(String table, String type) {
        List<Object[]> actors = jdbcTemplate.query("SELECT * FROM " + table, new SimplePersonRowMapper());

        String query = "INSERT INTO " + IDaoPersons.TABLE + "(NAME, FIRST_NAME, NOTE, THE_COUNTRY_FK, TYPE) VALUES (?,?,?,?,?)";

        for (Object[] author : actors) {
            jdbcTemplate.update(query, author[0], author[1], author[2], author[3], type);
        }

        jdbcTemplate.update("DROP TABLE IF EXISTS ?", table);
    }

    /**
     * A simple mapper to get persons from the old tables of persons (actors & realizers).
     *
     * @author Baptiste Wicht
     */
    private static final class SimplePersonRowMapper implements ParameterizedRowMapper<Object[]> {
        @Override
        public Object[] mapRow(ResultSet rs, int i) throws SQLException {
            Object[] person = new Object[4];

            person[0] = rs.getString("NAME");
            person[1] = rs.getString("FIRSTNAME");
            person[2] = rs.getInt("NOTE");
            person[3] = rs.getInt("THE_COUNTRY_FK");

            return person;
        }
    }

    @Override
    public void importDataFromHSQL(Iterable<Insert> inserts) {
        HSQLImporter importer = new HSQLImporter();

        importer.match("OD_SAGA_FILM", "INSERT INTO " + IDaoSagas.TABLE + " (ID, NAME, IMPL) VALUES (?,?,?)", "Films", 0, 2);
        importer.match("OD_KIND", "INSERT INTO " + IDaoKinds.TABLE + " (ID, NAME, IMPL) VALUES (?,?,?)", "Films", 0, 2);
        importer.match("OD_TYPE", "INSERT INTO " + IDaoTypes.TABLE + " (ID, NAME, IMPL) VALUES (?,?,?)", "Films", 0, 2);
        importer.match("FILM_ACTOR", "INSERT INTO " + IDaoFilms.ACTORS_FILMS_TABLE + " (THE_FILM_FK, THE_ACTOR_FK) VALUES (?,?)", 0, 1);
        importer.match("FILM_KIND", "INSERT INTO " + IDaoFilms.KINDS_FILMS_TABLE + " (THE_FILM_FK, THE_KIND_FK) VALUES (?,?)", 0, 1);
        importer.match("OD_LENDING", "INSERT INTO " + IDaoLendings.TABLE + " (ID, DATE, THE_BORROWER_FK, IMPL) VALUES (?,?,?,?)", "Films", 0, 2, 3);
        importer.match("OD_ACTOR", "INSERT INTO " + IDaoPersons.TABLE + " (ID, NAME, FIRST_NAME, NOTE, THE_COUNTRY_FK,TYPE) VALUES (?,?,?,?,?,?)", IActorService.PERSON_TYPE, 0, 3, 2, 4, 5);
        importer.match("OD_REALIZER", "INSERT INTO " + IDaoPersons.TABLE + " (ID, NAME, FIRST_NAME, NOTE, THE_COUNTRY_FK,TYPE) VALUES (?,?,?,?,?,?)", IRealizersService.PERSON_TYPE, 0, 3, 2, 4, 5);
        importer.match("OD_FILM_COLLECTION", "INSERT INTO " + IDaoCollections.TABLE + " (ID, TITLE, PROTECTED, PASSWORD, IMPL) VALUES(?,?,?,?,?)", "Films", 0, 4, 3, 2);
        importer.match("OD_FILM", "INSERT INTO " + IDaoFilms.TABLE + " (ID, TITLE, YEAR, COMMENT,DURATION,IMAGE,NOTE,RESUME,THE_REALIZER_FK,THE_COLLECTION_FK, THE_KIND_FK, THE_LANGUAGE_FK, THE_LENDING_FK, THE_SAGA_FK, THE_TYPE_FK, FILEPATH) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                0, 7, 8, 2, 3, 4, 5, 6, 13, 9, 10, 11, 12, 15, 14, 16);

        importer.importInserts(inserts);
    }
}