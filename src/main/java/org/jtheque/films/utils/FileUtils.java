package org.jtheque.films.utils;

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
import org.jtheque.core.managers.language.ILanguageManager;

import java.io.File;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * This class provide utilities function to file.
 *
 * @author Baptiste Wicht
 */
public final class FileUtils {
    private static final Pattern SPECIAL_CHARACTERS = Pattern.compile("[ :\\?|<>']");

    /**
     * Construct a new FileUtils. This class isn't instanciable.
     */
    private FileUtils() {
        super();
    }

    /**
     * Prepare a string to be a file. It seems deleting special characters with normal characters.
     *
     * @param destinationPath The string path to prepare.
     * @return The prepared path.
     */
    public static String prepareFilePath(String destinationPath) {
        String filePath = destinationPath.substring(destinationPath.lastIndexOf(File.separator));

        String prepared = SPECIAL_CHARACTERS.matcher(filePath).replaceAll("_");

        return destinationPath.replace(filePath, prepared);
    }

    /**
     * Return the HTML page's link for the id in the good language.
     *
     * @param id The id of the localised page
     * @return The HTML page's link
     */
    public static URL getLocalisedPage(String id) {
        return FileUtils.class.getClassLoader().
                getResource("org/jtheque/films/i18n/pages/" +
                        id + '_' + Managers.getManager(ILanguageManager.class).
                        getCurrentLocale().getLanguage() + ".html");
    }
}