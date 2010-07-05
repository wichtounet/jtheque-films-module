package org.jtheque.films.services.impl.utils.file.exports;

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
import org.jtheque.core.utils.file.XMLWriter;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.utils.Constants;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.utils.bean.IntDate;

/**
 * An exporter to XML.
 *
 * @author Baptiste Wicht
 */
public final class XMLExporter extends AbstractExporter<Film> {
    @Override
    public boolean canExportTo(FileType fileType) {
        return fileType == FileType.XML;
    }

    @Override
    public void export(String path) {
        XMLWriter writer = new XMLWriter("films");

        writeHeader(writer);

        for (Film film : getDatas()) {
            writer.add("film");

            writer.addOnly("title", film.getTitle());

            exportActors(film, writer);
            exportKinds(film, writer);

            writer.addOnly("year", film.getYear());

            exportRealizer(film, writer);

            if (film.hasType()) {
                writer.add("type", film.getTheType().getDisplayableText());
            }

            if (film.hasLanguage()) {
                writer.add("language", film.getTheLanguage().getDisplayableText());
            }

            writer.addOnly("duration", film.getDuration());
            writer.addOnly("note", film.getNote().getValue().intValue());
            writer.addOnly("resume", film.getResume());
            writer.addOnly("comment", film.getComment());

            writer.switchToParent();
        }

        writer.switchToParent();
    }

    /**
     * Write the header to the XML file.
     *
     * @param writer The XML writer.
     */
    private static void writeHeader(XMLWriter writer) {
        writer.add("header");

        writer.addOnly("date", IntDate.today().intValue());
        writer.addOnly("file-version", Constants.Files.Versions.XML.THIRD);
        writer.addOnly("jtheque-version", Managers.getCore().getApplication().getVersion().getVersion());

        writer.switchToParent();
    }

    /**
     * Write the actors of the film to the XML file.
     *
     * @param film   The film to get the informations from.
     * @param writer The XML writer.
     */
    private static void exportActors(Film film, XMLWriter writer) {
        writer.add("actors");

        for (Person actor : film.getActors()) {
            writePerson(writer, actor, "actor");
        }

        writer.switchToParent();
    }

    /**
     * Write the realizer of the film to the XML file.
     *
     * @param film   The film to get the informations from.
     * @param writer The XML writer.
     */
    private static void exportRealizer(Film film, XMLWriter writer) {
        if (film.hasRealizer()) {
            writePerson(writer, film.getTheRealizer(), "realizer");
        }
    }

    /**
     * Write a person.
     *
     * @param writer   The XML Writer.
     * @param person   The person to write.
     * @param category The category id.
     */
    private static void writePerson(XMLWriter writer, Person person, String category) {
        writer.add(category);

        writer.addOnly("name", person.getName());
        writer.addOnly("firstname", person.getFirstName());
        writer.addOnly("country", person.getTheCountry().getDisplayableText());

        writer.switchToParent();
    }

    /**
     * Write the kinds of the film to the XML file.
     *
     * @param film   The film to get the informations from.
     * @param writer The XML writer.
     */
    private static void exportKinds(Film film, XMLWriter writer) {
        writer.add("kinds");

        for (SimpleData kind : film.getKinds()) {
            writer.add("kind");

            writer.addOnly("name", kind.getName());

            writer.switchToParent();
        }

        writer.switchToParent();
    }
}
