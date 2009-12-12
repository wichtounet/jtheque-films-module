package org.jtheque.films.view.impl.frames;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.JThequeCheckBox;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.services.impl.utils.file.FTPConnectionInfos;
import org.jtheque.films.utils.Constants.Properties.Publication;
import org.jtheque.films.view.able.IPublicationView;
import org.jtheque.films.view.impl.actions.CloseViewAction;
import org.jtheque.films.view.impl.actions.publication.AcValidatePublicationView;
import org.jtheque.utils.ui.GridBagUtils;
import org.jtheque.utils.ui.SwingUtils;

import javax.swing.Action;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
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
 * A publication view implementation.
 *
 * @author Baptiste Wicht
 */
public final class PublicationView extends SwingDialogView implements IPublicationView {
    private JTextField fieldHost;
    private JTextField fieldPath;
    private JTextField fieldUser;
    private JPasswordField fieldPassword;
    private JTextField fieldPort;
    private JThequeCheckBox passiveBox;

    private static final int FIELD_COLUMNS = 15;

    /**
     * Construct a new Publication View.
     *
     * @param parent         The parent frame.
     */
    public PublicationView(Frame parent) {
        super(parent);

        setTitleKey("publication.view.title");
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

        Action validateAction = new AcValidatePublicationView();
        
        addPathFields(builder, validateAction);
        addAuthenticationFields(builder, validateAction);
        addPortField(builder, validateAction);
        addPassiveField(builder);

        builder.addButtonBar(builder.gbcSet(0, 6, GridBagUtils.HORIZONTAL, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0),
                validateAction, new CloseViewAction("generic.view.actions.cancel", this));

        return builder.getPanel();
    }

    /**
     * Add the field for the path.
     *
     * @param builder The builder of the view.
     * @param validateAction
     */
    private void addPathFields(PanelBuilder builder, Action validateAction) {
        builder.addI18nLabel(Publication.HOST, builder.gbcSet(0, 0));

        fieldHost = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 0, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        SwingUtils.addFieldValidateAction(fieldHost, validateAction);
        ConstraintManager.configure(fieldHost, Publication.HOST);

        builder.addI18nLabel(Publication.PATH, builder.gbcSet(0, 1));

        fieldPath = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 1, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        SwingUtils.addFieldValidateAction(fieldPath, validateAction);
        ConstraintManager.configure(fieldPath, Publication.PATH);
    }

    /**
     * Add the field for the authentication.
     *
     * @param builder The builder of the view.
     * @param validateAction
     */
    private void addAuthenticationFields(PanelBuilder builder, Action validateAction) {
        builder.addI18nLabel(Publication.USER, builder.gbcSet(0, 2));

        fieldUser = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 2, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        SwingUtils.addFieldValidateAction(fieldUser, validateAction);
        ConstraintManager.configure(fieldUser, Publication.USER);

        builder.addI18nLabel(Publication.PASSWORD, builder.gbcSet(0, 3));

        fieldPassword = builder.add(new JPasswordField(FIELD_COLUMNS), builder.gbcSet(1, 3, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        SwingUtils.addFieldValidateAction(fieldPassword, validateAction);
    }

    /**
     * Add the field for the port.
     *
     * @param builder The builder of the view.
     * @param validateAction
     */
    private void addPortField(PanelBuilder builder, Action validateAction) {
        builder.addI18nLabel(Publication.PORT, builder.gbcSet(0, 4));

        fieldPort = builder.add(new JTextField(FIELD_COLUMNS), builder.gbcSet(1, 4, GridBagUtils.HORIZONTAL, GridBagUtils.BASELINE_LEADING, 0, 1, 1.0, 0.0));
        SwingUtils.addFieldValidateAction(fieldPort, validateAction);
        ConstraintManager.configure(fieldPort, Publication.PORT);
    }

    /**
     * Add the field for the passive configuration.
     *
     * @param builder The builder of the view.
     */
    private void addPassiveField(PanelBuilder builder) {
        passiveBox = new JThequeCheckBox("publication.passive");
        passiveBox.setBackground(Color.white);

        builder.add(passiveBox, builder.gbcSet(0, 5, GridBagUtils.HORIZONTAL, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 1, 1.0, 0.0));
    }

    @Override
    protected void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Publication.HOST, fieldHost.getText(), errors);
        ConstraintManager.validate(Publication.USER, fieldUser.getText(), errors);
        ConstraintManager.validate(Publication.PASSWORD, new String(fieldPassword.getPassword()), errors);
        ConstraintManager.validate(Publication.PORT, fieldPort.getText(), errors);
        ConstraintManager.validate(Publication.PATH, fieldPath.getText(), errors);
    }

    @Override
    public FTPConnectionInfos getConnectionInfos() {
        FTPConnectionInfos infos = new FTPConnectionInfos();

        infos.setHost(fieldHost.getText());
        infos.setPath(fieldPath.getText());
        infos.setUser(fieldUser.getText());
        infos.setPassword(new String(fieldPassword.getPassword()));
        infos.setPassive(passiveBox.isSelected());
        infos.setPort(Integer.parseInt(fieldPort.getText()));

        return infos;
    }
}