package com.example.back;

import com.example.back.entity.Rapport;
import com.example.back.services.RapportService;
import com.example.back.services.ServiceLdap;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootTest

class BackApplicationTests {
    private final RapportService rapportService;

    BackApplicationTests(RapportService rapportService) {
        this.rapportService = rapportService;
    }

    @Test
    void contextLoads() throws SQLException, ClassNotFoundException {
        Rapport rapport = rapportService.getRapportById(104);
        ResultSet rs=rapportService.extract(rapport);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }

    }

}
