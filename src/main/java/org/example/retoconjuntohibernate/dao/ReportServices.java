package org.example.retoconjuntohibernate.dao;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class ReportServices {
    private static Connection conn = null;

    static {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/repasoHibernateDB", "root", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarInforme() {
        try {
            generarInforme(hemisferio);
            JasperPrint jp = JasperFillManager.fillReport("starmap.jasper", params, conn);
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public static void generarInforme() {
        try {
            JasperPrint jp = JasperFillManager.fillReport("starmap.jasper", params, conn);
            JasperExportManager.exportReportToPdfFile(jp, "starmap"+hemisferio+".pdf");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
