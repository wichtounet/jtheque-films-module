package org.jtheque.films.view.impl.actions.lend;

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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.ILendController;
import org.jtheque.films.view.able.ILendFilmView;

import java.awt.event.ActionEvent;

/**
 * Action to validate the lend view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateLendView extends JThequeAction {
    /**
     * Construct a new AcValidateLendView.
     */
    public AcValidateLendView() {
        super("generic.view.actions.validate");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ILendController lendController = Managers.getManager(IBeansManager.class).getBean("lendController");

        ILendFilmView view = lendController.getView();

        if (view.getSelectedBorrower() == null || view.getSelectedFilm() == null) {
            Managers.getManager(IViewManager.class).displayI18nText("view.dialogs.fillall");
        } else {
            lendController.newLending(view.getSelectedBorrower(), view.getSelectedFilm());
            lendController.closeView();
        }
    }
}