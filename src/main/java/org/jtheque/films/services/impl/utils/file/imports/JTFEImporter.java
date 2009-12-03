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