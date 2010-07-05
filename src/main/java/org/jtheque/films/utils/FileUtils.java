package org.jtheque.films.utils;

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
     *
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
     *
     * @return The HTML page's link
     */
    public static URL getLocalisedPage(String id) {
        return FileUtils.class.getClassLoader().
                getResource("org/jtheque/films/i18n/pages/" +
                        id + '_' + Managers.getManager(ILanguageManager.class).
                        getCurrentLocale().getLanguage() + ".html");
    }
}
