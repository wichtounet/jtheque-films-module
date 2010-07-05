package org.jtheque.films.services.impl;

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
import org.jtheque.core.managers.log.IJThequeLogger;
import org.jtheque.core.managers.log.Logger;
import org.jtheque.core.managers.view.able.IViewManager;
import org.jtheque.films.persistence.od.able.Film;
import org.jtheque.films.services.able.ICoverService;
import org.jtheque.films.services.impl.utils.cover.Format;
import org.jtheque.films.services.impl.utils.file.exports.datasources.FilmsDatasource;
import org.jtheque.films.services.impl.utils.file.jt.FileFilterFactory;
import org.jtheque.films.utils.Constants;
import org.jtheque.utils.collections.ArrayUtils;

import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * A cover service implementation.
 *
 * @author Baptiste Wicht
 */
public final class CoverService implements ICoverService {
    private final Format[] formats;

    private Image image;
    private JasperPrint print;
    private Film film;
    private Format format;

    @Logger
    private IJThequeLogger logger;

    /**
     * Construct a new CoverService.
     */
    public CoverService() {
        super();

        formats = new Format[4];

        formats[0] = new Format("cover.formats.dvd.standard", Constants.Report.FORM002_DVD_STANDARD);
        formats[1] = new Format("cover.formats.dvd.slim", Constants.Report.FORM002_DVD_SLIM);
        formats[2] = new Format("cover.formats.cd.standard", Constants.Report.FORM002_CD_STANDARD);
        formats[3] = new Format("cover.formats.cd.slim", Constants.Report.FORM002_CD_SLIM);
    }

    @Override
    public Format[] getFormats() {
        return ArrayUtils.copyOf(formats);
    }

    @Override
    public Image getReportImage(Film film, Format format) {
        if (film != null && (this.film != film || this.format != format)) {
            this.film = film;
            this.format = format;

            try {
                JasperDesign jasperDesign = JRXmlLoader.load(getClass().getClassLoader().getResourceAsStream("org/jtheque/films/reports/" + format.getForm()));
                JasperReport report = JasperCompileManager.compileReport(jasperDesign);

                Map<String, Object> parameters = new HashMap<String, Object>(3);
                parameters.put("REPORT_REALIZER_HEADER", Managers.getManager(ILanguageManager.class).getMessage("cover.realizer"));
                parameters.put("REPORT_ACTORS_HEADER", Managers.getManager(ILanguageManager.class).getMessage("cover.actors"));
                parameters.put("REPORT_DVD_LOGO", getClass().getClassLoader().getResourceAsStream("org/jtheque/films/images/dvd-logo-h.gif"));

                print = JasperFillManager.fillReport(report, parameters, new FilmsDatasource(film));

                image = JasperPrintManager.printPageToImage(print, 0, 1.0f);
            } catch (JRException e) {
                logger.error(e);
            }
        }

        return image;
    }

    @Override
    public void printCurrentReport() {
        try {
            AttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();

            printRequestAttributeSet.add(new Copies(1));
            printRequestAttributeSet.add(new JobName("JTheque Print Report Job", null));

            JRPrintServiceExporter exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.TRUE);
            exporter.exportReport();
        } catch (JRException e) {
            logger.error(e);
        }
    }

    @Override
    public void exportCurrentReportToPDF() {
        String filePath = Managers.getManager(IViewManager.class).chooseFile(FileFilterFactory.getFileFilter(Constants.Files.FileType.PDF));

        try {
            JasperExportManager.exportReportToPdfFile(print, filePath);
        } catch (JRException e) {
            logger.error(e);
        }
    }
}
