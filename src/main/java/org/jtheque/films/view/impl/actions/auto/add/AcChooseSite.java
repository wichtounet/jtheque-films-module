package org.jtheque.films.view.impl.actions.auto.add;

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
import org.jtheque.core.managers.view.impl.actions.JThequeSimpleAction;
import org.jtheque.films.services.able.IFilmAutoService;
import org.jtheque.films.services.impl.utils.web.FilmResult;
import org.jtheque.films.view.able.IAutoAddView;

import javax.annotation.Resource;

import java.awt.event.ActionEvent;
import java.util.Collection;

/**
 * Action to choose the site.
 *
 * @author Baptiste Wicht
 */
public final class AcChooseSite extends JThequeSimpleAction {
    @Resource
    private IFilmAutoService filmAutoService;

    @Resource
    private IAutoAddView autoAddView;

    /**
     * Construct a new <code>AcChooseSite</code>.
     */
    public AcChooseSite() {
        super();

        setText(">>");

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (autoAddView.validateContent(IAutoAddView.PHASE_1)) {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoAddView.startWait();

                    new Thread(new ChooseSiteRunnable()).start();
                }
            });
        }
    }

    /**
     * A runnable for choose site.
     *
     * @author Baptiste Wicht
     */
    private final class ChooseSiteRunnable implements Runnable {
        @Override
        public void run() {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoAddView.getModelListFilms().clear();
                }
            });

            final Collection<FilmResult> results = filmAutoService.getFilms(
                    autoAddView.getSelectedSite(),
                    autoAddView.getFieldTitle().getText());

            for (final FilmResult result : results) {
                Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                    @Override
                    public void run() {
                        autoAddView.getModelListFilms().addElement(result.getTitre());
                    }
                });
            }

            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    autoAddView.getModel().setResults(results);
                }
            });

            autoAddView.stopWait();
        }
    }
}
