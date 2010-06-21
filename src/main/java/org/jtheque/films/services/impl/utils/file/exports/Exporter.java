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

import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.primary.od.able.Data;

import java.util.Collection;

/**
 * An Exporter.
 *
 * @author Baptiste Wicht
 */
public interface Exporter<T extends Data> {
    /**
     * Indicate if the exporter can export to a file format.
     *
     * @param fileType The file format destination.
     *
     * @return true if the exporter can export to this file type else false.
     */
    boolean canExportTo(FileType fileType);

    /**
     * Export to the file path.
     *
     * @param path The file path.
     */
    void export(String path);

    /**
     * Set the datas of the exporter.
     *
     * @param datas The datas to set.
     */
    void setDatas(Collection<T> datas);

}