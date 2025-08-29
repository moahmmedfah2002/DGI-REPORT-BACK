package com.example.back.services;

import com.example.back.dto.FiltreDto;
import com.example.back.dto.RapportDemandeExtra;
import com.example.back.dto.RapportDto;
import com.example.back.entity.*;
import com.example.back.reposetory.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RapportService {
    public final RepoRapport repoRapport;
    private final RepoSiExt repoSiExt;
    private final  RepoFilter repoFilter;
    public final RepoMenu repoMenu;
    public final RepoSousMenu repoSousMenu;
    public void createRapport(RapportDto rapportDto) {

        Rapport rapport = new Rapport();
        rapport.setName(rapportDto.name());
        rapport.setTitre(rapportDto.titre());
        rapport.setQuery(rapportDto.query());
        List<Filter> f = new ArrayList<>();
        try {
            f = getFilters(rapportDto, rapport);
        }catch (Exception e){
            System.err.println(e.getMessage());

        }
        rapport.setFiltre(f);
        rapport.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));

        SiExterne siExterne = new SiExterne();
        siExterne.setHost(rapportDto.host());
        siExterne.setPort(rapportDto.port());
        siExterne.setDb(rapportDto.db());
        siExterne.setType(rapportDto.type());

        siExterne.setUsername(rapportDto.username_db());
        siExterne.setPassword(rapportDto.password_db());
        repoSiExt.save(siExterne);
        rapport.setSiExterne(siExterne);



        if (rapportDto.menu() != null && !rapportDto.menu().isEmpty()) {
            Menu menu = repoMenu.findByName(rapportDto.menu());
            rapport.setMenu(menu);
        if(!menu.getSous_menus().isEmpty()) {
            SousMenu sousMenu = repoSousMenu.findByName(rapportDto.sousMenus());
            rapport.setSousMenus(sousMenu);
        }

        }
        try {
            repoRapport.save(rapport);
        }
        catch (Exception e) {
            System.err.println(e.getMessage());

        }

    }

    private  List<Filter> getFilters(RapportDto rapportDto, Rapport rapport) {

        List<Filter> f = new ArrayList<>();
        if(rapport.getFiltre()!=null) {
            f.addAll(rapport.getFiltre());
        }

        for(FiltreDto f1: rapportDto.filters()){
            boolean a=true;
            for(Filter f2:f){
                if(Objects.equals(f1.columnName(), f2.getColumnName())){
                    a=false;
                    break;

                }
            }
            if(a){
                Filter newFilter=new Filter();
                newFilter.setColumnName(f1.columnName());
                newFilter.setType(f1.type_col());
                f.add(newFilter);
                this.repoFilter.save(newFilter);



            }

        }
        return f;
    }

    public ResultSet extract(Rapport rapport, Map<String,String> filterContent) throws ClassNotFoundException, SQLException {

        if(Objects.equals(rapport.getSiExterne().getType(), "mysql")) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection dm= DriverManager.getConnection("jdbc:"+
                            rapport.getSiExterne().getType()+
                            "://"+
                            rapport.getSiExterne().getHost()+
                            ":"+
                            rapport.getSiExterne().getPort()+
                            "/"+
                            rapport.getSiExterne().getDb(),
                    rapport.getSiExterne().getUsername(),
                    rapport.getSiExterne().getPassword());
            Statement stmt = dm.createStatement();
            List<Filter> f = rapport.getFiltre();
            String query= rapport.getQuery();
            query=query.toLowerCase();
            System.out.println(query);
            int index= query.indexOf("where");
            System.out.println(index);
            ResultSet rs;
            if(index!=-1){
            String query1 = query.substring(0, index+5);
            query1+="   ";
            for (Filter i :f) {

                if( filterContent.get(i.getColumnName())!=null && filterContent.get(i.getColumnName())!=""  ) {
                    query1 += " " + i.getColumnName() + " = '" + filterContent.get(i.getColumnName()) + "'   AND";
                    System.out.println(query1);
                }






            }
            query1=query1.substring(0, query.length()-3);
            System.out.println(query1);
            String query2= query.substring(index+4);
            System.out.println(query2);
            String queryRes=query1+" "+query2;
             rs = stmt.executeQuery(queryRes);}
            else {
                query+="   ";
                if(!f.isEmpty()) {
                    query += " where";
                }

                for (Filter i :f) {
                    System.out.println(filterContent.isEmpty());
                    if( filterContent.get(i.getColumnName())!=null && filterContent.get(i.getColumnName())!=""  ) {
                        query += " " + i.getColumnName() + " = '" + filterContent.get(i.getColumnName()) + "'   AND";
                    }
                }
                query=query.substring(0, query.length()-3);
                System.out.println(query);
                rs = stmt.executeQuery(query);
            }

            return rs;
        }
        if(Objects.equals(rapport.getSiExterne().getType(), "oracle")) {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("jdbc:"+
                    rapport.getSiExterne().getType()+
                    ":thin:@//"+
                    rapport.getSiExterne().getHost()+
                    ":"+
                    rapport.getSiExterne().getPort()+
                    "/"+rapport.getSiExterne().getDb());
           Connection  dm= DriverManager.getConnection("jdbc:"+
                           rapport.getSiExterne().getType()+
                           ":thin:@//"+
                           rapport.getSiExterne().getHost()+
                           ":"+
                           rapport.getSiExterne().getPort()+
                           "/"+rapport.getSiExterne().getDb(),
                    rapport.getSiExterne().getUsername(),
                    rapport.getSiExterne().getPassword());
            Statement stmt = dm.createStatement();
            List<Filter> f = rapport.getFiltre();
            String query= rapport.getQuery();
            query=query.toLowerCase();
            int index= query.indexOf("where");
            ResultSet rs;
            if(index!=-1){
                String query1 = query.substring(0, index);
                query1+="   ";
                for (Filter i :f) {

                    if( filterContent.get(i.getColumnName())!=null && filterContent.get(i.getColumnName())!=""  ) {
                        query1 += " " + i.getColumnName() + " = '" + filterContent.get(i.getColumnName()) + "'   AND";
                    }






                }
                query1=query1.substring(0, query.length()-3);
                String query2= query.substring(index+4);
                String queryRes=query1+" "+query2;
                rs = stmt.executeQuery(queryRes);}
            else {
                query+="   ";
                if(!f.isEmpty()) {
                    query += " where";
                }

                for (Filter i :f) {
                    System.out.println(filterContent.isEmpty());
                    if( filterContent.get(i.getColumnName())!=null && filterContent.get(i.getColumnName())!=""  ) {
                        query += " " + i.getColumnName() + " = '" + filterContent.get(i.getColumnName()) + "'   AND";
                    }
                }
                query=query.substring(0, query.length()-3);
                System.out.println(query);
                rs = stmt.executeQuery(query);
            }

            return rs;
        }
        if(Objects.equals(rapport.getSiExterne().getType(), "sqlserver")) {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("jdbc:"+
                    rapport.getSiExterne().getType()+
                    "://"+
                    rapport.getSiExterne().getHost()+
                    ":"+
                    rapport.getSiExterne().getPort()+
                    ";databaseName="+
                    rapport.getSiExterne().getDb()+
                    ";trustServerCertificate=true;user=SA;password=cdgPassword@2025;encrypt=true;loginTimeout=30;");
            Connection dm= DriverManager.getConnection("jdbc:"+
                            rapport.getSiExterne().getType()+
                            "://"+
                            rapport.getSiExterne().getHost()+
                            ":"+
                            rapport.getSiExterne().getPort()+
                            ";databaseName="+
                            rapport.getSiExterne().getDb()+
                            ";trustServerCertificate=true;",
                    rapport.getSiExterne().getUsername(),
                    rapport.getSiExterne().getPassword());
            Statement stmt = dm.createStatement();
            List<Filter> f = rapport.getFiltre();
            String query= rapport.getQuery();
            query=query.toLowerCase();
            int index= query.indexOf("where");
            ResultSet rs;
            if(index!=-1){
                String query1 = query.substring(0, index);
                query1+="   ";
                for (Filter i :f) {

                    if( filterContent.get(i.getColumnName())!=null && filterContent.get(i.getColumnName())!=""  ) {
                        query1 += " " + i.getColumnName() + " = '" + filterContent.get(i.getColumnName()) + "'   AND";
                    }






                }
                query1=query1.substring(0, query.length()-3);
                String query2= query.substring(index+4);
                String queryRes=query1+" "+query2;
                rs = stmt.executeQuery(queryRes);}
            else {
                query+="   ";
                if(!f.isEmpty()) {
                    query += " where";
                }

                for (Filter i :f) {
                    System.out.println(filterContent.isEmpty());
                    if( filterContent.get(i.getColumnName())!=null && filterContent.get(i.getColumnName())!=""  ) {
                        query += " " + i.getColumnName() + " = '" + filterContent.get(i.getColumnName()) + "'   AND";
                    }
                }
                query=query.substring(0, query.length()-3);
                System.out.println(query);
                rs = stmt.executeQuery(query);
            }

            return rs;
        }

        return null;

    }
    public Rapport getRapportById(int id) {
        return repoRapport.findById_rapport(id);
    }
    public Rapport getRapportByName(String name) {
        return repoRapport.findByName(name);
    }
    public List<String> getRapportBySous(String name) {
        SousMenu sousMenu = repoSousMenu.findByName(name);
       List<Rapport> rapports= repoRapport.findBySousMenus(sousMenu);
       return rapports.stream().map(Rapport::getName).toList();
    }
    public List<FiltreDto> getFilter(String name){
        Rapport rapport=repoRapport.findByName(name);
        if(rapport.getFiltre()==null){
            System.out.println("no filters");
        }else {
            rapport.getFiltre().forEach(f->{
                System.out.println("col>> "+f.getColumnName());

            });
        }
        System.out.println("filters: "+rapport.getFiltre());
        List<FiltreDto> filtreDtos= new ArrayList<>();
        for (Filter f :rapport.getFiltre()) {
            System.out.println(f.getColumnName());
            FiltreDto filtreDto=new FiltreDto(f.getColumnName(),f.getType());
            filtreDtos.add(filtreDto);

        }
        return filtreDtos;
    }


}
