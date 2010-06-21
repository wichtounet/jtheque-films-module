package org.jtheque.films.view.impl.panels;

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

import org.jtheque.core.managers.error.JThequeError;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.core.utils.ui.constraints.ConstraintManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.utils.Constants.Properties;
import org.jtheque.films.view.able.IInfosFilmView;
import org.jtheque.films.view.impl.fb.IFilmFormBean;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.primary.view.impl.actions.principal.CreateNewPrincipalAction;
import org.jtheque.primary.view.impl.listeners.ObjectChangedEvent;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.DefaultFormatter;

import java.text.NumberFormat;
import java.util.Collection;

/**
 * Panel for general informations of the film.
 *
 * @author Baptiste Wicht
 */
public final class JPanelInfosFilm extends JPanel implements IInfosFilmView {
    private DataContainerCachedComboBoxModel<Person> modelRealizer;
    private DataContainerCachedComboBoxModel<SimpleData> modelLanguage;
    private DataContainerCachedComboBoxModel<SimpleData> modelSaga;

    private JComboBox comboRealizer;
    private JComboBox comboLanguage;
    private JComboBox comboSaga;

    private JFormattedTextField fieldYear;
    private JFormattedTextField fieldDuration;
    private JTextArea areaResume;

    private JButton buttonAddLanguage;
    private JButton buttonAddSaga;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private ISimpleDataService sagasService;

    /**
     * Build the panel.
     */
    @PostConstruct
    private void build() {
        I18nPanelBuilder builder = new JThequePanelBuilder(this);

        addRealizerField(builder);

        NumberFormat format = NumberFormat.getNumberInstance();
        format.setParseIntegerOnly(true);
        format.setGroupingUsed(false);

        addYearField(builder, format);
        addDurationField(builder, format);

        addLanguageField(builder);

        addSagaField(builder);

        addResumeField(builder);
    }

    /**
     * Add the realizer field.
     *
     * @param builder The builder of the panel.
     */
    private void addRealizerField(I18nPanelBuilder builder) {
        builder.addI18nLabel(Properties.Film.REALIZER, builder.gbcSet(0, 0));

        modelRealizer = new DataContainerCachedComboBoxModel<Person>(realizersService);

        comboRealizer = builder.addComboBox(modelRealizer, builder.gbcSet(1, 0));
        comboRealizer.setEnabled(false);
    }

    /**
     * Add the year field.
     *
     * @param builder The builder of the panel.
     * @param format  The format of the year.
     */
    private void addYearField(I18nPanelBuilder builder, NumberFormat format) {
        builder.addI18nLabel(Properties.Film.YEAR, builder.gbcSet(0, 1));

        fieldYear = new JFormattedTextField(format);
        fieldYear.setColumns(4);
        ((DefaultFormatter) fieldYear.getFormatter()).setAllowsInvalid(false);
        fieldYear.setEnabled(false);

        ConstraintManager.configure(fieldYear, Properties.Film.YEAR);

        builder.add(fieldYear, builder.gbcSet(1, 1));
    }

    /**
     * Add the duration field.
     *
     * @param builder The builder of the panel.
     * @param format  The format of the duration.
     */
    private void addDurationField(I18nPanelBuilder builder, NumberFormat format) {
        builder.addI18nLabel(Properties.Film.DURATION, builder.gbcSet(0, 2));

        fieldDuration = builder.add(new JFormattedTextField(format), builder.gbcSet(1, 2));
        fieldDuration.setColumns(4);
        fieldDuration.setEnabled(false);

        ConstraintManager.configure(fieldDuration, Properties.Film.DURATION);
    }

    /**
     * Add the language field.
     *
     * @param builder The builder of the panel.
     */
    private void addLanguageField(I18nPanelBuilder builder) {
        builder.addI18nLabel(Properties.Film.LANGUAGE, builder.gbcSet(0, 3));

        modelLanguage = new DataContainerCachedComboBoxModel<SimpleData>(languagesService);

        comboLanguage = builder.addComboBox(modelLanguage, builder.gbcSet(1, 3));
        comboLanguage.setEnabled(false);

        buttonAddLanguage = builder.addButton(
                new CreateNewPrincipalAction("generic.view.actions.new", "languageController"), builder.gbcSet(2, 3));
        buttonAddLanguage.setEnabled(false);
    }

