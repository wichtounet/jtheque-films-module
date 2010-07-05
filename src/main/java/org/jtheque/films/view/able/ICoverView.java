package org.jtheque.films.view.able;

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

import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.cover.Format;

import java.awt.Image;

/**
 * A cover view specification.
 *
 * @author Baptiste Wicht
 */
public interface ICoverView extends IWindowView {
    /**
     * Display the image.
     *
     * @param image The image to display.
     */
    void display(Image image);

    /**
     * Return the selected film.
     *
     * @return The film to select.
     */
    Film getSelectedFilm();

    /**
     * Return the selected format.
     *
     * @return The selected format.
     */
    Format getSelectedFormat();
}
