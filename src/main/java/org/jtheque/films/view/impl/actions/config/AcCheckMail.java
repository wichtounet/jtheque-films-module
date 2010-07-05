package org.jtheque.films.view.impl.actions.config;

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
        JPanelConfigLendings config = (JPanelConfigLendings) Managers.getManager(IViewManager.class).getViews().getConfigView().getSelectedPanelConfig();

        boolean selected = config.getBoxSendMail().isSelected();
        config.setMailEnabled(selected);
    }
}
