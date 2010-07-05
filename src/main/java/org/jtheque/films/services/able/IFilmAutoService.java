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
