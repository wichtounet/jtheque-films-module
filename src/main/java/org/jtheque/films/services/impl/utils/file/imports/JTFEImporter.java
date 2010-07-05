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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.films.services.impl.utils.file.jt.JTFEFile;
import org.jtheque.films.services.impl.utils.file.jt.JTFFile;
import org.jtheque.films.services.impl.utils.file.jt.reader.JTFEFileReader;
import org.jtheque.films.services.impl.utils.file.jt.reader.JTZippedFileReader;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.utils.io.FileException;

/**
 * An importer for JTFE file.
 *
 * @author Baptiste Wicht
 */
public final class JTFEImporter implements Importer {
    @Override
    public boolean canImportFrom(FileType fileType) {
        return fileType == FileType.JTFE;
    }

    @Override
    public void importFrom(String filePath) throws FileException {
        JTZippedFileReader reader = new JTFEFileReader();

        JTFEFile file = (JTFEFile) reader.readFile(filePath);

        if (file.isValid()) {
            JTFImporter importer = (JTFImporter) ImporterFactory.getImporter(FileType.JTF);

            for (JTFFile jtf : file.getFiles()) {
                importer.importFrom(jtf);
            }
        } else {
            throw new FileException(Managers.getManager(ILanguageManager.class).getMessage("errors.file.structureerror"));
        }
    }
}
