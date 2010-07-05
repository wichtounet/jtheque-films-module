package org.jtheque.films.services.impl.utils.file.exports;

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
