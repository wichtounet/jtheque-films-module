package org.jtheque.films.persistence.dao.able;

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