package org.jtheque.films.controllers.impl;

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.IPublicationController;
import org.jtheque.films.view.able.IPublicationView;

import javax.annotation.Resource;

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
 * @author Baptiste Wicht
 */
public final class PublicationController extends AbstractController implements IPublicationController {
    @Resource
    private IPublicationView publicationView;

    @Override
    public IPublicationView getView() {
        return publicationView;
    }
}
