package org.jtheque.films.services.impl.utils.file.jt.writer;

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

import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.file.IFileManager;
import org.jtheque.core.managers.file.able.BasicDataSource;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.core.utils.file.jt.JTFileWriter;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.impl.utils.file.jt.sources.JTFDataSource;
import org.jtheque.films.utils.Constants;
import org.jtheque.primary.od.able.Person;
import org.jtheque.primary.od.able.SimpleData;
import org.jtheque.utils.bean.IntDate;
import org.jtheque.utils.io.FileUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/**
 * A writer for JTF file.
 *
 * @author Baptiste Wicht
 */
public final class JTFFileWriter extends JTFileWriter {
    @Override
    public void writeFile(DataOutputStream stream, BasicDataSource source) {
        JTFDataSource dataSource = (JTFDataSource) source;

        try {
            writeHeader(stream, dataSource);

            Film film = dataSource.getFilm();

            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getTitle()));
            stream.writeInt(film.getYear());
            stream.writeInt(film.getDuration());
            stream.writeInt(film.getNote().getValue().intValue());
            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getResume()));
            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getComment()));

            stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);

            Collection<SimpleData> countries = new HashSet<SimpleData>(10);

            writeActors(stream, film, countries);
            writeRealizer(stream, film, countries);
            writeLanguage(stream, film);
            writeKinds(stream, film);
            writeType(stream, film);
            writeCountries(stream, countries);
        } catch (IOException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        } finally {
            FileUtils.close(stream);
        }
    }

    /**
     * Write the header of the file.
     *
     * @param stream     The stream to write to.
     * @param dataSource The data source.
     * @throws IOException Thrown if an error occurs during the stream writing.
     */
    private static void writeHeader(DataOutputStream stream, BasicDataSource dataSource) throws IOException {
        stream.writeUTF(FileUtils.encryptKey(Constants.Files.JT.JTKEY));
        stream.writeUTF(dataSource.getVersion().getVersion());
        stream.writeInt(dataSource.getFileVersion());
        stream.writeInt(IntDate.today().intValue());

        stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);
    }

    /**
     * Write the actors to the file.
     *
     * @param stream    The stream to write to.
     * @param film      The film.
     * @param countries The countries.
     * @throws IOException Thrown if an error occurs during the stream writing.
     */
    private static void writeActors(DataOutputStream stream, Film film, Collection<SimpleData> countries) throws IOException {
        if (film.hasActors()) {
            stream.writeInt(Constants.Files.JT.NO_CONTENT);
        } else {
            stream.writeInt(Constants.Files.JT.CONTENT);

            boolean first = true;

            for (Person actor : film.getActors()) {
                if (first) {
                    first = false;
                } else {
                    stream.writeLong(Constants.Files.JT.JTINTERNSEPARATOR);
                }

                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(actor.getName()));
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(actor.getFirstName()));
                stream.writeInt(actor.getTheCountry().getId());
                stream.writeInt(actor.getNote().getValue().intValue());

                countries.add(actor.getTheCountry());
            }
        }

        stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);
    }

    /**
     * Write the realizer to the file.
     *
     * @param stream    The stream to write to.
     * @param film      The film.
     * @param countries The countries.
     * @throws IOException Thrown if an error occurs during the stream writing.
     */
    private static void writeRealizer(DataOutputStream stream, Film film, Collection<SimpleData> countries) throws IOException {
        if (film.hasRealizer()) {
            stream.writeInt(Constants.Files.JT.NO_CONTENT);
        } else {
            stream.writeInt(Constants.Files.JT.CONTENT);

            Person realizer = film.getTheRealizer();

            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(realizer.getName()));
            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(realizer.getFirstName()));
            stream.writeInt(realizer.getTheCountry().getId());
            stream.writeInt(realizer.getNote().getValue().intValue());

            if (realizer.hasCountry()) {
                countries.add(realizer.getTheCountry());
            }
        }

        stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);
    }

    /**
     * Write the language to the file.
     *
     * @param stream The stream to write to.
     * @param film   The film.
     * @throws IOException Thrown if an error occurs during the stream writing.
     */
    private static void writeLanguage(DataOutputStream stream, Film film) throws IOException {
        if (film.hasLanguage()) {
            stream.writeInt(Constants.Files.JT.NO_CONTENT);
        } else {
            stream.writeInt(Constants.Files.JT.CONTENT);

            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getTheLanguage().getName()));
        }

        stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);
    }

    /**
     * Write the kinds to the file.
     *
     * @param stream The stream to write to.
     * @param film   The film.
     * @throws IOException Thrown if an error occurs during the stream writing.
     */
    private static void writeKinds(DataOutputStream stream, Film film) throws IOException {
        if (film.hasKinds()) {
            stream.writeInt(Constants.Files.JT.NO_CONTENT);
        } else {
            stream.writeInt(Constants.Files.JT.CONTENT);

            boolean first = true;

            for (SimpleData kind : film.getKinds()) {
                if (first) {
                    first = false;
                } else {
                    stream.writeLong(Constants.Files.JT.JTINTERNSEPARATOR);
                }

                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(kind.getName()));
            }
        }

        stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);
    }

    /**
     * Write the type to the file.
     *
     * @param stream The stream to write to.
     * @param film   The film.
     * @throws IOException Thrown if an error occurs during the stream writing.
     */
    private static void writeType(DataOutputStream stream, Film film) throws IOException {
        if (film.getTheType() == null) {
            stream.writeInt(Constants.Files.JT.NO_CONTENT);
        } else {
            stream.writeInt(Constants.Files.JT.CONTENT);

            stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(film.getTheType().getName()));
        }

        stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);
    }

    /**
     * Write the countries to the file.
     *
     * @param stream    The stream to write to.
     * @param countries The countries to write.
     * @throws IOException Thrown if an error occurs during the stream writing.
     */
    private static void writeCountries(DataOutputStream stream, Collection<SimpleData> countries) throws IOException {
        if (countries.isEmpty()) {
            stream.writeInt(Constants.Files.JT.NO_CONTENT);
        } else {
            stream.writeInt(Constants.Files.JT.CONTENT);

            boolean first = true;

            for (SimpleData country : countries) {
                if (first) {
                    first = false;
                } else {
                    stream.writeLong(Constants.Files.JT.JTINTERNSEPARATOR);
                }

                stream.writeInt(country.getId());
                stream.writeUTF(Managers.getManager(IFileManager.class).formatUTFToWrite(country.getName()));
            }
        }

        stream.writeLong(Constants.Files.JT.JTCATEGORYSEPARATOR);
    }
}