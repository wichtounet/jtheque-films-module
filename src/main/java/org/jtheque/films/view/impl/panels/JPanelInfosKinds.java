package org.jtheque.films.view.impl.panels;

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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.persistence.able.DataContainerProvider;
import org.jtheque.core.managers.persistence.able.DataListener;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.core.utils.CoreUtils;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.IInfosKindsView;
import org.jtheque.films.view.impl.actions.film.AcAddKindToList;
import org.jtheque.films.view.impl.actions.film.AcRemoveKindFromList;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.list.DataCachedContainerListModel;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.view.impl.actions.principal.CreateNewPrincipalAction;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.utils.ui.GridBagUtils;

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
    private final JButton buttonAdd;
    private final JButton buttonRemove;
    private final JButton buttonNew;

    private final JList listKinds;
    private final JList listKindsForFilm;

    private final DataCachedContainerListModel<SimpleData> kindsModel;
    private final SimpleListModel<SimpleData> kindsForFilmModel;

    private static final double AN_HALF = 0.5;

    public JPanelInfosKinds() {
        super();

        PanelBuilder builder = new JThequePanelBuilder(this);

        kindsModel = new DataCachedContainerListModel<SimpleData>(CoreUtils.<DataContainer<SimpleData>>getBean("kindsService"));

        DataContainerProvider.getInstance().getContainerForDataType(SimpleData.DataType.KIND.getDataType()).addDataListener(this);

        listKinds = builder.addScrolledList(kindsModel, null,
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 1, 0, AN_HALF, 1.0));
        listKinds.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        buttonAdd = builder.addButton(new AcAddKindToList(), builder.gbcSet(1, 0));
        buttonAdd.setEnabled(false);

        buttonRemove = builder.addButton(new AcRemoveKindFromList(), builder.gbcSet(1, 1));
        buttonRemove.setEnabled(false);

        buttonNew = builder.addButton(new CreateNewPrincipalAction("generic.view.actions.new", "typeController"), builder.gbcSet(1, 2));
        buttonNew.setEnabled(false);

        kindsForFilmModel = new SimpleListModel<SimpleData>();

        listKindsForFilm = builder.addScrolledList(kindsForFilmModel, null,
                builder.gbcSet(2, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 1, 0, AN_HALF, 1.0));
        listKindsForFilm.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        kindsForFilmModel.removeAllElements();
        kindsModel.reload();

        for (SimpleData kind : ((Film) event.getObject()).getKinds()) {
            kindsModel.removeElement(kind);
            kindsForFilmModel.addElement(kind);
        }
    }

    @Override
    public void fillFilm(IFilmFormBean fb) {
        if (kindsForFilmModel.getSize() != 0) {
            fb.setKinds(new HashSet<SimpleData>(kindsForFilmModel.getObjects()));
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
        for (SimpleData o : kindsForFilmModel.getObjects()) {
            kindsModel.removeElement(o);
        }
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}
