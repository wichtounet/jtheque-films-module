package org.jtheque.films.view.impl.actions.actor;

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
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IActorController;

import java.awt.event.ActionEvent;

/**
 * Action to create a new actor.
 *
 * @author Baptiste Wicht
 */
public final class AcNewActor extends JThequeAction {
    /**
     * Construct a new AcNewActor.
     */
    public AcNewActor() {
        super("generic.view.actions.new");
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Managers.getManager(IBeansManager.class).<IActorController>getBean("actorController").create();
    }
}
