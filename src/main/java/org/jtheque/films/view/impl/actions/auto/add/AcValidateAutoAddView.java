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
    @Resource
    private IAutoAddController autoAddController;

    /**
     * Construct a new AcValidateAutoAddView.
     */
    public AcValidateAutoAddView() {
        super("generic.view.actions.ok");

        Managers.getManager(IBeansManager.class).inject(this);
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
