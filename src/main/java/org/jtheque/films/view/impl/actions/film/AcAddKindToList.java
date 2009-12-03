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
public final class AcAddKindToList extends JThequeSimpleAction {
    private static final long serialVersionUID = -1335864379997427135L;

    @Resource
    private IFilmView filmView;

    /**
     * Construct a new <code>AcAddKindToList</code>.
     */
    public AcAddKindToList() {
        super();

        setText(">>");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DefaultListModel kindsModel = filmView.getPanelKinds().getKindsModel();
        DefaultListModel kindsForFilmModel = filmView.getPanelKinds().getKindsForFilmModel();

        int[] selectedKinds = filmView.getPanelKinds().getSelectedKindsIndexes();

        Arrays.sort(selectedKinds);

        ArrayUtils.reverse(selectedKinds);

        for (int index : selectedKinds) {
            Object kind = kindsModel.remove(index);
            kindsForFilmModel.addElement(kind);
        }
    }
}