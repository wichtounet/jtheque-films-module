package org.jtheque.films.view.impl.actions.video;

import org.jtheque.core.managers.view.impl.actions.JThequeAction;
import org.jtheque.films.controllers.able.IVideoFileController;

import javax.annotation.Resource;
import java.awt.event.ActionEvent;

/*
 * This file is part of JTheque.
 *
 * JTheque is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 *
 * JTheque is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTheque.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * An action to open the video file view.
 *
 * @author Baptiste Wicht
 */
public final class AcNewVideoFile extends JThequeAction {
    @Resource
    private IVideoFileController videoFileController;

    /**
     * Construct a new AcNewVideoFile.
     */
    public AcNewVideoFile() {
        super("video.view.actions.new");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        videoFileController.displayView();
    }
}