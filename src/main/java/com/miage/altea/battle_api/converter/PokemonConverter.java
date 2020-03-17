package com.miage.altea.battle_api.converter;

import com.miage.altea.battle_api.bo.BattlePokemon;
import com.miage.altea.battle_api.bo.Pokemon;
import com.miage.altea.battle_api.bo.PokemonType;
import com.miage.altea.battle_api.service.PokemonTypeServiceImpl;
import com.miage.altea.battle_api.utils.SequenceGenerator;
import com.miage.altea.battle_api.utils.StatsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PokemonConverter {

    @Autowired
    private PokemonTypeServiceImpl pokemonTypeService;

    public BattlePokemon PokemonToBattlePokemon(Pokemon pokemon){
        PokemonType pokemonType = pokemonTypeService.getPokemon(pokemon.getPokemonTypeId());
        return BattlePokemon.builder()
                .alive(true)
                .ko(false)
                .level(pokemon.getLevel())
                .type(pokemonType)
                .hp(StatsCalculator.calculateHP(pokemonType.getStats().getHp(),pokemon.getLevel()))
                .attack(StatsCalculator.calculateStat(pokemonType.getStats().getAttack(),pokemon.getLevel()))
                .defence(StatsCalculator.calculateStat(pokemonType.getStats().getDefense(),pokemon.getLevel()))
                .speed(StatsCalculator.calculateStat(pokemonType.getStats().getSpeed(),pokemon.getLevel()))
                .maxHp(StatsCalculator.calculateHP(pokemonType.getStats().getHp(),pokemon.getLevel()))
                .id(SequenceGenerator.getNext())
                .build();
    }


}
