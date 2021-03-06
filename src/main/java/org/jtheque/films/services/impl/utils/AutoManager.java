package org.jtheque.films.services.impl.utils;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IFilmAutoService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.utils.Constants.Site;

import javax.annotation.Resource;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A manager to extract the films of a folder.
 *
 * @author Baptiste Wicht
 */
public final class AutoManager {
    private static final AutoManager INSTANCE = new AutoManager();

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IFilmAutoService filmAutoService;

    /**
     * Create a new AutoManager.
     */
    private AutoManager() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    /**
     * Return the unique instance of the class.
     *
     * @return The unique instance of the class.
     */
    public static AutoManager getInstance() {
        return INSTANCE;
    }

    /**
     * Fill all films.
     *
     * @param titles  The films titles.
     * @param webMode The mode to fill the films.
     * @param site    The site to get the informations from.
     *
     * @return A list containing all the films.
     */
    public Collection<Film> getFilmsOfFolder(Collection<String> titles, boolean webMode, Site site) {
        Collection<Film> films = new ArrayList<Film>(titles.size());

        for (String title : titles) {
            if (webMode) {
                Collection<FilmResult> results = filmAutoService.getFilms(site, title);

                if (results.isEmpty()) {
                    addSimpleFilm(films, title);
                } else {
                    Film film = filmAutoService.getFilm(results.iterator().next());

                    films.add(film);
                }
            } else {
                addSimpleFilm(films, title);
            }
        }

        return films;
    }

    /**
     * Add a simple film to the list.
     *
     * @param films The films list.
     * @param title The title of the film.
     */
    private void addSimpleFilm(Collection<Film> films, String title) {
        Film film = filmsService.getDefaultFilm();

        film.setTitle(title);

        films.add(film);
    }

    /**
     * Return all the film titles from a folder.
     *
     * @param folder   The folder
     * @param fileMode The file mode.
     *
     * @return A List containing all the titles of the film.
     */
    public Collection<String> getFilmTitles(File folder, boolean fileMode) {
        Collection<String> titles = new ArrayList<String>(50);

        for (File f : folder.listFiles()) {
            if (!f.isHidden()) {
                if (f.isFile() && fileMode) {
                    String title = f.getName();

                    title = title.substring(0, title.lastIndexOf('.'));
                    title = title.trim();

                    titles.add(title);
                }

                if (f.isDirectory() && !fileMode) {
                    String title = f.getName();

                    title = title.trim();

                    titles.add(title);
                }
            }
        }

        return titles;
    }
}
