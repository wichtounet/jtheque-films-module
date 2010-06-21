package org.jtheque.films.services.impl.utils.file.exports;

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
import org.jtheque.core.managers.log.ILoggingManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.utils.Constants.Files.FileType;
import org.jtheque.films.utils.Constants.Report;
import org.jtheque.films.utils.ReportUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * An exporter to XLS.
 *
 * @author Baptiste Wicht
 */
public final class CSVExporter extends AbstractExporter<Film> {
    @Override
    public boolean canExportTo(FileType fileType) {
        return fileType == FileType.CSV;
    }

    @Override
    public void export(String path) {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream(Report.FORM001_DATA));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            JasperPrint jasperPrint = ReportUtils.configureForm001(jasperReport, getDatas());

            JRCsvExporter cvsExporter = new JRCsvExporter();
            cvsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            cvsExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);

            cvsExporter.exportReport();
        } catch (JRException e) {
            Managers.getManager(ILoggingManager.class).getLogger(getClass()).error(e);
        }
    }
}