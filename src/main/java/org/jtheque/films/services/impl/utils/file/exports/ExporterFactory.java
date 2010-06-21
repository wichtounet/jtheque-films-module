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