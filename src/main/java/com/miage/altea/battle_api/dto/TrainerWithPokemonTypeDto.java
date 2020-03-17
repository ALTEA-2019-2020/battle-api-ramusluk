package com.miage.altea.battle_api.dto;

import java.util.List;

public class TrainerWithPokemonTypeDto {

    private String name;

    private List<PokemonDto> team;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PokemonDto> getTeam() {
        return team;
    }

    public void setTeam(List<PokemonDto> team) {
        this.team = team;
    }
}
