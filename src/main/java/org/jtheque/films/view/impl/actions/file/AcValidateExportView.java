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
