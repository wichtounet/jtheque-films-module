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
