package org.jtheque.films.view.impl.editors;

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
     *
     * @return A JCombobox with a model bounded to the data container.
     */
    private static <T extends Data> JComboBox buildCombo(DataContainer<T> container) {
        return new JComboBox(new DataContainerCachedComboBoxModel<T>(container));
    }
}
