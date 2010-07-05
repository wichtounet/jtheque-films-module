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
    @Resource
    private IAutoImportService service;

    @Resource
    private IAutoImportView autoImportView;

    /**
     * Create a new AcSearchTitles action.
     */
    public AcSearchTitles() {
        super("generic.view.actions.search");

        Managers.getManager(IBeansManager.class).inject(this);
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
