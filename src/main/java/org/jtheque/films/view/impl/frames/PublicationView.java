package org.jtheque.films.view.impl.frames;

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.managers.view.impl.components.JThequeCheckBox;
import org.jtheque.core.managers.view.impl.frame.abstraction.SwingDialogView;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.builders.PanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.services.impl.utils.file.FTPConnectionInfos;
import org.jtheque.films.utils.Constants.Properties.Publication;
import org.jtheque.films.view.able.IPublicationView;
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
     * @param parent The parent frame.
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
        I18nPanelBuilder builder = new JThequePanelBuilder();

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
     * @param builder        The builder of the view.
     * @param validateAction
     */
    private void addPathFields(I18nPanelBuilder builder, Action validateAction) {
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
     * @param builder        The builder of the view.
     * @param validateAction
     */
    private void addAuthenticationFields(I18nPanelBuilder builder, Action validateAction) {
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
     * @param builder        The builder of the view.
     * @param validateAction
     */
    private void addPortField(I18nPanelBuilder builder, Action validateAction) {
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
