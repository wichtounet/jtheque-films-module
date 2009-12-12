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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.persistence.able.DataContainer;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.view.able.IInfosActorsView;
import org.jtheque.films.view.impl.actions.film.AcAddActorToList;
import org.jtheque.films.view.impl.actions.film.AcRemoveActorFromList;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.films.view.impl.models.list.DataCachedContainerListModel;
import org.jtheque.films.view.impl.models.list.SimpleModel;
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
    private final SimpleModel<Person> actorsForFilmModel;

    private static final double AN_HALF = 0.5;

    /**
     * Construct a new JPanelInfosActors.
     * 
     */
    public JPanelInfosActors(){
        super();

        PanelBuilder builder = new PanelBuilder(this);

        actorsModel = new DataCachedContainerListModel<Person>(
                Managers.getManager(IBeansManager.class).<DataContainer<Person>>getBean("actorService"));

        listActors = builder.addList(actorsModel, null,
                builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 1, 0, AN_HALF, 1.0));
        listActors.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        actorsForFilmModel = new SimpleModel<Person>();

        listActorsForFilm = builder.addList(actorsForFilmModel, null,
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