package org.jtheque.films.services.impl.utils.file.jt.reader;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.utils.file.jt.able.IJTFileReader;
import org.jtheque.core.utils.file.jt.able.JTFile;
import org.jtheque.utils.io.FileException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * A reader for zipped JT File.
 *
 * @author Baptiste Wicht
 */
public abstract class JTZippedFileReader implements IJTFileReader {
    @Override
    public final JTFile readFile(File file) throws FileException {
        try {
            return readFile(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new FileException(e);
        }
    }

    @Override
    public final JTFile readFile(String path) throws FileException {
        return readFile(new File(path));
    }

    @Override
    public final JTFile readFile(FileInputStream stream) throws FileException {
        JTFile file = null;

        try {
            file = readFile(new BufferedInputStream(stream));
        } catch (FileException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }

        return file;
    }
}
