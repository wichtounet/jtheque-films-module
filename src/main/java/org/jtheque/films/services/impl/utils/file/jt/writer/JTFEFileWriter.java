package org.jtheque.films.services.impl.utils.file.jt.writer;

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