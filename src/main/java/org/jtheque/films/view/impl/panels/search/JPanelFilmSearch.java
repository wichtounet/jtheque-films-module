package org.jtheque.films.view.impl.panels.search;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.utils.ui.builders.I18nPanelBuilder;
import org.jtheque.core.utils.ui.builders.JThequePanelBuilder;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.films.services.impl.utils.search.DataSearcher;
import org.jtheque.films.services.impl.utils.search.Searcher;
import org.jtheque.films.services.impl.utils.search.filters.ActorFilter;
import org.jtheque.films.services.impl.utils.search.filters.DurationFilter;
import org.jtheque.films.services.impl.utils.search.filters.KindFilter;
import org.jtheque.films.services.impl.utils.search.filters.LanguageFilter;
import org.jtheque.films.services.impl.utils.search.filters.NoteFilter;
import org.jtheque.films.services.impl.utils.search.filters.RealizerFilter;
import org.jtheque.films.services.impl.utils.search.filters.TypeFilter;
import org.jtheque.films.services.impl.utils.search.filters.YearFilter;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.view.impl.editors.DurationEditor;
import org.jtheque.films.view.impl.models.spinner.SpinnerDurationModel;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.primary.view.impl.models.DataContainerCachedComboBoxModel;
import org.jtheque.primary.view.impl.models.NotesComboBoxModel;
import org.jtheque.utils.ui.GridBagUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 * A search panel for films.
 *
 * @author Baptiste Wicht
 */
public final class JPanelFilmSearch extends JPanelSearch {
    private static final long serialVersionUID = 7108296888681550453L;

    private JCheckBox boxKind;
    private JCheckBox boxActor;
    private JCheckBox boxType;
    private JCheckBox boxYear;
    private JTextField fieldYearFrom;
    private JTextField fieldYearTo;
    private JCheckBox boxRealizer;
    private JCheckBox boxLanguage;
    private JCheckBox boxNote;
    private JCheckBox boxDuration;
    private JSpinner fieldDurationFrom;
    private JSpinner fieldDurationTo;

    private NotesComboBoxModel modelNotes;
    private DataContainerCachedComboBoxModel<SimpleData> modelKinds;
    private DataContainerCachedComboBoxModel<Person> modelActors;
    private DataContainerCachedComboBoxModel<SimpleData> modelTypes;
    private DataContainerCachedComboBoxModel<Person> modelRealizers;
    private DataContainerCachedComboBoxModel<SimpleData> modelLanguages;

    @Resource
    private ISimpleDataService typesService;

    @Resource
    private ISimpleDataService languagesService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private IActorService actorService;

    /**
     * Build the panel.
     */
    @PostConstruct
    private void build() {
        I18nPanelBuilder builder = new JThequePanelBuilder(this);

        addKindChoice(builder);
        addActorChoice(builder);
        addTypeChoice(builder);
        addYearChoice(builder);
        addRealizerChoice(builder);
        addLanguageChoice(builder);
        addNoteChoice(builder);
        addDurationChoice(builder);
    }

    /**
     * Add the kind choice.
     *
     * @param builder The builder of the panel.
     */
    private void addKindChoice(I18nPanelBuilder builder) {
        boxKind = builder.add(new JCheckBox(), builder.gbcSet(0, 0));

        builder.addI18nLabel(Film.KIND, builder.gbcSet(1, 0));

        modelKinds = new DataContainerCachedComboBoxModel<SimpleData>(kindsService);

        builder.addComboBox(modelKinds, builder.gbcSet(2, 0, GridBagUtils.NONE, 2, 1));
    }

    /**
     * Add the actor choice.
     *
     * @param builder The builder of the panel.
     */
    private void addActorChoice(I18nPanelBuilder builder) {
        boxActor = builder.add(new JCheckBox(), builder.gbcSet(0, 1));

        builder.addI18nLabel(Film.ACTORS, builder.gbcSet(1, 1));

        modelActors = new DataContainerCachedComboBoxModel<Person>(actorService);

        builder.addComboBox(modelActors, builder.gbcSet(2, 1, GridBagUtils.NONE, 2, 1));
    }

    /**
     * Add the type choice.
     *
     * @param builder The builder of the panel.
     */
    private void addTypeChoice(I18nPanelBuilder builder) {
        boxType = builder.add(new JCheckBox(), builder.gbcSet(0, 2));

        builder.addI18nLabel(Film.TYPE, builder.gbcSet(1, 2));

        modelTypes = new DataContainerCachedComboBoxModel<SimpleData>(typesService);

        builder.addComboBox(modelTypes, builder.gbcSet(2, 2, GridBagUtils.NONE, 2, 1));
    }

