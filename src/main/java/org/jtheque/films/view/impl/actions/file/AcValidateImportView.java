package org.jtheque.films.view.impl.actions.file;

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
import org.jtheque.core.managers.error.IErrorManager;
import org.jtheque.core.managers.error.InternationalizedError;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IImportController;
import org.jtheque.utils.io.FileException;

import java.awt.event.ActionEvent;

/**
 * An action to validate the import view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateImportView extends JThequeAction {
    /**
     * Construct a new AcValidateImportView.
     */
    public AcValidateImportView() {
        super("generic.view.actions.validate");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IImportController importController = Managers.getManager(IBeansManager.class).getBean("importController");

        try {
            importController.importData(importController.getView().getFilePath());
        } catch (FileException e1) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e1);
            Managers.getManager(IErrorManager.class).addError(new InternationalizedError(
                    "export.errors.file.filenotfound.title",
                    "export.errors.file.filenotfound" + importController.getView().getFilePath()));
        }

        importController.closeView();
    }
}
