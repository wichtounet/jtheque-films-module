package org.jtheque.films.services.impl.utils.file.jt.writer;

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
import org.jtheque.core.managers.file.able.BasicDataSource;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.utils.file.jt.JTFileWriter;
import org.jtheque.core.utils.file.jt.JTZippedFileWriter;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.file.jt.sources.JTFDataSource;
import org.jtheque.films.services.impl.utils.file.jt.sources.JTFEDataSource;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A writer for JTFE file.
 *
 * @author Baptiste Wicht
 */
public final class JTFEFileWriter extends JTZippedFileWriter {
    @Override
    public void writeFile(BufferedOutputStream stream, BasicDataSource source) {
        JTFEDataSource dataSource = (JTFEDataSource) source;

        Collection<File> files = new ArrayList<File>(dataSource.getFilms().size());
        JTFileWriter writer = new JTFFileWriter();

        int count = 0;
        for (Film film : dataSource.getFilms()) {
            ++count;

            File f = new File(Managers.getCore().getFolders().getTempFolderPath() + File.separator + count + ".jtf");

            try {
                BasicDataSource filmDataSource = new JTFDataSource(film);
                filmDataSource.setFileVersion(dataSource.getFileVersion());
                filmDataSource.setVersion(dataSource.getVersion());

                writer.writeFile(f, filmDataSource);
            } catch (FileException e) {
                Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
            }

            files.add(f);
        }

        FileUtils.zip(files, stream);

        for (File f : files) {
            if (!f.delete()) {
                Managers.getManager(IErrorManager.class).addInternationalizedError("errors.jtfe.delete", f.getAbsolutePath());
            }
        }
    }
}
