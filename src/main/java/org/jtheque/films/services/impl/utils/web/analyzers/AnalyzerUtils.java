package org.jtheque.films.services.impl.utils.web.analyzers;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.utils.io.FileException;
import org.jtheque.utils.io.FileUtils;

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
                FileUtils.downloadFile(path, destinationPath);
            } catch (FileException e) {
                Managers.getManager(ILoggingManager.class).getLogger(AnalyzerUtils.class).error(e);
            }

            film.setImage(destinationPath);
        }
    }
}
