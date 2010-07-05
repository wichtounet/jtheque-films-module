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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.managers.view.impl.components.model.SimpleListModel;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.IInfosActorsView;
import org.jtheque.films.view.impl.actions.film.AcAddActorToList;
import org.jtheque.films.view.impl.actions.film.AcRemoveActorFromList;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.list.DataCachedContainerListModel;
import org.jtheque.primary.od.able.Person;
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
 * Panel to configure the actors of the film.
 *
 * @author Baptiste Wicht
 */
public final class JPanelInfosActors extends JPanel implements IInfosActorsView {
    private final JButton buttonAdd;
    private final JButton buttonRemove;

    private final JList listActors;
    private final JList listActorsForFilm;

    private final DataCachedContainerListModel<Person> actorsModel;
    private final SimpleListModel<Person> actorsForFilmModel;

    private static final double AN_HALF = 0.5;

    /**
     * Construct a new JPanelInfosActors.
     */
    public JPanelInfosActors() {
        super();

        PanelBuilder builder = new JThequePanelBuilder(this);

        actorsModel = new DataCachedContainerListModel<Person>(
                Managers.getManager(IBeansManager.class).<DataContainer<Person>>getBean("actorService"));

        listActors = builder.addScrolledList(actorsModel, null,
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 1, 0, AN_HALF, 1.0));
        listActors.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        actorsForFilmModel = new SimpleListModel<Person>();

        listActorsForFilm = builder.addScrolledList(actorsForFilmModel, null,
                builder.gbcSet(2, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, AN_HALF, 1.0));
        listActorsForFilm.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        buttonAdd = builder.addButton(new AcAddActorToList(), builder.gbcSet(1, 0));
        buttonAdd.setEnabled(false);

        buttonRemove = builder.addButton(new AcRemoveActorFromList(), builder.gbcSet(1, 1));
        buttonRemove.setEnabled(false);
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        actorsForFilmModel.removeAllElements();
        actorsModel.reload();

        for (Person actor : ((Film) event.getObject()).getActors()) {
            actorsModel.removeElement(actor);
            actorsForFilmModel.addElement(actor);
        }
    }

    @Override
    public void fillFilm(IFilmFormBean fb) {
        if (actorsForFilmModel.getSize() != 0) {
            fb.setActors(new HashSet<Person>(actorsForFilmModel.getObjects()));
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        buttonAdd.setEnabled(enabled);
        buttonRemove.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    @Override
    public JComponent getImpl() {
        return this;
    }

    @Override
    public DefaultListModel getActorsModel() {
        return actorsModel;
    }

    @Override
    public DefaultListModel getActorsForFilmModel() {
        return actorsForFilmModel;
    }

    @Override
    public int[] getSelectedActorsIndexes() {
        return listActors.getSelectedIndices();
    }

    @Override
    public int[] getSelectedActorsForFilmIndexes() {
        return listActorsForFilm.getSelectedIndices();
    }

    @Override
    public void validate(Collection<JThequeError> errors) {
    }
}
