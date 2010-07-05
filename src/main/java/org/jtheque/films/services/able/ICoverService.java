package org.jtheque.films.services.able;

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.cover.Format;

import java.awt.Image;

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

/**
 * A cover service specification.
 *
 * @author Baptiste Wicht
 */
public interface ICoverService {
    /**
     * Return all the cover formats.
     *
     * @return An Array containing all the cover formats.
     */
    Format[] getFormats();

    /**
     * Return the image of a report.
     *
     * @param film   The film.
     * @param format The format.
     *
     * @return The image.
     */
    Image getReportImage(Film film, Format format);

    /**
     * Print the current report.
     */
    void printCurrentReport();

    /**
     * Export the current report to PDF.
     */
    void exportCurrentReportToPDF();
}
