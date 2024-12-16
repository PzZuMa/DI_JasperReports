package org.example.retoconjuntohibernate.dao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;

public class ReportServices {
    private static Connection conn;

    public ReportServices(Connection conn) {
        this.conn = conn;
    }

    public static void mostrarInformeListadoPeliculas() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("ListadoPeliculas.jasper", null, conn);
//            JasperExportManager.exportReportToPdfFile(jp, "ListadoPeliculas.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

//    public static void generarInforme() {
//        try {
//            JasperPrint jp = JasperFillManager.fillReport("starmap.jasper", null, conn);
//            JasperExportManager.exportReportToPdfFile(jp, "starmap.pdf");
//        } catch (JRException e) {
//            e.printStackTrace();
//        }
//    }
}
