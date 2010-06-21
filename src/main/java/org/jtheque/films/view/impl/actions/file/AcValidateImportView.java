package org.jtheque.films.view.impl.actions.file;

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