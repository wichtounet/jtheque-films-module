package org.jtheque.films.view.impl.actions.film;

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
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.films.view.able.IInfosActorsView;
import org.jtheque.utils.collections.ArrayUtils;

import javax.swing.DefaultListModel;

import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Action to remove an actor from the list.
 *
 * @author Baptiste Wicht
 */
public final class AcRemoveActorFromList extends JThequeSimpleAction {
    /**
     * Construct a new <code>AcRemoveActorFromList</code>.
     */
    public AcRemoveActorFromList() {
        super();

        setText(" << ");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IInfosActorsView actorsView = Managers.getManager(IBeansManager.class).getBean("filmActorsView");

        DefaultListModel modelActors = actorsView.getActorsModel();
        DefaultListModel modelActorsForFilm = actorsView.getActorsForFilmModel();

        int[] selectedActors = actorsView.getSelectedActorsForFilmIndexes();

        Arrays.sort(selectedActors);

        ArrayUtils.reverse(selectedActors);

        for (int index : selectedActors) {
            Object actor = modelActorsForFilm.remove(index);
            modelActors.addElement(actor);
        }
    }
}