package org.jtheque.films.services.impl.utils.web;

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