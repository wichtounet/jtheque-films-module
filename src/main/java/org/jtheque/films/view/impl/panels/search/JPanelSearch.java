package org.jtheque.films.view.impl.panels.search;

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