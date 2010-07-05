package org.jtheque.films.services.able;

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

import org.jtheque.films.services.impl.utils.Filmography;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.services.able.IPersonService;
import org.jtheque.primary.view.able.fb.IPersonFormBean;

/**
 * A service for the actor functions.
 *
 * @author Baptiste Wicht
 */
public interface IActorService extends IPersonService {
    String DATA_TYPE = "Actors";
    String PERSON_TYPE = "Actor";

    /**
     * Return the filmography of the actor.
     *
     * @param actor The actor.
     *
     * @return The filmography of the actor.
     */
    Filmography getFilmography(Person actor);

    /**
     * Edit the actor.
     *
     * @param actor The actor to edit.
     * @param infos The informations of the view.
     */
    void edit(Person actor, IPersonFormBean infos);
}
