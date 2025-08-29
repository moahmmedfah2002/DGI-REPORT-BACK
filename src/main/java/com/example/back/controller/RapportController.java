package com.example.back.controller;

import com.example.back.dto.FiltreDto;
import com.example.back.dto.RapportDemandeExtra;
import com.example.back.dto.RapportDto;
import com.example.back.dto.RapportOut;
import com.example.back.entity.Rapport;
import com.example.back.services.RapportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rapport/")
@RequiredArgsConstructor
public class RapportController {
    private final RapportService rapportService;
    @PostMapping("add")
    private void createRapport(@RequestBody RapportDto rapportDto) {
        System.out.println(rapportDto.name());
        System.out.println(rapportDto.query());
        System.out.println(rapportDto.name());
        rapportService.createRapport(rapportDto);

    }
    @GetMapping("getRapportFromSous")
    public List<String> getRapportFromSous(@RequestParam String name) {
        return rapportService.getRapportBySous(name);

    }
    @GetMapping("getFilters")
    public List<FiltreDto> getFilters(@RequestParam String name){
                return  this.rapportService.getFilter(name);
    }


    @PostMapping("getRapport")
    public RapportOut test(@RequestBody RapportDemandeExtra rapportDemandeExtra) throws SQLException, ClassNotFoundException {
        Rapport rapport = rapportService.getRapportByName(rapportDemandeExtra.name());
        ResultSet rs=rapportService.extract(rapport,rapportDemandeExtra.map());
        List<String> columns=new ArrayList<>();
        int nbColumn=rs.getMetaData().getColumnCount();
        for (int i = 1; i <= nbColumn; i++) {
            columns.add(rs.getMetaData().getColumnName(i));
        }

        List<List<Object>> data=new ArrayList<>();
        while (rs.next()) {
            List<Object> row=new ArrayList<>();
            for (int i = 1; i <= nbColumn; i++) {
                row.add(rs.getObject(i));
            }
            data.add(row);
        }
        RapportOut rapportOut=new RapportOut(columns,data);
        return rapportOut ;
    }

}

