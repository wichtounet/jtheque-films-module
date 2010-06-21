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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IExportController;
import org.jtheque.films.view.able.IExportView;

import java.awt.event.ActionEvent;

/**
 * An action to validate the export view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateExportView extends JThequeAction {
    /**
     * Construct a new AcValidateExportView.
     */
    public AcValidateExportView() {
        super("generic.view.actions.validate");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (Managers.getManager(IBeansManager.class).<IExportView>getBean("exportView").validateContent()) {
            Managers.getManager(IViewManager.class).execute(new ExportTask());
        }
    }

    /**
     * A task to export the data.
     *
     * @author Baptiste Wicht
     */
    private final class ExportTask extends SimpleTask {
        @Override
        public void run() {
            final IExportController exportController = Managers.getManager(IBeansManager.class).getBean("exportController");

            exportController.getView().startWait();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    exportController.export(exportController.getView().getSearcher(), exportController.getView().getFilePath());

                    Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                        @Override
                        public void run() {
                            exportController.getView().stopWait();
                        }
                    });

                    exportController.closeView();
                }
            }).start();
        }
    }
}