package org.jtheque.films.persistence.dao.able;

/*
 * Copyright JTheque (Baptiste Wicht)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jtheque.core.managers.persistence.able.JThequeDao;
import org.jtheque.films.persistence.od.able.Film;

import java.util.Collection;

/**
 * A DAO for films specification.
 *
 * @author Baptiste Wicht
 */
public interface IDaoFilms extends JThequeDao {
    String TABLE = "T_FILMS";
    String ACTORS_FILMS_TABLE = "T_ACTORS_FILMS";
    String KINDS_FILMS_TABLE = "T_KINDS_FILMS";

    /**
     * Return all the films of the current collection.
     *
     * @return All the films of the current collection.
     */
    Collection<Film> getFilms();

    /**
     * Return the film with the specified id.
     *
     * @param id The id of the searched film.
     *
     * @return The <code>Film</code> with the specified id or <code>null</code> if there is no film with this id.
     */
    Film getFilm(int id);

    /**
     * Create the film.
     *
     * @param film The film to create.
     */
    void create(Film film);

    /**
     * Create all the films in the list.
     *
     * @param films The films to create.
     */
    void createAll(Iterable<Film> films);

    /**
     * Save the film on the database.
     *
     * @param film The film to save.
     */
    void save(Film film);

    /**
     * Delete the film.
     *
     * @param film The film to delete.
     *
     * @return true if the object is deleted else false.
     */
    boolean delete(Film film);

    /**
     * Create an empty film.
     *
     * @return An empty film.
     */
    Film createFilm();
}
