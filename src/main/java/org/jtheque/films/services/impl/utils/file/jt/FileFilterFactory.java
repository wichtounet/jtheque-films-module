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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.language.Internationalizable;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.utils.io.SimpleFilter;

/**
 * A factory to obtain file filter for a specific type of file.
 *
 * @author Baptiste Wicht
 */
public final class FileFilterFactory {
    private static final JTFileFilter[] FILTERS;

    static {
        FILTERS = new JTFileFilter[9];

        createFilters();

        Managers.getManager(ILanguageManager.class).addInternationalizable(new FiltersUpdater());
    }

    /**
     * Construct a new FileFilterFactory.
     */
    private FileFilterFactory() {
        super();
    }

    /**
     * Create the filters.
     */
    private static void createFilters() {
        ILanguageManager resources = Managers.getManager(ILanguageManager.class);

        FILTERS[0] = new JTFileFilter(resources.getMessage("utils.filters.html"), ".html", FileType.HTML);
        FILTERS[1] = new JTFileFilter(resources.getMessage("utils.filters.xml"), ".xml", FileType.XML);
        FILTERS[2] = new JTFileFilter(resources.getMessage("utils.filters.xls"), ".xls", FileType.XLS);
        FILTERS[3] = new JTFileFilter(resources.getMessage("utils.filters.txt"), ".txt", FileType.TXT);
        FILTERS[4] = new JTFileFilter(resources.getMessage("utils.filters.pdf"), ".pdf", FileType.PDF);
        FILTERS[5] = new JTFileFilter(resources.getMessage("utils.filters.jtf"), ".jtf", FileType.JTF);
        FILTERS[6] = new JTFileFilter(resources.getMessage("utils.filters.rtf"), ".rtf", FileType.RTF);
        FILTERS[7] = new JTFileFilter(resources.getMessage("utils.filters.csv"), ".csv", FileType.CSV);
        FILTERS[8] = new JTFileFilter(resources.getMessage("utils.filters.jtfe"), ".jtfe", FileType.JTFE);
    }

    /**
     * Return the file filter for this type of file.
     *
     * @param fileType The file type.
     *
     * @return The good filter or <code>null</code> if we doesn't filter an appropriate filter.
     */
    public static SimpleFilter getFileFilter(FileType fileType) {
        JTFileFilter filter = null;

        for (JTFileFilter f : FILTERS) {
            if (f.canFilter(fileType)) {
                filter = f;
                break;
            }
        }

        return filter;
    }

    /**
     * A filters updater.
     *
     * @author Baptiste Wicht
     */
    private static final class FiltersUpdater implements Internationalizable {
        @Override
        public void refreshText() {
            createFilters();
        }
    }
}
