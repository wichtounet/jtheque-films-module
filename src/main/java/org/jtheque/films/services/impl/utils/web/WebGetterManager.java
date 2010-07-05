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
import org.jtheque.films.utils.Constants.Site;

import java.util.Collection;

/**
 * Manage the automatic research of informations of a film on the web.
 *
 * @author Baptiste Wicht
 */
public final class WebGetterManager {
    private final WebGetterFactory factory;

    /**
     * Construct a new <code>WebGetterManager</code>.
     */
    public WebGetterManager() {
        super();

        factory = new WebGetterFactory();
    }

    /**
     * Return all the available films of the website <code>site</code> for <code>search</code>.
     *
     * @param site   The site on which we must search.
     * @param search The search of films we search.
     *
     * @return A list containing all the films found on the site with the search.
     */
    public Collection<FilmResult> getFilms(Site site, String search) {
        WebGetter getter = factory.getWebGetter(site);

        return getter.getFilms(search);
    }

    /**
     * Return the film on the site for the result.
     *
     * @param search The search of this films.
     *
     * @return The film with all the informations.
     */
    public Film getFilm(FilmResult search) {
        WebGetter getter = factory.getWebGetter(search.getSite());

        return getter.getFilm(search, null, null);
    }

    /**
     * Modify the film with the informations extracted on internet. We only modify the informations specified by
     * <code>args</code>.
     *
     * @param search The search of the film.
     * @param film   The film we modify.
     * @param args   The arguments who specify the fields we must modify.
     */
    public void modifyFilm(FilmResult search, Film film, EditArguments args) {
        WebGetter getter = factory.getWebGetter(search.getSite());

        film.saveToMemento();

        getter.getFilm(search, film, args);
    }
}
