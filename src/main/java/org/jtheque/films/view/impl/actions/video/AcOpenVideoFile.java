package org.jtheque.films.view.impl.actions.video;

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.services.impl.utils.VideoFile;
import org.jtheque.films.view.able.IVideoView;
import org.jtheque.utils.DesktopUtils;

import java.awt.event.ActionEvent;
import java.io.File;

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
 * An action to open a video file.
 *
 * @author Baptiste Wicht
 */
public final class AcOpenVideoFile extends JThequeAction {
    /**
     * Construct a new AcOpenVideoFile.
     */
    public AcOpenVideoFile() {
        super("video.view.actions.open");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        VideoFile file = Managers.getManager(IBeansManager.class).<IVideoView>getBean("videoView").getSelectedFile();

        DesktopUtils.open(new File(file.getFilm().getFilePath()));
    }
}
