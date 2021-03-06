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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.view.able.IAutoImportView;
import org.jtheque.utils.StringUtils;

import java.awt.event.ActionEvent;

/**
 * Action to delete the selected title from the list.
 *
 * @author Baptiste Wicht
 */
public final class AcDeleteTitle extends JThequeAction {
    /**
     * Create a new AcDeleteTitle action.
     */
    public AcDeleteTitle() {
        super("generic.view.actions.delete");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        IAutoImportView autoImportView = Managers.getManager(IBeansManager.class).getBean("autoImportView");

        String title = autoImportView.getSelectedTitle();

        if (!StringUtils.isEmpty(title)) {
            autoImportView.getModelTitles().removeElement(title);
            autoImportView.getModel().getTitles().remove(title);
        }
    }
}
