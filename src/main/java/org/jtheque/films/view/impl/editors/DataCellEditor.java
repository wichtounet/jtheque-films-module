package org.jtheque.films.view.impl.editors;

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

import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.primary.od.able.Data;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 * A CellEditor for JTable. This editor enable us to display a combo box of datas of dao in a cell of JTable.
 *
 * @author Baptiste Wicht
 */
public final class DataCellEditor extends DefaultCellEditor {
    private static final long serialVersionUID = 7853045852810480944L;

    /**
     * Construct a DataCellEditor for a data container.
     *
     * @param container The container.
     * @param <T>       The type of data.
     */
    public <T extends Data> DataCellEditor(DataContainer<T> container) {
        super(buildCombo(container));
    }

    /**
     * Build a combo box for a data container.
     *
     * @param container The container.
     * @param <T>       The type of data.
     * @return A JCombobox with a model bounded to the data container.
     */
    private static <T extends Data> JComboBox buildCombo(DataContainer<T> container) {
        return new JComboBox(new DataContainerCachedComboBoxModel<T>(container));
    }
}