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
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.edt.SimpleTask;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IAutoAddController;
import org.jtheque.films.view.able.IAutoAddView;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/**
 * Action to validate the auto add view.
 *
 * @author Baptiste Wicht
 */
public final class AcValidateAutoAddView extends JThequeAction {
    private static final long serialVersionUID = -859591053047928367L;

    @Resource
    private IAutoAddController autoAddController;

    /**
     * Construct a new AcValidateAutoAddView.
     */
    public AcValidateAutoAddView() {
        super("generic.view.actions.ok");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        final IAutoAddView view = autoAddController.getView();

        if (view.validateContent(IAutoAddView.PHASE_2)) {
            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    view.startWait();

                    new Thread(new AutoAddRunnable(autoAddController)).start();
                }
            });
        }
    }

    /**
     * A runnable to launch the auto add process.
     *
     * @author Baptiste Wicht
     */
    private static final class AutoAddRunnable implements Runnable {
        private final IAutoAddController controller;

        /**
         * Create a new AutoAddRunnable.
         *
         * @param controller The controller to manage.
         */
        AutoAddRunnable(IAutoAddController controller) {
            super();

            this.controller = controller;
        }

        @Override
        public void run() {
            controller.auto(controller.getView().getSelectedFilm());

            Managers.getManager(IViewManager.class).execute(new SimpleTask() {
                @Override
                public void run() {
                    controller.getView().stopWait();
                    controller.getView().closeDown();
                }
            });
        }
    }
}