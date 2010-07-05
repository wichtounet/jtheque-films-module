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
