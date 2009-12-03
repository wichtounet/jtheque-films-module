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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.able.IAutoImportService;
import org.jtheque.films.view.able.IAutoImportView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Collection;

/**
 * Action to search film titles from a folder.
 *
 * @author Baptiste Wicht
 */
public final class AcSearchTitles extends JThequeAction {
    private static final long serialVersionUID = -8086054102467874235L;

    @Resource
    private IAutoImportService service;

    @Resource
    private IAutoImportView autoImportView;

    /**
     * Create a new AcSearchTitles action.
     */
    public AcSearchTitles() {
        super("generic.view.actions.search");
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (autoImportView.validateContent(IAutoImportView.PHASE_1)) {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoImportView.startWait();

                    new Thread(new SearchTitlesRunnable()).start();
                }
            });
        }
    }

    /**
     * A runnable for search titles.
     *
     * @author Baptiste Wicht
     */
    private final class SearchTitlesRunnable implements Runnable {
        @Override
        public void run() {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoImportView.getModelTitles().clear();
                }
            });

            final Collection<String> titles = service.getFilmTitles(new File(autoImportView.getFolderPath()), autoImportView.isFileMode());

            for (final String title : titles) {
                Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                    @Override
                    public void run() {
                        autoImportView.getModelTitles().addElement(title);
                    }
                });
            }

            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoImportView.getModel().setTitles(titles);
                    autoImportView.stopWait();
                }
            });
        }
    }
}