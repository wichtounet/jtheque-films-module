package org.jtheque.films.view.impl.frames;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.films.services.impl.utils.VideoFile;
import org.jtheque.films.view.able.IVideoView;
import org.jtheque.films.view.impl.actions.video.AcNewVideoFile;
import org.jtheque.films.view.impl.actions.video.AcOpenVideoFile;
import org.jtheque.films.view.impl.actions.video.AcRefreshVideoView;
import org.jtheque.films.view.impl.models.list.VideoListModel;
import org.jtheque.films.view.impl.renderers.VideoListRenderer;
import org.jtheque.utils.ui.GridBagUtils;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

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
 * A video view implementation.
 *
 * @author Baptiste Wicht
 */
public final class VideoView extends SwingDialogView implements IVideoView {
    private JList list;

    private final VideoListModel model;

    /**
     * Construct a new VideoView.
     *
     * @param parent The parent frame.
     */
    public VideoView(Frame parent) {
        super(parent);

        model = new VideoListModel();

        setTitleKey("video.view.title");
        setContentPane(buildContentPane());
        pack();

        setLocationRelativeTo(getOwner());
    }

    /**
     * Build the content pane.
     *
     * @return The builded content pane.
     */
    private Container buildContentPane() {
        PanelBuilder builder = new JThequePanelBuilder();

        list = new JList();
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new VideoListRenderer());
        list.setVisibleRowCount(4);

        builder.addScrolled(list, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, -1, 1.0, 1.0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 0.0),
                new AcRefreshVideoView(), new AcOpenVideoFile(), new AcNewVideoFile(), new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
    }

    @Override
    public VideoFile getSelectedFile() {
        return (VideoFile) list.getSelectedValue();
    }

    @Override
    public void refreshList() {
        model.refresh();
    }
}
