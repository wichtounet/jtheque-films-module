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
import org.jtheque.core.utils.file.jt.JTZippedFileWriter;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.file.jt.sources.JTFEDataSource;
import org.jtheque.films.services.impl.utils.file.jt.writer.JTFEFileWriter;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.utils.io.FileException;

/**
 * An exporter to JTFE.
 *
 * @author Baptiste Wicht
 */
public final class JTFEExporter extends AbstractExporter<Film> {
    @Override
    public boolean canExportTo(FileType fileType) {
        return fileType == FileType.JTFE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void export(String path) {
        JTFEDataSource source = new JTFEDataSource();
        source.setFileVersion(Constants.Files.Versions.JTF.FIRST);
        source.setVersion(Managers.getCore().getApplication().getVersion());
        source.setFilms(getDatas());

        JTZippedFileWriter writer = new JTFEFileWriter();

        try {
            writer.writeFile(path, source);
        } catch (FileException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }
}