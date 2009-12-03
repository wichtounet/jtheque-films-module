package org.jtheque.films.view.impl.panels;

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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.persistence.able.DataContainerProvider;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.IInfosKindsView;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.list.DataCachedContainerListModel;
import org.jtheque.films.view.impl.models.list.SimpleModel;
import org.jtheque.primary.od.able.Kind;
import org.jtheque.primary.services.able.IKindsService;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Baptiste Wicht
 */
public final class JPanelInfosKinds extends JPanel implements DataListener, IInfosKindsView {
    private static final long serialVersionUID = 447863972139498415L;

    private JButton buttonAdd;
    private JButton buttonRemove;
    private JButton buttonNew;
    private JList listKinds;
    private JList listKindsForFilm;

    private DataCachedContainerListModel<Kind> kindsModel;
    private SimpleModel<Kind> kindsForFilmModel;

    private JThequeSimpleAction removeAction;
    private JThequeSimpleAction addAction;
    private Action newAction;

    @Resource
    private IKindsService kindsService;
    private static final double AN_HALF = 0.5;

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
        PanelBuilder builder = new PanelBuilder(this);

        kindsModel = new DataCachedContainerListModel<Kind>(kindsService);

        DataContainerProvider.getInstance().getContainerForDataType(IKindsService.DATA_TYPE).addDataListener(this);

        listKinds = builder.addList(kindsModel, null,
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 1, 0, AN_HALF, 1.0));
        listKinds.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        buttonAdd = builder.addButton(addAction, builder.gbcSet(1, 0));
        buttonAdd.setEnabled(false);

        buttonRemove = builder.addButton(removeAction, builder.gbcSet(1, 1));
        buttonRemove.setEnabled(false);

        buttonNew = builder.addButton(newAction, builder.gbcSet(1, 2));
        buttonNew.setEnabled(false);

        kindsForFilmModel = new SimpleModel<Kind>();

        listKindsForFilm = builder.addList(kindsForFilmModel, null,
                builder.gbcSet(2, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 1, 0, AN_HALF, 1.0));
        listKindsForFilm.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        kindsForFilmModel.removeAllElements();
        kindsModel.reload();

        for (Kind kind : ((Film) event.getObject()).getKinds()) {
            kindsModel.removeElement(kind);
            kindsForFilmModel.addElement(kind);
        }
    }

    @Override
    public void fillFilm(IFilmFormBean fb) {
        if (kindsForFilmModel.getSize() != 0) {
            fb.setKinds(new HashSet<Kind>(kindsForFilmModel.getObjects()));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        buttonAdd.setEnabled(enabled);
        buttonRemove.setEnabled(enabled);
        buttonNew.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    @Override
    public DefaultListModel getKindsModel() {
        return kindsModel;
    }

    @Override
    public DefaultListModel getKindsForFilmModel() {
        return kindsForFilmModel;
    }

    @Override
    public int[] getSelectedKindsIndexes() {
        return listKinds.getSelectedIndices();
    }

    @Override
    public int[] getSelectedKindsForFilmIndexes() {
        return listKindsForFilm.getSelectedIndices();
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
    }

    @Override
    public void dataChanged() {
        for (Kind o : kindsForFilmModel.getObjects()) {
            kindsModel.removeElement(o);
        }
    }

    /**
     * Set the action to remove a kind.
     *
     * @param removeAction The action to remove a kind.
     */
    public void setRemoveAction(JThequeSimpleAction removeAction) {
        this.removeAction = removeAction;
    }

    /**
     * Set the action to add a kind.
     *
     * @param addAction The action to add a kind.
     */
    public void setAddAction(JThequeSimpleAction addAction) {
        this.addAction = addAction;
    }

    /**
     * Set the action to create a new kind.
     *
     * @param newAction The action to remove a new kind.
     */
    public void setNewAction(Action newAction) {
        this.newAction = newAction;
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}