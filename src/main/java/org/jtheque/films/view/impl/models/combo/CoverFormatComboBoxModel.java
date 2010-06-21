package org.jtheque.films.view.impl.models.combo;

import org.jtheque.core.managers.view.impl.components.model.SimpleComboBoxModel;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.services.impl.utils.cover.Format;
import org.jtheque.utils.collections.CollectionUtils;

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
public final class CoverFormatComboBoxModel extends SimpleComboBoxModel<Format> {
    /**
     * Construct a new CoverFormatComboBoxModel.
     */
    public CoverFormatComboBoxModel() {
        super();

        ICoverService coverService = CoreUtils.getBean("coverService");

        setElements(coverService.getFormats());
    }

    /**
     * Select the first element.
     */
    public void selectFirst() {
        setSelectedItem(CollectionUtils.first(getObjects()));
    }
}
