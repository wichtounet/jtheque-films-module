package org.jtheque.films.controllers.impl;

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

import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.Logger;
import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.ISitesController;
import org.jtheque.films.utils.FileUtils;
import org.jtheque.films.view.able.ISitesView;

import javax.annotation.Resource;
import javax.swing.event.ListSelectionEvent;

import java.io.IOException;

/**
 * A sites controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class SitesController extends AbstractController implements ISitesController {
    @Resource
    private ISitesView sitesView;

    @Logger
    private IJThequeLogger logger;

    @Override
    public ISitesView getView() {
        return sitesView;
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        try {
            sitesView.getSitesTextComponent().setPage(FileUtils.getLocalisedPage(
                    sitesView.getSelectedSite()));
        } catch (IOException e1) {
            sitesView.getSitesTextComponent().setText("");
            logger.error(e1);
        }
    }
}
