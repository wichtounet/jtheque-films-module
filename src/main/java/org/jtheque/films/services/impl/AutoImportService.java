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
