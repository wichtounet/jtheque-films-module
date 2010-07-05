package org.jtheque.films.services.impl.utils.file.jt;

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
import org.jtheque.utils.io.SimpleFilter;

/**
 * A File Filter for a certain type of file.
 *
 * @author Baptiste Wicht
 */
public final class JTFileFilter extends SimpleFilter {
    private final FileType fileType;

    /**
     * Construct a new JTFileFilter with a description, an extension and a file type.
     *
     * @param description The description of the file filter.
     * @param extensions  The extensions accepted by the filter.
     * @param fileType    The type of file this filter filter.
     */
    public JTFileFilter(String description, String extensions, FileType fileType) {
        super(description, extensions);

        this.fileType = fileType;
    }

    /**
     * Indicate if this filter can filter a certain type of file.
     *
     * @param fileType The file type we must test if this filter can filter.
     *
     * @return <code>true</code> if it can filter this type else <code>false</code>.
     */
    public boolean canFilter(FileType fileType) {
        return fileType == this.fileType;
    }
}
