package org.jtheque.films.persistence.od.impl;

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

/**
 * A relation between a film and an actor.
 *
 * @author Baptiste Wicht
 */
public final class FilmActorRelation {
    private int theActor;
    private int theFilm;

    /**
     * Return the actor of the relation.
     *
     * @return The actor.
     */
    public int getTheActor() {
        return theActor;
    }

    /**
     * Set the actor of the relation.
     *
     * @param theActor The actor.
     */
    public void setTheActor(int theActor) {
        this.theActor = theActor;
    }

    /**
     * Return the film of the relation.
     *
     * @return The film.
     */
    public int getTheFilm() {
        return theFilm;
    }

    /**
     * Set the film of the relation.
     *
     * @param theFilm The film.
     */
    public void setTheFilm(int theFilm) {
        this.theFilm = theFilm;
    }
}
