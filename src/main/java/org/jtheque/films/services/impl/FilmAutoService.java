package org.jtheque.films.services.impl;

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
import org.jtheque.films.services.able.IFilmAutoService;
import org.jtheque.films.services.impl.utils.EditArguments;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.services.impl.utils.web.WebGetterManager;
import org.jtheque.films.utils.Constants.Site;

import java.util.Collection;

/**
 * The auto service implementation.
 *
 * @author Baptiste Wicht
 */
public final class FilmAutoService implements IFilmAutoService {
    private WebGetterManager webGetterManager;

    @Override
    public Collection<FilmResult> getFilms(Site site, String search) {
        return getManager().getFilms(site, search);
    }

    @Override
    public Film getFilm(FilmResult selectedFilm) {
        return getManager().getFilm(selectedFilm);
    }

    @Override
    public void modifyFilm(FilmResult result, Film film, EditArguments args) {
        getManager().modifyFilm(result, film, args);
    }

    /**
     * Return the manager.
     *
     * @return The web getter manager.
     */
    private WebGetterManager getManager() {
        if (webGetterManager == null) {
            webGetterManager = new WebGetterManager();
        }

        return webGetterManager;
    }
}