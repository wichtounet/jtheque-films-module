package org.jtheque.films.view.able;

import org.jtheque.core.managers.view.able.IWindowView;
import org.jtheque.films.services.impl.utils.file.FTPConnectionInfos;

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
 * A publication view specification.
 *
 * @author Baptiste Wicht
 */
public interface IPublicationView extends IWindowView {
    /**
     * Return the connections infos of the view.
     *
     * @return The connections infos.
     */
    FTPConnectionInfos getConnectionInfos();
}