    /**
     * Add the realizer choice.
     *
     * @param builder The builder of the panel.
     */
    private void addRealizerChoice(I18nPanelBuilder builder) {
        boxRealizer = builder.add(new JCheckBox(), builder.gbcSet(0, 4));

        builder.addI18nLabel(Film.REALIZER, builder.gbcSet(1, 4));

        modelRealizers = new DataContainerCachedComboBoxModel<Person>(realizersService);

        builder.addComboBox(modelRealizers, builder.gbcSet(2, 4, GridBagUtils.NONE, 2, 1));
    }

    /**
     * Add the language choice.
     *
     * @param builder The builder of the panel.
     */
    private void addLanguageChoice(I18nPanelBuilder builder) {
        boxLanguage = builder.add(new JCheckBox(), builder.gbcSet(0, 5));

        builder.addI18nLabel(Film.LANGUAGE, builder.gbcSet(1, 5));

        modelLanguages = new DataContainerCachedComboBoxModel<SimpleData>(languagesService);

        builder.addComboBox(modelLanguages, builder.gbcSet(2, 5, GridBagUtils.NONE, 2, 1));
    }

    /**
     * Add the note choice.
     *
     * @param builder The builder of the panel.
     */
    private void addNoteChoice(I18nPanelBuilder builder) {
        boxNote = builder.add(new JCheckBox(), builder.gbcSet(0, 6));

        builder.addI18nLabel(Film.NOTE, builder.gbcSet(1, 6));

        modelNotes = new NotesComboBoxModel(daoNotes);

        builder.addComboBox(modelNotes, builder.gbcSet(2, 6, GridBagUtils.NONE, 2, 1));
    }

    /**
     * Add the year choice.
     *
     * @param builder The builder of the panel.
     */
    private void addYearChoice(I18nPanelBuilder builder) {
        boxYear = builder.add(new JCheckBox(), builder.gbcSet(0, 3));

        builder.addI18nLabel(Film.YEAR, builder.gbcSet(1, 3));

        fieldYearFrom = builder.add(new JTextField(4), builder.gbcSet(2, 3));

        builder.addI18nLabel("search.film.to", builder.gbcSet(3, 3));

        fieldYearTo = builder.add(new JTextField(4), builder.gbcSet(4, 3));
    }

    /**
     * Add the duration choice.
     *
     * @param builder The builder of the panel.
     */
    private void addDurationChoice(I18nPanelBuilder builder) {
        boxDuration = builder.add(new JCheckBox(), builder.gbcSet(0, 7));

        builder.addI18nLabel(Film.DURATION, builder.gbcSet(1, 7));

        fieldDurationFrom = new JSpinner(new SpinnerDurationModel("1:00"));
        fieldDurationFrom.setEditor(new DurationEditor(fieldDurationFrom));
        builder.add(fieldDurationFrom, builder.gbcSet(2, 7));

        builder.addI18nLabel("search.film.to", builder.gbcSet(3, 7));

        fieldDurationTo = new JSpinner(new SpinnerDurationModel("2:00"));
        fieldDurationTo.setEditor(new DurationEditor(fieldDurationTo));
        builder.add(fieldDurationTo, builder.gbcSet(4, 7));
    }

    @Override
    public String getTitle() {
        return Managers.getManager(ILanguageManager.class).getMessage("search.film.title");
    }

    @Override
    public Searcher<org.jtheque.films.persistence.od.able.Film> getSearcher() {
        Searcher<org.jtheque.films.persistence.od.able.Film> searcher = new DataSearcher<org.jtheque.films.persistence.od.able.Film>();

        if (boxActor.isSelected()) {
            searcher.addFilter(new ActorFilter(modelActors.getSelectedData()));
        }

        if (boxKind.isSelected()) {
            searcher.addFilter(new KindFilter(modelKinds.getSelectedData()));
        }

        if (boxType.isSelected()) {
            searcher.addFilter(new TypeFilter(modelTypes.getSelectedData()));
        }

        if (boxRealizer.isSelected()) {
            searcher.addFilter(new RealizerFilter(modelRealizers.getSelectedData()));
        }

        if (boxLanguage.isSelected()) {
            searcher.addFilter(new LanguageFilter(modelLanguages.getSelectedData()));
        }

        if (boxNote.isSelected()) {
            searcher.addFilter(new NoteFilter<org.jtheque.films.persistence.od.able.Film>(modelNotes.getSelectedNote()));
        }

        if (boxYear.isSelected()) {
            searcher.addFilter(new YearFilter(Integer.parseInt(fieldYearFrom.getText()), Integer.parseInt(fieldYearTo.getText())));
        }

        if (boxDuration.isSelected()) {
            searcher.addFilter(new DurationFilter(
                    ((SpinnerDurationModel) fieldDurationFrom.getModel()).intValue(),
                    ((SpinnerDurationModel) fieldDurationTo.getModel()).intValue()));
        }

        return searcher;
    }
}
