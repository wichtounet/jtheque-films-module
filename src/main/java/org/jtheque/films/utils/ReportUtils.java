package org.jtheque.films.utils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.jtheque.core.managers.Managers;
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.films.services.impl.utils.file.exports.datasources.FilmsDatasource;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.utils.Constants.Properties.Form001;
import org.jtheque.films.utils.Constants.Report.Form001_Parameters;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
 * Utility class for the report generation.
 *
 * @author Baptiste Wicht
 */
public final class ReportUtils {
    /**
     * Construct a new ReportUtils. This class isn't instanciable.
     */
    private ReportUtils() {
        super();
    }

    /**
     * Configure the Form001.
     *
     * @param jasperReport The report.
     * @param films        The films to include.
     * @return The jasper print.
     * @throws JRException Jasper could throw this exception.
     */
    public static JasperPrint configureForm001(JasperReport jasperReport, Collection<org.jtheque.films.persistence.od.able.Film> films) throws JRException {
        Map<String, String> parameters = new HashMap<String, String>(9);
        parameters.put(Form001_Parameters.TITLE, Managers.getManager(ILanguageManager.class).getMessage(Form001.TITLE));
        parameters.put(Form001_Parameters.KIND, Managers.getManager(ILanguageManager.class).getMessage(Film.KIND));
        parameters.put(Form001_Parameters.FILM, Managers.getManager(ILanguageManager.class).getMessage(Film.TITLE));
        parameters.put(Form001_Parameters.TYPE, Managers.getManager(ILanguageManager.class).getMessage(Film.TYPE));
        parameters.put(Form001_Parameters.REALIZER, Managers.getManager(ILanguageManager.class).getMessage(Film.REALIZER));
        parameters.put(Form001_Parameters.YEAR, Managers.getManager(ILanguageManager.class).getMessage(Film.YEAR));
        parameters.put(Form001_Parameters.DURATION, Managers.getManager(ILanguageManager.class).getMessage(Film.DURATION));
        parameters.put(Form001_Parameters.LANGUAGE, Managers.getManager(ILanguageManager.class).getMessage(Film.LANGUAGE));
        parameters.put(Form001_Parameters.ACTORS, Managers.getManager(ILanguageManager.class).getMessage(Film.ACTORS));

        return JasperFillManager.fillReport(jasperReport, parameters, new FilmsDatasource(films));
    }
}