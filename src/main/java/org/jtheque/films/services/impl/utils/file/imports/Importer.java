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
 * An importer specification.
 *
 * @author Baptiste Wicht
 */
public interface Importer {
    /**
     * Indicate if the importer can import from the specified file type.
     *
     * @param fileType The file type.
     *
     * @return <code>true</code> if the importer can import from this file type else <code>false</code>.
     */
    boolean canImportFrom(FileType fileType);

    /**
     * Import all the data from the file.
     *
     * @param filePath The path to the file.
     *
     * @throws FileException This exception is throwed when the import cannot be correctly.
     */
    void importFrom(String filePath) throws FileException;
}