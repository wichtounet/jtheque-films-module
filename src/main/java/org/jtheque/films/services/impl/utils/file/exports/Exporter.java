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
