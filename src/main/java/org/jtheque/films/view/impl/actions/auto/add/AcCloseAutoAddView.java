package org.jtheque.films.view.impl.actions.auto.add;

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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.view.able.IAutoAddView;

import java.awt.event.ActionEvent;

/**
 * This close close the auto add view.
 *
 * @author Baptiste Wicht
 */
public final class AcCloseAutoAddView extends JThequeAction {
    /**
     * Construct a new AcCloseAutoAddView.
     */
    public AcCloseAutoAddView() {
        super("generic.view.actions.cancel");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Managers.getManager(IBeansManager.class).<IAutoAddView>getBean("autoAddView").getModelListFilms().clear();
        Managers.getManager(IBeansManager.class).<IAutoAddView>getBean("autoAddView").closeDown();
    }
}