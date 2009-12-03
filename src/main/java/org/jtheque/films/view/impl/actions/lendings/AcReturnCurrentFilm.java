package org.jtheque.films.view.impl.actions.lendings;

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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.ILendingsController;
import org.jtheque.films.view.able.ILendingsView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * Action to return the current film.
 *
 * @author Baptiste Wicht
 */
public final class AcReturnCurrentFilm extends JThequeAction {
    private static final long serialVersionUID = 1235248610981426905L;

    @Resource
    private ILendingsController lendingsController;

    /**
     * Construct a new AcReturnCurrentFilm.
     */
    public AcReturnCurrentFilm() {
        super("lendings.view.actions.return");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ILendingsView view = (ILendingsView) lendingsController.getView();

        Collection<Integer> ids = view.getSelectedLendingsID();

        if (ids.isEmpty()) {
            Managers.getManager(IViewManager.class).displayI18nText("lendings.dialogs.selectLending");
        } else {
            for (int id : ids) {
                lendingsController.deleteLending(id);
            }
        }
    }
}