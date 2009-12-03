package org.jtheque.films.utils;

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

/**
 * Contains the constants of the application.
 *
 * @author Baptiste Wicht
 */
public final class Constants {
    public static final int MINIATURE_WIDTH = 100;

    public static final String IMAGE_BASE_NAME = "org/jtheque/films/images";

    public static final String BORROWERS = "AbstractBorrower";
    public static final String YEAR = "Year";

    public static final String FILM_ICON = "film";

    /**
     * Construct a new Constants. This class isn't instanciable.
     */
    private Constants() {
        super();
    }

    /**
     * A properties class for data.
     *
     * @author Baptiste Wicht
     */
    public interface Properties {
        /**
         * Contains properties of film.
         *
         * @author Baptiste Wicht
         */
        interface Film {
            String KIND = "film.kind";
            String TITLE = "film.title";
            String TYPE = "film.type";
            String REALIZER = "film.realizer";
            String YEAR = "film.year";
            String DURATION = "film.duration";
            String LANGUAGE = "film.language";
            String ACTORS = "film.actors";
            String RESUME = "film.resume";
            String COMMENT = "film.comment";
            String IMAGE = "film.image";
            String FILE_PATH = "film.filepath";
            String SAGA = "film.saga";
            String NOTE = "film.note";

            int TITLE_MAX_LENGTH = 150;
            int RESUME_MAX_LENGTH = 2000;
            int COMMENT_MAX_LENGTH = 2000;
            int YEAR_MAX_LENGTH = 4;
            int DURATION_MAX_LENGTH = 4;
            int IMAGE_MAX_LENGTH = 200;
            int FILE_PATH_MAX_LENGTH = 200;
        }

        /**
         * Kind constants.
         *
         * @author Baptiste Wicht
         */
        interface Kind {
            String NAME = "kind.name";
            int NAME_MAX_LENGTH = 100;
        }

        /**
         * Type constants.
         *
         * @author Baptiste Wicht
         */
        interface Type {
            String NAME = "type.name";
            int NAME_MAX_LENGTH = 100;
        }

        /**
         * Saga constants.
         *
         * @author Baptiste Wicht
         */
        interface Saga {
            String NAME = "saga.name";
            int NAME_MAX_LENGTH = 100;
        }

        /**
         * Contains properties of publication.
         *
         * @author Baptiste Wicht
         */
        interface Publication {
            String HOST = "publication.host";
            String USER = "publication.user";
            String PASSWORD = "publication.password";
            String PATH = "publication.path";
            String PORT = "publication.port";

            int HOST_MAX_LENGTH = 100;
            int USER_MAX_LENGTH = 100;
            int PASSWORD_MAX_LENGTH = 100;
            int PATH_MAX_LENGTH = 200;
            int PORT_MAX_LENGTH = 12;
        }

        /**
         * Contains properties of person.
         *
         * @author Baptiste Wicht
         */
        interface Person {
            String NAME = "person.name";
            String FIRST_NAME = "person.firstname";
            String COUNTRY = "person.country";
            String NOTE = "person.note";

            int NAME_MAX_LENGTH = 100;
            int FIRST_NAME_MAX_LENGTH = 100;
        }

        /**
         * Contains properties of the Form001.
         *
         * @author Baptiste Wicht
         */
        interface Form001 {
            String TITLE = "reports.films.title";
        }
    }

    /**
     * Contains all the reports of the module.
     *
     * @author Baptiste Wicht
     */
    public interface Report {
        String FORM001_TABLE = "org/jtheque/films/reports/Form001_Table.jrxml";
        String FORM001_TEXT = "org/jtheque/films/reports/Form001_Text.jrxml";
        String FORM001_PAGE = "org/jtheque/films/reports/Form001_Page.jrxml";
        String FORM001_WEB = "org/jtheque/films/reports/Form001_Web.jrxml";
        String FORM001_DATA = "org/jtheque/films/reports/Form001_Data.jrxml";

