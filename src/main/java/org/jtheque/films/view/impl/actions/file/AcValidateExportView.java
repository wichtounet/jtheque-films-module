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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IExportController;
import org.jtheque.films.view.able.IExportView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * An action to validate the export view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateExportView extends JThequeAction {
    private static final long serialVersionUID = -1686157263127493029L;

    @Resource
    private IExportController exportController;

    @Resource
    private IExportView exportView;

    /**
     * Construct a new AcValidateExportView.
     */
    public AcValidateExportView() {
        super("generic.view.actions.validate");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (exportView.validateContent()) {
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
            exportView.startWait();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    exportController.export(exportView.getSearcher(), exportView.getFilePath());

                    Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                        @Override
                        public void run() {
                            exportView.stopWait();
                        }
                    });

                    exportController.closeView();
                }
            }).start();
        }
    }
}