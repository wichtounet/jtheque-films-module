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

import org.jtheque.core.managers.view.able.components.TabComponent;
import org.jtheque.primary.view.able.PrincipalDataView;
import org.jtheque.primary.view.able.fb.IPersonFormBean;
import org.jtheque.primary.view.impl.listeners.CurrentObjectListener;

/**
 * A realizer view specification.
 *
 * @author Baptiste Wicht
 */
public interface IRealizerView extends PrincipalDataView, CurrentObjectListener, TabComponent {
    /**
     * Fill a <code>RealizerFormBean</code> with the infos in the interface.
     *
     * @return The filled <code>RealizerFormBean</code>.
     */
    IPersonFormBean fillPersonFormBean();
}
