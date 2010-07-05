package org.jtheque.films.services.impl.utils.web.analyzers;

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
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.WebUtils;

import java.io.File;

/**
 * Utils for the analyzer.
 *
 * @author Baptiste Wicht
 */
final class AnalyzerUtils {
    /**
     * Construct a new AnalyzerUtils.
     */
    private AnalyzerUtils() {
        super();
    }

    /**
     * Download the image of a film.
     *
     * @param film The film.
     * @param path The path to the image.
     */
    public static void downloadMiniature(Film film, String path) {
        File folder = new File(Managers.getCore().getFolders().getApplicationFolder().getAbsolutePath() +
                "/miniatures");

        boolean ok = true;

        if (!folder.exists()) {
            ok = folder.mkdirs();
        }

        if (ok) {
            String destinationPath = org.jtheque.films.utils.FileUtils.prepareFilePath(
                    Managers.getCore().getFolders().getApplicationFolder().getAbsolutePath() +
                            "/miniatures/" + film.getTitle() + ".jpg");

            try {
                WebUtils.downloadFile(path, destinationPath);
            } catch (FileException e) {
                Managers.getManager(ILoggingManager.class).getLogger(AnalyzerUtils.class).error(e);
            }

            film.setImage(destinationPath);
        }
    }
}
