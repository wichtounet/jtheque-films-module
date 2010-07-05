package org.jtheque.films.services.impl.utils.web;

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
import org.jtheque.films.services.impl.utils.web.analyzers.AbstractFilmAnalyzer;
import org.jtheque.films.services.impl.utils.web.analyzers.AbstractFilmResultAnalyzer;
import org.jtheque.films.utils.Constants.Site;

import java.util.Collection;

/**
 * A web getter. It seems an object who can search informations about a film on a specific film.
 *
 * @author Baptiste Wicht
 */
public interface WebGetter {
    /**
     * Indicate if the web getter can get informations on the site.
     *
     * @param site The site we must test if the getter can get on.
     *
     * @return <code>true</code> if the getter can search on the site else <code>false</code>.
     */
    boolean canGetOn(Site site);

    /**
     * Set the analyzer.
     *
     * @param analyzer The analyzer.
     */
    void setAnalyzer(AbstractFilmAnalyzer analyzer);

    /**
     * Return the analyzer of the getter.
     *
     * @return The film analyzer.
     */
    AbstractFilmAnalyzer getAnalyzer();

    /**
     * Set the result analyzer.
     *
     * @param analyzer The result analyzer.
     */
    void setResultAnalyzer(AbstractFilmResultAnalyzer analyzer);

    /**
     * Return the result analyzer of the getter.
     *
     * @return The film result analyzer.
     */
    AbstractFilmResultAnalyzer getResultAnalyzer();

    /**
     * Return all the films on the site for the search.
     *
     * @param search The research.
     *
     * @return A list containing all the films on the site corresponding to the search.
     */
    Collection<FilmResult> getFilms(String search);

    /**
     * Return the film modified if there is a film or a new film filled with the informations found on the site.
     *
     * @param search The search for the film.
     * @param film   The film to modify or <code>null</code> if this is only a get.
     * @param args   The arguments to specify what we must modify or only null if this only a get.
     *
     * @return The film modified or a new film if there is no film to modify. .
     */
    Film getFilm(FilmResult search, Film film, EditArguments args);
}
