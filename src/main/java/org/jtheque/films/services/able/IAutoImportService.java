package org.jtheque.films.services.able;

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
