package org.jtheque.films.services.impl.utils.web;

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

import org.jtheque.films.utils.Constants.Site;

/**
 * A result of film search on site.
 *
 * @author Baptiste Wicht
 */
public final class FilmResult {
    private String index;
    private String titre;
    private Site site;

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param titre the titre to set
     */
    public void setTitre(String titre) {
        this.titre = titre;
    }

    /**
     * @return the titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * Set the site.
     *
     * @param site The site.
     */
    public void setSite(Site site) {
        this.site = site;
    }

    /**
     * Return the site of the result.
     *
     * @return The site of the result.
     */
    public Site getSite() {
        return site;
    }
}
