package org.jtheque.films.view.impl.actions.lend;

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
