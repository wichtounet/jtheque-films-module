package org.jtheque.films.controllers.able;

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
