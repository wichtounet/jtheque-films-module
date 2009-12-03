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
import org.jtheque.films.services.able.IAutoImportService;
import org.jtheque.films.services.impl.utils.AutoManager;
import org.jtheque.films.utils.Constants.Site;

import java.io.File;
import java.util.Collection;

/**
 * An auto-import service implementation.
 *
 * @author Baptiste Wicht
 */
public final class AutoImportService implements IAutoImportService {
    @Override
    public Collection<String> getFilmTitles(File folder, boolean fileMode) {
        return AutoManager.getInstance().getFilmTitles(folder, fileMode);
    }

    @Override
    public Collection<Film> importFilms(Collection<String> titles, boolean webMode, Site site) {
        return AutoManager.getInstance().getFilmsOfFolder(titles, webMode, site);
    }
}