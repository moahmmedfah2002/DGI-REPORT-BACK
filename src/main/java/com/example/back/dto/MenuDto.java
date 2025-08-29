package com.example.back.dto;


import java.util.List;

public record MenuDto(int id, String name, List<Integer> sousMenu) {

}
