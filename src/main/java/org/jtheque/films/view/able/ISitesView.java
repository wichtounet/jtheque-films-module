package org.jtheque.films.view.able;

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

import org.jtheque.core.managers.view.able.IWindowView;

import javax.swing.JTextPane;

/**
 * @author Baptiste Wicht
 */
public interface ISitesView extends IWindowView {
    /**
     * Return the component used to display the informations about the sites.
     *
     * @return The text component.
     */
    JTextPane getSitesTextComponent();

    /**
     * Return the selected site.
     *
     * @return The selected site.
     */
    String getSelectedSite();
}
