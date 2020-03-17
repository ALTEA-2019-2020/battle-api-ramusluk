package com.miage.altea.battle_api.service;


import com.miage.altea.battle_api.bo.PokemonType;

import java.util.List;

public interface PokemonTypeService {

    List<PokemonType> listPokemonsTypes();
    PokemonType getPokemon(int id);

}
