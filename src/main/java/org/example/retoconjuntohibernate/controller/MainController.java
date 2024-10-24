package org.example.retoconjuntohibernate.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.example.retoconjuntohibernate.dao.CopiaDAO;
import org.example.retoconjuntohibernate.dao.HibernateUtil;
import org.example.retoconjuntohibernate.dao.Session;

import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
    @javafx.fxml.FXML
    private ListView listaID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CopiaDAO cd = new CopiaDAO(HibernateUtil.getSessionFactory());
//        listaID.getItems().addAll(cd.findAll());
//        listaID.getItems().addAll(cd.findCopiasByUser(Session.user.getId()));
    }
}
