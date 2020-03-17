package com.miage.altea.battle_api.converter;

import com.miage.altea.battle_api.bo.BattleTrainer;
import com.miage.altea.battle_api.bo.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TrainerConverter {

    @Autowired
    private PokemonConverter pokemonConverter;

    public BattleTrainer trainerToBattleTrainer(Trainer trainer) {
        return BattleTrainer.builder()
                .name(trainer.getName())
                .nextTurn(false)
                .team(trainer.getTeam().stream().map(pokemon -> pokemonConverter.PokemonToBattlePokemon(pokemon)).collect(Collectors.toList()))
                .build();
    }

}
