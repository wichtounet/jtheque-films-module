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

import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.films.view.able.IFilmView;
import org.jtheque.utils.collections.ArrayUtils;

import javax.annotation.Resource;
import javax.swing.DefaultListModel;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * Action to add an actor to the list.
 *
 * @author Baptiste Wicht
 */
public final class AcAddActorToList extends JThequeSimpleAction {
    private static final long serialVersionUID = -1335864379997427135L;

    /**
     * Construct a new AcAddActorToList.
     */
    public AcAddActorToList() {
        super();

        setText(" >> ");
    }

    @Resource
    private IFilmView filmView;

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel modelActors = filmView.getPanelActors().getActorsModel();
        DefaultListModel modelActorsForFilm = filmView.getPanelActors().getActorsForFilmModel();

        int[] selectedActors = filmView.getPanelActors().getSelectedActorsIndexes();

        Arrays.sort(selectedActors);

        ArrayUtils.reverse(selectedActors);

        for (int index : selectedActors) {
            Object actor = modelActors.remove(index);
            modelActorsForFilm.addElement(actor);
        }
    }
}