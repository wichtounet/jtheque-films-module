package org.jtheque.films.view.impl.actions.auto.imports;

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
