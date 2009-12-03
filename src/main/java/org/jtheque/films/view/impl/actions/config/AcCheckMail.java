package org.jtheque.films.view.impl.actions.config;

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
import org.jtheque.core.managers.view.able.IConfigView;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.films.view.impl.panels.config.JPanelConfigLendings;

import java.awt.event.ActionEvent;

/**
 * Action executed when the checkbox mail is checked.
 *
 * @author Baptiste Wicht
 */
public final class AcCheckMail extends JThequeSimpleAction {
    private static final long serialVersionUID = 5921514083536717030L;

    @Override
    public void actionPerformed(ActionEvent e) {
        IConfigView view = Managers.getManager(IViewManager.class).getViews().getConfigView();

        JPanelConfigLendings config = (JPanelConfigLendings) view.getSelectedPanelConfig();

        boolean selected = config.getBoxSendMail().isSelected();
        config.setMailEnabled(selected);
    }
}