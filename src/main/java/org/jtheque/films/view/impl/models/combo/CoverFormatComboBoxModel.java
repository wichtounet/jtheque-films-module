package org.jtheque.films.view.impl.models.combo;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.services.impl.utils.cover.Format;

import javax.annotation.Resource;
import javax.swing.DefaultComboBoxModel;

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

/**
 * A combo box model to display a list of format.
 *
 * @author Baptiste Wicht
 */
public final class CoverFormatComboBoxModel extends DefaultComboBoxModel {
    @Resource
    private ICoverService coverService;

    /**
     * Construct a new CoverFormatComboBoxModel.
     */
    public CoverFormatComboBoxModel() {
        super();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public Object getElementAt(int index) {
        return coverService.getFormats()[index];
    }

    @Override
    public int getSize() {
        return coverService.getFormats().length;
    }

    /**
     * Return the selected format in the model.
     *
     * @return The format who's selected.
     */
    public Format getSelectedFormat() {
        return (Format) getSelectedItem();
    }

    /**
     * Select the first element.
     */
    public void selectFirst() {
        setSelectedItem(coverService.getFormats()[0]);
    }
}
