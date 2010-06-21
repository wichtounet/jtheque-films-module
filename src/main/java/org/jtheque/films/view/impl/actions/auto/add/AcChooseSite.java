package org.jtheque.films.view.impl.actions.auto.add;

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