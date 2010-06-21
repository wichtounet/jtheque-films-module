package org.jtheque.films.view.impl.actions.auto.imports;

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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.view.able.IAutoImportView;
import org.jtheque.utils.StringUtils;

import java.awt.event.ActionEvent;

/**
 * Action to add a title to the list of the films who'll be imported.
 *
 * @author Baptiste Wicht
 */
public final class AcAddTitle extends JThequeAction {
    /**
     * Create a new AcAddTitle action.
     */
    public AcAddTitle() {
        super("generic.view.actions.add");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        String title = Managers.getManager(IViewManager.class).
                askUserForText(Managers.getManager(ILanguageManager.class).getMessage("auto.import.input.title"));

        if (!StringUtils.isEmpty(title)) {
            IAutoImportView autoImportView = Managers.getManager(IBeansManager.class).getBean("autoImportView");

            autoImportView.getModelTitles().addElement(title);
            autoImportView.getModel().getTitles().add(title);
        }
    }
}