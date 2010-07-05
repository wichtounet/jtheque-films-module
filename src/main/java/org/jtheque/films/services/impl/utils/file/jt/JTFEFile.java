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
