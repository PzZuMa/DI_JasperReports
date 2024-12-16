package org.example.retoconjuntohibernate.dao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.util.HashMap;

public class ReportServices {
    private static Connection conn;

    public ReportServices(Connection conn) {
        this.conn = conn;
    }

    public static void informeListadoPeliculas() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("H1_ListadoPeliculas.jasper", null, conn);
            JasperExportManager.exportReportToPdfFile(jp, "H1_ListadoPeliculas.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void informePeliculasDanadas() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("H2_PeliculasDañadas.jasper", null, conn);
            JasperExportManager.exportReportToPdfFile(jp, "H2_PeliculasDañadas.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void informePeliculasMasCopias() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("H3_PeliculasMasCopias.jasper", null, conn);
            JasperExportManager.exportReportToPdfFile(jp, "H3_PeliculasMasCopias.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void informeInfoCopia(Long id) {
        var params = new HashMap();
        params.put("copia_seleccionada", id);
        try {
            JasperPrint jp = JasperFillManager.fillReport("H4_InfoCopia.jasper", params, conn);
            JasperExportManager.exportReportToPdfFile(jp, "H4_InfoCopia.pdf");
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }


}
