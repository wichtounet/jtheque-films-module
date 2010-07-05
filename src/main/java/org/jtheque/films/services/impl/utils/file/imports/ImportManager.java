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
     *
     * @throws FileException If there is an I/O during the import.
     */
    public static void importFrom(FileType fileType, String filePath) throws FileException {
        Importer importer = ImporterFactory.getImporter(fileType);

        importer.importFrom(filePath);
    }
}
