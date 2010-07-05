package org.jtheque.films.view.impl.panels.search;

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

import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.primary.od.able.Data;

import javax.swing.JPanel;

/**
 * Represents a panel of research.
 *
 * @author Baptiste Wicht
 */
public abstract class JPanelSearch extends JPanel {
    private static final long serialVersionUID = -695899761548455194L;

    /**
     * Return the title of the panel.
     *
     * @return The title
     */
    public abstract String getTitle();

    /**
     * Build the search with the content of the panel.
     *
     * @return The search
     */
    public abstract Searcher<? extends Data> getSearcher();
}
