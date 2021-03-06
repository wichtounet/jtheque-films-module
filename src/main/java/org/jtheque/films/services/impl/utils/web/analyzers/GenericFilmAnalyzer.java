package org.jtheque.films.services.impl.utils.web.analyzers;

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
import org.jtheque.core.managers.beans.IBeansManager;
import org.jtheque.films.IFilmsModule;
import org.jtheque.films.services.able.IActorService;
import org.jtheque.films.services.able.INotesService;
import org.jtheque.films.services.able.IRealizersService;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.primary.services.able.ISimpleDataService;
import org.jtheque.primary.utils.web.analyzers.generic.GenericGenerator;
import org.jtheque.primary.utils.web.analyzers.generic.field.FieldGetter;
import org.jtheque.primary.utils.web.analyzers.generic.operation.ScannerPossessor;
import org.jtheque.utils.StringUtils;
import org.jtheque.utils.bean.DataUtils;

import javax.annotation.Resource;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A generic film analyzer. It seems an analyzer who takes its parser information from an XML file.
 *
 * @author Baptiste Wicht
 */
public final class GenericFilmAnalyzer extends AbstractFilmAnalyzer implements ScannerPossessor {
    @Resource
    private ISimpleDataService kindsService;

    @Resource
    private IRealizersService realizersService;

    @Resource
    private INotesService notesService;

    @Resource
    private ISimpleDataService countriesService;

    @Resource
    private IActorService actorService;

    @Resource
    private IFilmsModule filmsModule;

    /**
     * The generator of the field getters.
     */
    private final GenericGenerator generator;

    private FieldGetter dateGetter;
    private FieldGetter durationGetter;
    private FieldGetter imageGetter;
    private FieldGetter kindGetter;
    private FieldGetter realizerGetter;
    private FieldGetter resumeGetter;
    private FieldGetter actorsGetter;

    private static final int SECONDS_IN_A_MINUTE = 60;

    private static final Pattern ACTOR_SEPARATOR_PATTERN = Pattern.compile("%%%");
    private static final Pattern HOUR_SEPARATOR_PATTERN = Pattern.compile("h");

    /**
     * Construct a new GenericFilmAnalyzer.
     *
     * @param generator The generator to use.
     */
    public GenericFilmAnalyzer(GenericGenerator generator) {
        super();

        this.generator = generator;

        init();

        Managers.getManager(IBeansManager.class).inject(this);
    }

    @Override
    public Scanner getScanner() {
        return null;
    }

    /**
     * Init the parser.
     */
    private void init() {
        dateGetter = generator.getFieldGetter("date");
        durationGetter = generator.getFieldGetter("duration");
        imageGetter = generator.getFieldGetter("image");
        kindGetter = generator.getFieldGetter("kind");
        realizerGetter = generator.getFieldGetter("realizer");
        resumeGetter = generator.getFieldGetter("resume");
        actorsGetter = generator.getFieldGetter("actors");
    }

    @Override
    public void findDate(String line) {
        if (isDateDo()) {
            return;
        }

        if (dateGetter.mustGet(line)) {
            String transformedLine = dateGetter.performOperations(line, this);

            String value = dateGetter.getValue(transformedLine);

            if (value != null) {
                getFilm().setYear(Integer.parseInt(value));

                setDate(true);
            }
        }
    }

    @Override
    public void findDuration(String line) {
        if (isDurationDo()) {
            return;
        }

        if (durationGetter.mustGet(line)) {
            String transformedLine = durationGetter.performOperations(line, this);

            String value = durationGetter.getValue(transformedLine);

            if (value != null) {
                int minutes;

                if (value.contains("h")) {
                    String[] hour = HOUR_SEPARATOR_PATTERN.split(value);
                    minutes = Integer.parseInt(hour[0]) * SECONDS_IN_A_MINUTE + Integer.parseInt(hour[1]);
                } else {
                    minutes = Integer.parseInt(value);
                }

                getFilm().setDuration(minutes);

                setDuration(true);
            }
        }
    }

