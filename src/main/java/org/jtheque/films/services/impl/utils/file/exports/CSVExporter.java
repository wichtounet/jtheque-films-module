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
