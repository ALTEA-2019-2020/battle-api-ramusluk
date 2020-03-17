package com.miage.altea.battle_api.service;

import com.miage.altea.battle_api.bo.Battle;
import com.miage.altea.battle_api.bo.BattlePokemon;
import com.miage.altea.battle_api.bo.BattleTrainer;
import com.miage.altea.battle_api.bo.StatsCalculator;
import com.miage.altea.battle_api.converter.TrainerConverter;
import com.miage.altea.battle_api.exceptions.ApplicationException;
import com.miage.altea.battle_api.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BattleServiceImpl implements BattleService {

    public TrainersServiceImpl trainersService;
    public BattleRepository battleRepository;
    private TrainerConverter trainerConverter;

    @Autowired
    public BattleServiceImpl(TrainersServiceImpl trainersService, TrainerConverter trainerConverter) {
        this.trainersService = trainersService;
        this.trainerConverter = trainerConverter;
        this.battleRepository = BattleRepository.getInstance();
    }

    @Override
    public String createBattle(String trainerName, String opponentName) {
        BattleTrainer trainer = trainerConverter.trainerToBattleTrainer(trainersService.getTrainerEntity(trainerName));
        BattleTrainer opponent = trainerConverter.trainerToBattleTrainer(trainersService.getTrainerEntity(opponentName));
        if(trainer == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, trainerName + " not found");
        if(opponent == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, opponentName + " not found");
        this.setFirstPlayer(trainer,opponent);
        String uuid = UUID.randomUUID().toString();
        Battle battle = new Battle(trainer, opponent, uuid);
        this.battleRepository.addBattle(battle);
        return uuid;
    }

    private void setFirstPlayer(BattleTrainer trainer, BattleTrainer opponent) {
        if(trainer.getTeam().get(0).getSpeed()>=opponent.getTeam().get(0).getSpeed()){
            trainer.setNextTurn(true);
            opponent.setNextTurn(false);
        }
        else{
            opponent.setNextTurn(true);
            trainer.setNextTurn(false);
        }
    }

    @Override
    public List<Battle> getAllBattles() {
        return this.battleRepository.getAllBattles();
    }

    @Override
    public Battle getBattle(String uuid) {
        Battle battle = this.battleRepository.getBattle(uuid);
        if(battle == null)
            throw new ApplicationException(HttpStatus.NOT_FOUND, uuid+" not found");
        return battle;
    }

    @Override
    public Battle attack(String uuid, String trainerName) {
        Battle battle = this.getBattle(uuid);
        int val = checkTrainerRound(battle,trainerName);
        BattleTrainer battletrainer = this.checkIfTrainersHaveAPokemonAliveToFight(battle);
        if(battletrainer != null)
            throw new ApplicationException(HttpStatus.METHOD_NOT_ALLOWED, battletrainer.getName()+" has no more pokemon.");
        if(val % 2 == 1 ){
            if(val == 1){ //attaquant == Opponent
                doAttackRound(battle.getOpponent(), battle.getTrainer());
            }if(val == 3){ // attaquant == trainer
                doAttackRound(battle.getTrainer(), battle.getOpponent());
            }
        }else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, trainerName+" it's not your turn.");
        }
        checkIfTrainersHaveAPokemonAliveToFight(battle);
        return battle;
    }

    private BattleTrainer checkIfTrainersHaveAPokemonAliveToFight(Battle battle){
        if(!battle.getTrainer().updateTeamAlive())
            return battle.getTrainer();
        if(!battle.getOpponent().updateTeamAlive())
            return battle.getOpponent();
        return null;
    }

    private int checkTrainerRound(Battle battle, String trainerName){
        if(battle.getOpponent().getName().equals(trainerName) ){
            if(battle.getOpponent().isNextTurn()){
                return 1; //attaquant == Opponent
            }else
                return 2;
        }
        if(battle.getTrainer().getName().equals(trainerName) ){
            if(battle.getTrainer().isNextTurn()){
                return 3; //attaquant == trainer
            }else
                return 4;
        }
        return 0;
    }

    private void doAttackRound(BattleTrainer attackerTrainer, BattleTrainer defenderTrainer) {
        dealDamages(attackerTrainer, defenderTrainer);
        setNextRound(attackerTrainer, defenderTrainer);
    }

    private void setNextRound(BattleTrainer attackerTrainer, BattleTrainer defenderTrainer) {
        attackerTrainer.setNextTurn(!attackerTrainer.isNextTurn());
        defenderTrainer.setNextTurn(!defenderTrainer.isNextTurn());
    }

    private void dealDamages(BattleTrainer attackerTrainer, BattleTrainer defenderTrainer){
        BattlePokemon attackerPokemon = attackerTrainer.getTeam().get(attackerTrainer.getCurrentPokemon());
        BattlePokemon defenderPokemon = defenderTrainer.getTeam().get(defenderTrainer.getCurrentPokemon());
        if(!defenderPokemon.takeDamages(StatsCalculator.calculateDamage(attackerPokemon, defenderPokemon))) //pokemon is ko
            defenderTrainer.increaseCurrentPokemon();
    }


}
