package org.jtheque.films.controllers.able;

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

import org.jtheque.core.managers.view.able.controller.Controller;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.view.able.IExportView;

/**
 * An export controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IExportController extends Controller {
    /**
     * Open the export view.
     *
     * @param fileType The type of the file we want to export to.
     */
    void openExportView(Constants.Files.FileType fileType);

    /**
     * Export data to the file type.
     *
     * @param search   The search who define the datas we must export.
     * @param filePath The path to the file.
     */
    void export(Searcher<Film> search, String filePath);

    /**
     * Export a film to a file of a specific type.
     *
     * @param filePath The path to the film.
     * @param film     The film to export.
     */
    void exportFilm(String filePath, Film film);

    @Override
    IExportView getView();
}
