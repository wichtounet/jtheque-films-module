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

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.utils.Constants.Site;

import java.util.Collection;

/**
 * A service for the automatic recuperation of data.
 *
 * @author Baptiste Wicht
 */
public interface IFilmAutoService {
    /**
     * Return the films we found in a site for a specific search.
     *
     * @param site   The site to search in.
     * @param search The film search.
     *
     * @return A List containing all the films found on the site for the search.
     */
    Collection<FilmResult> getFilms(Site site, String search);

    /**
     * Modify a film using a film found in a site.
     *
     * @param filmResult The result to get the informations from.
     * @param film       The film to edit.
     * @param args       The arguments who describe the fields to edit.
     */
    void modifyFilm(FilmResult filmResult, Film film, EditArguments args);

    /**
     * Fill a film with the information of the film result and return it.
     *
     * @param filmResult The fim result to get the information from.
     *
     * @return A film filled with the information of the site.
     */
    Film getFilm(FilmResult filmResult);
}