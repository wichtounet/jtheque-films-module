package org.jtheque.films.view.able;

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

import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.search.Searcher;

/**
 * @author Baptiste Wicht
 */
public interface IExportView extends IWindowView {
    /**
     * Return the search for this export.
     *
     * @return The Search.
     */
    Searcher<Film> getSearcher();

    /**
     * Return the path of the file we must export to.
     *
     * @return The file path.
     */
    String getFilePath();

    /**
     * Stop the wait animation.
     */
    void stopWait();

    /**
     * Start the wait animation.
     */
    void startWait();
}
