package org.jtheque.films.services.impl.utils.file.jt.reader;

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
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.utils.file.jt.JTFileReader;
import org.jtheque.core.utils.file.jt.able.JTFile;
import org.jtheque.films.services.impl.utils.file.jt.JTFEFile;
import org.jtheque.films.services.impl.utils.file.jt.JTFFile;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A reader for JTFE file.
 *
 * @author Baptiste Wicht
 */
public final class JTFEFileReader extends JTZippedFileReader {
    @Override
    public JTFile readFile(BufferedInputStream stream) throws FileException {
        JTFEFile file = new JTFEFile();

        Collection<File> files = FileUtils.unzip(stream, Managers.getCore().getFolders().getTempFolderPath());
        Collection<JTFFile> jFiles = new ArrayList<JTFFile>(10);

        JTFileReader reader = new JTFFileReader();

        for (File f : files) {
            JTFFile jtFile = (JTFFile) reader.readFile(f);

            jFiles.add(jtFile);

            if (!f.delete()) {
                Managers.getManager(IErrorManager.class).addInternationalizedError("errors.jtfe.delete", f.getAbsolutePath());
            }
        }

        file.setFiles(jFiles);

        return file;
    }
}