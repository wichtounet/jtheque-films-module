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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IAutoImportService;
import org.jtheque.films.services.able.IFilmsService;
import org.jtheque.films.view.able.IAutoImportView;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * Action to validate the auto import process.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateAutoImportView extends JThequeAction {
    @Resource
    private IAutoImportService service;

    @Resource
    private IFilmsService filmsService;

    @Resource
    private IAutoImportView autoImportView;

    /**
     * Create a new AcValidateAutoImportView action.
     */
    public AcValidateAutoImportView() {
        super("auto.import.view.actions.import");

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (autoImportView.validateContent(IAutoImportView.PHASE_2)) {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoImportView.startWait();

                    new Thread(new ImportFilmsRunnable()).start();
                }
            });
        }
    }

    /**
     * A runnable for search titles.
     *
     * @author Baptiste Wicht
     */
    private final class ImportFilmsRunnable implements Runnable {
        @Override
        public void run() {
            Collection<Film> films = service.importFilms(autoImportView.getModel().getTitles(), autoImportView.isWebMode(), autoImportView.getSelectedSite());

            filmsService.createAll(films);

            autoImportView.stopWait();
        }
    }
}