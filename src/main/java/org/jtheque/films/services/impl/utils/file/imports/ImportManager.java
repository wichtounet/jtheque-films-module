package org.jtheque.films.services.impl.utils.file.imports;

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

import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.utils.io.FileException;

/**
 * An import manager. This class is the entry point for all the import operations.
 *
 * @author Baptiste Wicht
 */
public final class ImportManager {
    /**
     * Construct a new ImportManager.
     */
    private ImportManager() {
        super();
    }

    /**
     * Import all the data from a file.
     *
     * @param fileType The file type.
     * @param filePath The path to the file.
     * @throws FileException If there is an I/O during the import.
     */
    public static void importFrom(FileType fileType, String filePath) throws FileException {
        Importer importer = ImporterFactory.getImporter(fileType);

        importer.importFrom(filePath);
    }
}