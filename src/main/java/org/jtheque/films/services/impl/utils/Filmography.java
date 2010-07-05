package org.jtheque.films.services.impl.utils;

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

import java.util.ArrayList;
import java.util.Collection;

/**
 * A filmography. A filmography contain an actor and a list of films in which the actor has played.
 *
 * @author Baptiste Wicht
 */
public final class Filmography {
    /**
     * The String representation of the actor.
     */
    private String actor;

    /**
     * The films on which the actors has played.
     */
    private Collection<String> films;

    /**
     * Return the films of the filmography.
     *
     * @return A List containing all the films of the filmography.
     */
    public Iterable<String> getFilms() {
        return films;
    }

    /**
     * Set the films of the filmography.
     *
     * @param films The new List of films to set
     */
    public void setFilms(Collection<String> films) {
        this.films = new ArrayList<String>(films);
    }

    /**
     * Set the actor of the filmography.
     *
     * @param actor The new actor to set.
     */
    public void setActor(String actor) {
        this.actor = actor;
    }

    /**
     * Return the actor of the filmography.
     *
     * @return The actor.
     */
    public String getActor() {
        return actor;
    }
}