        String FORM002_DVD_STANDARD = "Form002_DVD_Standard.jrxml";
        String FORM002_DVD_SLIM = "Form002_DVD_Slim.jrxml";
        String FORM002_CD_STANDARD = "Form002_CD_Standard.jrxml";
        String FORM002_CD_SLIM = "Form002_CD_Slim.jrxml";

        /**
         * Contains the parameters of the form001.
         *
         * @author Baptiste Wicht
         */
        interface Form001_Parameters {
            String TITLE = "REPORT_TITLE";
            String KIND = "REPORT_KIND";
            String FILM = "REPORT_FILM";
            String TYPE = "REPORT_TYPE";
            String REALIZER = "REPORT_REALIZER";
            String YEAR = "REPORT_YEAR";
            String DURATION = "REPORT_DURATION";
            String LANGUAGE = "REPORT_LANGUAGE";
            String ACTORS = "REPORT_ACTORS";
        }
    }

    /**
     * A properties class for all file's properties.
     *
     * @author Baptiste Wicht
     */
    public interface Files {
        String MINIATURE_FOLDER = Managers.getCore().getFolders().getApplicationFolder() + "/miniatures/";

        /**
         * A properties class for file's types.
         *
         * @author Baptiste Wicht
         */
        enum FileType {
            XML,
            XLS,
            PDF,
            TXT,
            HTML,
            JTF,
            CSV,
            RTF,
            JTFE;

            /**
             * Return the FileType enum value from the String value.
             *
             * @param fileType The string value of the enum.
             * @return The FileType enum value.
             */
            public static FileType fromString(String fileType) {
                if ("xls".equalsIgnoreCase(fileType)) {
                    return XLS;
                } else if ("xml".equalsIgnoreCase(fileType)) {
                    return XML;
                } else if ("pdf".equalsIgnoreCase(fileType)) {
                    return PDF;
                } else if ("txt".equalsIgnoreCase(fileType)) {
                    return TXT;
                } else if ("html".equalsIgnoreCase(fileType)) {
                    return JTF;
                } else if ("jtf".equalsIgnoreCase(fileType)) {
                    return JTF;
                } else if ("csv".equalsIgnoreCase(fileType)) {
                    return CSV;
                } else if ("rtf".equalsIgnoreCase(fileType)) {
                    return RTF;
                } else if ("jtfe".equalsIgnoreCase(fileType)) {
                    return JTFE;
                }

                return null;
            }
        }

        /**
         * A properties class for versions of file.
         *
         * @author Baptiste Wicht
         */
        interface Versions {
            /**
             * A properties class for versions of XML File.
             *
             * @author Baptiste Wicht
             */
            interface XML {
                int FIRST = 1;
                int SECOND = 2;
                int THIRD = 3;
            }

            /**
             * A properties class for versions of JTF File.
             *
             * @author Baptiste Wicht
             */
            interface JTF {
                int FIRST = 1;
                int SECOND = 1;
            }
        }

        /**
         * A properties class for JT's File.
         *
         * @author Baptiste Wicht
         */
        interface JT {
            long JTCATEGORYSEPARATOR = -89569876428459544L;
            long JTINTERNSEPARATOR = -95684111123647897L;

            int CONTENT = 5;
            int NO_CONTENT = 10;

            String JTKEY = "1W@JTHEQUE@W1";
        }
    }

    /**
     * A properties class for sites.
     *
     * @author Baptiste Wicht
     */
    public enum Site {
        CINEMOVIES("Cinemovies"),
        ALLOCINE("Allocine"),
        MOVIESCOVERS("MoviesCovers"),
        DVDFR("DVDFr");

        private final String value;

        /**
         * Construct a new Site.
         *
         * @param value The string value of the site.
         */
        Site(String value) {
            this.value = value;
        }

        /**
         * Return the string value of the site.
         *
         * @return The string value of the site.
         */
        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}