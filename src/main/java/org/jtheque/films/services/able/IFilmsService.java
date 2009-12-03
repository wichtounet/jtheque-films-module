package org.jtheque.films.services.able;

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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.VideoFile;

import java.util.Collection;

/**
 * A service for the films functions.
 *
 * @author Baptiste Wicht
 */
public interface IFilmsService extends DataContainer<Film> {
    String DATA_TYPE = "Films";

    /**
     * Return an empty film.
     *
     * @return An empty film.
     */
    Film getDefaultFilm();

    /**
     * Print the list of films.
     */
    void printListOfFilms();

    /**
     * Generate an email with the description of the film to advise it to someone else.
     *
     * @param film The film to advise.
     * @return The String representing the body of the email.
     */
    String generateEmail(Film film);

    /**
     * Generate the description of the film in a String for printing it.
     *
     * @param film The film to describe.
     * @return The String representation of the description of the film.
     */
    String generateFilmDescriptionForPrinting(Film film);

    /**
     * Return all the video files.
     *
     * @return a List containing all the video files.
     */
    Collection<VideoFile> getVideoFiles();

    /**
     * Create the film.
     *
     * @param film The film to create.
     */
    void create(Film film);

    /**
     * Save the film.
     *
     * @param film The film to save.
     */
    void save(Film film);

    /**
     * Delete the film.
     *
     * @param film The film to delete.
     * @return true if the film has been deleted else false.
     */
    boolean delete(Film film);

    /**
     * Indicate if the service has nos films.
     *
     * @return true if there is no films else false.
     */
    boolean isNoFilms();

    /**
     * Create all the films.
     *
     * @param films The films to create.
     */
    void createAll(Collection<Film> films);

    /**
     * Return the films of the current collection.
     *
     * @return The films of the current collection.
     */
    Collection<Film> getFilms();

    @Override
    void addDataListener(DataListener listener);

    /**
     * Return an empty film.
     *
     * @return An empty film.
     */
    Film getEmptyFilm();
}