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

import org.jtheque.core.managers.view.able.controller.AbstractController;
import org.jtheque.films.controllers.able.ICoverController;
import org.jtheque.films.view.able.ICoverView;

import javax.annotation.Resource;

/**
 * A cover controller implementation.
 *
 * @author Baptiste Wicht
 */
public final class CoverController extends AbstractController implements ICoverController {
    @Resource
    private ICoverView coverView;

    @Override
    public ICoverView getView() {
        return coverView;
    }
}
