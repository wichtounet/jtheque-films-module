package org.jtheque.films.view.impl.frames;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.films.services.impl.utils.VideoFile;
import org.jtheque.films.view.able.IVideoView;
import org.jtheque.films.view.impl.models.list.VideoListModel;
import org.jtheque.films.view.impl.renderers.VideoListRenderer;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.swing.Action;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import java.awt.Container;
import java.awt.Frame;
import java.util.Collection;

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
 * A video view implementation.
 *
 * @author Baptiste Wicht
 */
public final class VideoView extends SwingDialogView implements IVideoView {
    private JList list;

    private VideoListModel model;

    private Action openAction;
    private Action refreshAction;
    private Action newAction;
    private Action closeAction;

    /**
     * Construct a new VideoView.
     *
     * @param parent The parent frame.
     */
    public VideoView(Frame parent) {
        super(parent);
    }

    /**
     * Build the view.
     */
    @PostConstruct
    private void build() {
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
        PanelBuilder builder = new PanelBuilder();

        list = new JList();
        list.setModel(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new VideoListRenderer());
        list.setVisibleRowCount(4);

        builder.addScrolled(list, builder.gbcSet(0, 0, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, -1, 1.0, 1.0));

        builder.addButtonBar(builder.gbcSet(0, 1, GridBagUtils.NONE, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 0.0),
                refreshAction, openAction, newAction, closeAction);

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

    /**
     * Set the action to open the video.
     *
     * @param openAction The action to open the video.
     */
    public void setOpenAction(Action openAction) {
        this.openAction = openAction;
    }

    /**
     * Set the action to create a new video.
     *
     * @param newAction The action to create a new video.
     */
    public void setNewAction(Action newAction) {
        this.newAction = newAction;
    }

    /**
     * Set the action to close the view.
     *
     * @param closeAction The action to close the view.
     */
    public void setCloseAction(Action closeAction) {
        this.closeAction = closeAction;
    }

    /**
     * Set the action to refresh the view.
     *
     * @param refreshAction The action to refresh the view.
     */
    public void setRefreshAction(Action refreshAction) {
        this.refreshAction = refreshAction;
    }
}