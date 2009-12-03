package org.jtheque.films.services.impl.utils.file.exports;

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
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.utils.file.jt.JTFileWriter;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.file.jt.sources.JTFDataSource;
import org.jtheque.films.services.impl.utils.file.jt.writer.JTFFileWriter;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.utils.io.FileException;

/**
 * An exporter to JTF.
 *
 * @author Baptiste Wicht
 */
public final class JTFExporter extends AbstractExporter<Film> {
    @Override
    public boolean canExportTo(FileType fileType) {
        return fileType == FileType.JTF;
    }

    @Override
    public void export(String path) {
        Film result = getDatas().iterator().next();

        JTFDataSource source = new JTFDataSource();

        source.setFileVersion(Constants.Files.Versions.JTF.SECOND);
        source.setVersion(Managers.getCore().getApplication().getVersion());
        source.setFilm(result);

        JTFileWriter writer = new JTFFileWriter();

        try {
            writer.writeFile(path, source);
        } catch (FileException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }
}