package com.example.back.controller;

import com.example.back.entity.Rapport;
import com.example.back.services.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final RapportService rapportService;
//    @GetMapping
//    public String test(@RequestParam int id) throws SQLException, ClassNotFoundException {
//        Rapport rapport = rapportService.getRapportById(id);
//        ResultSet rs=rapportService.extract(rapport);
//        List<String> columns=new ArrayList<String>();
//        int nbColumn=rs.getMetaData().getColumnCount();
//        for (int i = 1; i <= nbColumn; i++) {
//            columns.add(rs.getMetaData().getColumnName(i));
//        }
//        String result="";
//        while (rs.next()) {
//            for (int i = 1; i <= nbColumn; i++) {
//                result+=columns.get(i - 1) +" >> " + rs.getObject(i)+"\n";
//            }
//        }
//        return result;
//    }
}
