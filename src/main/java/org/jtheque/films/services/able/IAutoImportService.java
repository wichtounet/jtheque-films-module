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
import org.jtheque.films.utils.Constants.Site;

import java.io.File;
import java.util.Collection;

/**
 * A service for the auto import of films from a folder.
 *
 * @author Baptiste Wicht
 */
public interface IAutoImportService {
    /**
     * Return the titles of the film from a folder.
     *
     * @param folder   The folder to search in.
     * @param fileMode The fileMode. If true, we search for filename, else for directory name.
     *
     * @return A List containing all the titles found in the folder.
     */
    Collection<String> getFilmTitles(File folder, boolean fileMode);

    /**
     * Import the films titles.
     *
     * @param titles  All the titles to search.
     * @param webMode The mode. If true, all the informations are extracted from internet, else the film are simply
     *                created but not filled.
     * @param site    The site to search in.
     *
     * @return A List containing all the films.
     */
    Collection<Film> importFilms(Collection<String> titles, boolean webMode, Site site);
}