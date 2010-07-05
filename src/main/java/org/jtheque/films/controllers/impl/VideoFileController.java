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
import org.jtheque.films.controllers.able.IVideoFileController;
import org.jtheque.films.view.able.IVideoFileView;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Baptiste Wicht
 */
@Component
public final class VideoFileController extends AbstractController implements IVideoFileController {
    @Resource
    private IVideoFileView videoFileView;

    @Override
    public IVideoFileView getView() {
        return videoFileView;
    }
}
