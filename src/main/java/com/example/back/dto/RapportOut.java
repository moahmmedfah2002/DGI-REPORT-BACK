package com.example.back.dto;

import com.example.back.entity.Rapport;

import java.util.List;

public record RapportOut(List<String> col,List<List<Object>> rows) {
}
