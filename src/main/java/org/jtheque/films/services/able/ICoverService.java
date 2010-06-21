package org.jtheque.films.services.able;

import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.cover.Format;

import java.awt.Image;

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