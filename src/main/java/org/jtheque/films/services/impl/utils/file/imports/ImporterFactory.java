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

/**
 * A factory for importers.
 *
 * @author Baptiste Wicht
 */
final class ImporterFactory {
    private static final Importer[] IMPORTERS = {
            new XMLImporter(),
            new JTFImporter(),
            new JTFEImporter()
    };

    /**
     * Create a new ImporterFactory. This class isn't instanciable.
     */
    private ImporterFactory() {
        super();
    }

    /**
     * Get the importer for the specified file type.
     *
     * @param fileType The file type.
     * @return The importer for the specified file type.
     */
    public static Importer getImporter(FileType fileType) {
        Importer importer = null;

        for (Importer i : IMPORTERS) {
            if (i.canImportFrom(fileType)) {
                importer = i;
                break;
            }
        }

        return importer;
    }
}