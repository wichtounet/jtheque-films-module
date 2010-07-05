package org.jtheque.films.services.impl;

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
