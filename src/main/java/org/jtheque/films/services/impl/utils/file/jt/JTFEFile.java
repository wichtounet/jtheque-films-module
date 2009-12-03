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

import org.jtheque.core.utils.file.jt.able.JTZippedFile;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A JTFE File.
 *
 * @author Baptiste Wicht
 */
public final class JTFEFile implements JTZippedFile {
    private Collection<JTFFile> files;

    @Override
    public boolean isValid() {
        boolean valid = !files.isEmpty();

        for (JTFFile f : files) {
            if (!f.isValid()) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    /**
     * Return the files.
     *
     * @return The files.
     */
    public Iterable<JTFFile> getFiles() {
        return files;
    }

    /**
     * Set the files.
     *
     * @param files The files.
     */
    public void setFiles(Collection<JTFFile> files) {
        this.files = new ArrayList<JTFFile>(files);
    }
}