    @Override
    public void findImage(String line) {
        if (isImageDo()) {
            return;
        }

        if (imageGetter.mustGet(line)) {
            String transformedLine = imageGetter.performOperations(line, this);

            String value = imageGetter.getValue(transformedLine);

            if (value != null) {
                AnalyzerUtils.downloadMiniature(getFilm(), value);

                setImage(true);
            }
        }
    }

    @Override
    public void findKind(String line) {
        if (isKindDo()) {
            return;
        }

        if (kindGetter.mustGet(line)) {
            String transformedLine = kindGetter.performOperations(line, this);

            String value = kindGetter.getValue(transformedLine);

            if (StringUtils.isNotEmpty(value)) {
                value = StringUtils.setFirstLetterOnlyUpper(value);

                if (kindsService.exist(value)) {
                    getFilm().addKind(kindsService.getSimpleData(value));
                } else {
                    SimpleData kind = kindsService.getEmptySimpleData();

                    kind.setName(value);

                    kindsService.create(kind);

                    getFilm().addKind(kind);
                }

                setKind(true);
            }
        }
    }


    @Override
    public void findRealizer(String line) {
        if (isRealizerDo()) {
            return;
        }

        if (realizerGetter.mustGet(line)) {
            String transformedLine = realizerGetter.performOperations(line, this);

            String value = realizerGetter.getValue(transformedLine);

            if (value != null) {
                String[] nameAndFirstName = DataUtils.getNameAndFirstName(value);

                setRealizerOfFilm(nameAndFirstName[0], nameAndFirstName[1]);

                setRealizer(true);
            }
        }
    }

    /**
     * Set the realizer of the film.
     *
     * @param name      The name of the realizer.
     * @param firstName The first name of the realizer.
     */
    private void setRealizerOfFilm(String name, String firstName) {
        if (realizersService.exists(firstName, name)) {
            getFilm().setTheRealizer(realizersService.getRealizer(firstName, name));
        } else {
            Person realizer = realizersService.getEmptyRealizer();
            realizer.setName(name);
            realizer.setFirstName(firstName);
            realizer.setTheCountry(countriesService.getDefaultSimpleData());
            realizer.setNote(notesService.getDefaultNote());

            realizersService.create(realizer);

            getFilm().setTheRealizer(realizer);
        }
    }

    @Override
    public void findActors(String line) {
        if (isActorsDo()) {
            return;
        }

        if (actorsGetter.mustGet(line)) {
            String transformedLine = actorsGetter.performOperations(line, this);

            String value = actorsGetter.getValue(transformedLine);

            if (value != null) {
                value = StringUtils.removeHTMLEntities(value);

                String[] actorsTemp = ACTOR_SEPARATOR_PATTERN.split(value);

                int current = 0;

                for (String name : actorsTemp) {
                    if (current++ >= filmsModule.getConfiguration().getNumberOfActors()) {
                        break;
                    }

                    String[] nameAndFirstName = DataUtils.getNameAndFirstName(name);

                    addActor(nameAndFirstName[0], nameAndFirstName[1]);

                    if (getFilm().getActors().size() >= filmsModule.getConfiguration().getNumberOfActors()) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Add an actor to the film.
     *
     * @param name      The name of the actor.
     * @param firstName The first name of the actor.
     */
    private void addActor(String name, String firstName) {
        if (actorService.exist(firstName, name)) {
            Person actor = actorService.getPerson(firstName, name);
            getFilm().addActor(actor);
        } else {
            Person actor = actorService.getEmptyPerson();
            actor.setName(name);
            actor.setFirstName(firstName);
            actor.setTheCountry(countriesService.getDefaultSimpleData());
            actor.setNote(notesService.getDefaultNote());

            actorService.create(actor);

            getFilm().addActor(actor);
        }
    }

    @Override
    public void findResume(String line) {
        if (isResumeDo()) {
            return;
        }

        if (resumeGetter.mustGet(line)) {
            String transformedLine = resumeGetter.performOperations(line, this);

            String value = resumeGetter.getValue(transformedLine);

            if (value != null) {
                getFilm().setResume(StringUtils.removeHTMLEntities(value));

                setResume(true);
            }
        }
    }
}
