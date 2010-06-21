package org.jtheque.films.services.impl.utils.file.jt;

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