package org.jtheque.films.view.impl.actions.lendings;

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
import org.jtheque.films.controllers.able.ILendingsController;
import org.jtheque.films.view.able.ILendingsView;

import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * Action to return the current film.
 *
 * @author Baptiste Wicht
 */
public final class AcReturnCurrentFilm extends JThequeAction {
    /**
     * Construct a new AcReturnCurrentFilm.
     */
    public AcReturnCurrentFilm() {
        super("lendings.view.actions.return");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ILendingsController lendingsController = Managers.getManager(IBeansManager.class).getBean("lendingsController");

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
