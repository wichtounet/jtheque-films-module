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
import org.jtheque.films.utils.Constants;
import org.jtheque.utils.io.FileException;

/**
 * An import controller specification.
 *
 * @author Baptiste Wicht
 */
public interface IImportController extends Controller {
    /**
     * Open the export view.
     *
     * @param fileType The type of the file we want to export to.
     */
    void openImportView(Constants.Files.FileType fileType);

    /**
     * Import Data from a file.
     *
     * @param filePath The path to the file.
     * @throws FileException If there is an I/O problem during the import.
     */
    void importData(String filePath) throws FileException;
}
