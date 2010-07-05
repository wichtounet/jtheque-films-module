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
import org.jtheque.core.managers.language.ILanguageManager;
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.films.services.impl.utils.file.exports.datasources.FilmsDatasource;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.films.utils.Constants.Properties.Film;
import org.jtheque.films.utils.Constants.Properties.Form001;
import org.jtheque.films.utils.Constants.Report;
import org.jtheque.films.utils.Constants.Report.Form001_Parameters;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * An exporter to XLS.
 *
 * @author Baptiste Wicht
 */
public final class XLSExporter extends AbstractExporter<org.jtheque.films.persistence.od.able.Film> {
    @Override
    public boolean canExportTo(FileType fileType) {
        return fileType == FileType.XLS;
    }

    @Override
    public void export(String path) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream(Report.FORM001_TABLE));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, getParameters(), new FilmsDatasource(getDatas()));

            JRXlsAbstractExporter xlsExporter = new JExcelApiExporter();
            xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            xlsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
            xlsExporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            xlsExporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            xlsExporter.setParameter(JExcelApiExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.TRUE);

            xlsExporter.exportReport();
        } catch (JRException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }

    /**
     * Return the parameters for the jasper report.
     *
     * @return The parameters for the jasper report.
     */
    private static Map<String, String> getParameters() {
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
        return parameters;
    }
}
