package org.jtheque.films.view.impl.models.combo;

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

import org.jtheque.films.utils.Constants.Site;

import javax.swing.DefaultComboBoxModel;

/**
 * A combo box model for sites.
 *
 * @author Baptiste Wicht
 */
public final class SitesComboBoxModel extends DefaultComboBoxModel {
    private static final long serialVersionUID = 229679641284881689L;

    private final Site[] sites;

    /**
     * Construct a new SitesComboBoxModel and fill it with the available sites.
     */
    public SitesComboBoxModel() {
        super();

        sites = Site.values();
    }

    @Override
    public Object getElementAt(int index) {
        return sites[index];
    }

    @Override
    public int getSize() {
        return sites.length;
    }

    /**
     * Return the selected site.
     *
     * @return The selected site.
     */
    public Site getSelectedSite() {
        return (Site) getSelectedItem();
    }
}