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

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.utils.Constants.Files.FileType;

import java.util.Arrays;
import java.util.Collection;

/**
 * An Exporter factory.
 *
 * @author Baptiste Wicht
 */
public final class ExporterFactory {
    private static final Collection<AbstractExporter<Film>> EXPORTERS = Arrays.asList(
            new TextExporter(),
            new XMLExporter(),
            new HTMLExporter(),
            new JTFExporter(),
            new JTFEExporter(),
            new XLSExporter(),
            new PDFExporter(),
            new CSVExporter(),
            new RTFExporter());

    /**
     * Construct a new ExporterFactory. This class isn't instanciable.
     */
    private ExporterFactory() {
        super();
    }

    /**
     * Return the exporter for the spécific file type.
     *
     * @param fileType The file type.
     *
     * @return The exporter or <code>null</code> if we don't find the exporter of the file type
     */
    public static Exporter<Film> getExporter(FileType fileType) {
        Exporter<Film> exporter = null;

        for (Exporter<Film> e : EXPORTERS) {
            if (e.canExportTo(fileType)) {
                exporter = e;
                break;
            }
        }

        return exporter;
    }
}
