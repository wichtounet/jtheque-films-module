package org.jtheque.films.services.impl.utils.file.imports;

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
     *
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
