package org.jtheque.films.view.impl.frames;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.view.able.ILendingsView;
import org.jtheque.films.view.impl.actions.lendings.AcReturnCurrentFilm;
import org.jtheque.films.view.impl.editors.DataCellEditor;
import org.jtheque.films.view.impl.menus.JMenuBarLendings;
import org.jtheque.films.view.impl.models.table.LendingsTableModel;
import org.jtheque.primary.od.able.Person;
import org.jtheque.utils.ui.GridBagUtils;

import org.jdesktop.swingx.JXTable;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.Container;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collection;

/**
 * User interface to display lendings.
 *
 * @author Baptiste Wicht
 */
public final class LendingsView extends SwingDialogView implements ILendingsView {
    private static final long serialVersionUID = 6554684772063503187L;

    private LendingsTableModel model;
    private JXTable tableLendings;

    /**
     * Construct a new <code>JFrameLendings</code>.
     *
     * @param parent The parent frame.
     */
    public LendingsView(Frame parent) {
        super(parent);

        build();
    }

    /**
     * Build the view.
     */
    private void build() {
        setJMenuBar(new JMenuBarLendings());
        setTitleKey("lendings.view.title");
        setContentPane(buildContentPane());

        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return the content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new JThequePanelBuilder();

        model = new LendingsTableModel();
        setTableHeader();

        tableLendings = new JXTable(model);
        tableLendings.getColumn(1).setCellEditor(new DataCellEditor(Managers.getManager(IBeansManager.class).<DataContainer<Person>>getBean("filmsService")));
        tableLendings.getColumn(2).setCellEditor(new DataCellEditor(Managers.getManager(IBeansManager.class).<DataContainer<Person>>getBean("borrowersService")));
        tableLendings.getTableHeader().setReorderingAllowed(false);
        tableLendings.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tableLendings.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        tableLendings.setColumnControlVisible(true);
        setIdColumnNotVisible();
        tableLendings.packAll();

        builder.addScrolled(tableLendings, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.BASELINE_LEADING, 0, -1, 1.0, 1.0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.HORIZONTAL, 0, 0),
                new DisplayBeanViewAction("lendings.view.actions.lend", "lendFilmView"), new AcReturnCurrentFilm(),
                new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    public Collection<Integer> getSelectedLendingsID() {
        Collection<Integer> lendings = new ArrayList<Integer>(tableLendings.getSelectedRowCount());

        int[] rows = tableLendings.getSelectedRows();

        if (rows.length > 0) {
            for (int row : rows) {
                lendings.add((Integer) model.getValueAt(tableLendings.convertRowIndexToModel(row), 0));
            }
        }

        return lendings;
    }

    @Override
    public void refreshText() {
        super.refreshText();

        setTableHeader();
        setIdColumnNotVisible();
    }

    /**
     * Set the table header.
     */
    private void setTableHeader() {
        model.setHeader(new String[]{
                getMessage("lendings.view.table.id"),
                getMessage("lendings.view.table.film"),
                getMessage("lendings.view.table.borrower"),
                getMessage("lendings.view.table.date")});
    }

    /**
     * Specify that the column "ID" is not visible.
     */
    private void setIdColumnNotVisible() {
        tableLendings.getColumnExt(getMessage("lendings.view.table.id")).setVisible(false);
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }
}