    /**
     * Add the saga field.
     *
     * @param builder The builder of the panel.
     */
    private void addSagaField(I18nPanelBuilder builder) {
        builder.addI18nLabel(Properties.Film.SAGA, builder.gbcSet(0, 4));

        modelSaga = new DataContainerCachedComboBoxModel<SimpleData>(sagasService);

        comboSaga = builder.addComboBox(modelSaga, builder.gbcSet(1, 4));
        comboSaga.setEnabled(false);

        buttonAddSaga = builder.addButton(new CreateNewPrincipalAction("generic.view.actions.new", "sagaController"),
                builder.gbcSet(2, 4));
        buttonAddSaga.setEnabled(false);
    }

    /**
     * Add the resume field.
     *
     * @param builder The builder of the panel.
     */
    private void addResumeField(I18nPanelBuilder builder) {
        builder.addI18nLabel(Properties.Film.RESUME, builder.gbcSet(0, 5));

        areaResume = new JTextArea();
        areaResume.setEnabled(false);
        areaResume.setLineWrap(true);
        areaResume.setWrapStyleWord(true);
        areaResume.setAutoscrolls(true);

        builder.addScrolled(areaResume, builder.gbcSet(0, 6, GridBagUtils.BOTH, GridBagUtils.ABOVE_BASELINE_LEADING, 0, 0, 1.0, 1.0));
    }

    @Override
    public void objectChanged(ObjectChangedEvent event) {
        Film film = (Film) event.getObject();

        if (film.getTheRealizer() != null) {
            modelRealizer.setSelectedItem(film.getTheRealizer());
        }

        if (film.getTheLanguage() != null) {
            modelLanguage.setSelectedItem(film.getTheLanguage());
        }

        if (film.getTheSaga() != null) {
            modelSaga.setSelectedItem(film.getTheSaga());
        }

        fieldYear.setText(Integer.toString(film.getYear()));
        fieldDuration.setText(Integer.toString(film.getDuration()));
        areaResume.setText(film.getResume());
    }

    /**
     * Fill the form bean.
     *
     * @param fb The form bean to fill.
     */
    @Override
    public void fillFilm(IFilmFormBean fb) {
        fb.setRealizer(modelRealizer.getSelectedData());
        fb.setLanguage(modelLanguage.getSelectedData());
        fb.setYear(Integer.parseInt(fieldYear.getText()));
        fb.setDuration(Integer.parseInt(fieldDuration.getText()));
        fb.setResume(areaResume.getText());
        fb.setSaga(modelSaga.getSelectedData());
    }

    @Override
    public void setEnabled(boolean enabled) {
        comboRealizer.setEnabled(enabled);
        comboLanguage.setEnabled(enabled);
        comboSaga.setEnabled(enabled);
        fieldYear.setEnabled(enabled);
        fieldDuration.setEnabled(enabled);
        areaResume.setEnabled(enabled);
        buttonAddLanguage.setEnabled(enabled);
        buttonAddSaga.setEnabled(enabled);

        super.setEnabled(enabled);
    }

    /**
     * Validate the view.
     *
     * @param errors The errors list to fill.
     */
    @Override
    public void validate(Collection<JThequeError> errors) {
        ConstraintManager.validate(Properties.Film.REALIZER, fieldYear.getText(), errors);
        ConstraintManager.validate(Properties.Film.DURATION, fieldDuration.getText(), errors);
        ConstraintManager.validate(Properties.Film.RESUME, areaResume.getText(), errors);
        ConstraintManager.validate(Properties.Film.REALIZER, modelRealizer.getSelectedData(), errors);
        ConstraintManager.validate(Properties.Film.SAGA, modelSaga.getSelectedData(), errors);
        ConstraintManager.validate(Properties.Film.LANGUAGE, modelLanguage.getSelectedData(), errors);
    }

    @Override
    public JComponent getImpl() {
        return this;
    }
}