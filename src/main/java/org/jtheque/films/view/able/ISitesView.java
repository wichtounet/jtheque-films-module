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

import javax.swing.JTextPane;

/**
 * @author Baptiste Wicht
 */
public interface ISitesView extends IWindowView {
    /**
     * Return the component used to display the informations about the sites.
     *
     * @return The text component.
     */
    JTextPane getSitesTextComponent();

    /**
     * Return the selected site.
     *
     * @return The selected site.
     */
    String getSelectedSite();
}
