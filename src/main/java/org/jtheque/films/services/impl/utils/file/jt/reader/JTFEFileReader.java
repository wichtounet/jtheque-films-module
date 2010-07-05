package org.jtheque.films.services.impl.utils.file.jt.reader;

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
