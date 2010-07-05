package org.jtheque.films.view.impl.actions.jtheque;

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
import org.jtheque.core.managers.resource.IResourceManager;
import org.jtheque.core.managers.resource.ImageType;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.utils.Constants;
import org.jtheque.primary.view.able.PrincipalDataView;

import java.awt.event.ActionEvent;

/**
 * Action to refresh the list.
 *
 * @author Baptiste Wicht
 */
public final class AcRefreshList extends JThequeAction {
    private static final long serialVersionUID = -6059011169838015671L;

    /**
     * Construct a new AcRefreshList.
     */
    public AcRefreshList() {
        super("jtheque.actions.refresh");

        setIcon(Managers.getManager(IResourceManager.class).getIcon(Constants.IMAGE_BASE_NAME, "refresh", ImageType.PNG));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        PrincipalDataView view = (PrincipalDataView) Managers.getManager(IViewManager.class).getViews().getSelectedView().getComponent();

        view.resort();
    }
}
