/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.atlantis.board;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.PlayerToken;
import fr.miage.atlantis.logic.GameLogic;

/**
 *
 * @author Loris
 */
public class NullGameLogic extends GameLogic {

    @Override
    public void boot() {
        
    }

    @Override
    public void requestPick(EntityPickRequest entRq, TilePickRequest tileRq) {
     
    }

    @Override
    public void onEntityPicked(GameEntity ent) {
      
    }

    @Override
    public void onTilePicked(GameTile tile) {
       
    }

    public void onTurnStart(Player p) {
        
    }

    public void onPlayTileAction(GameTile tile, TileAction action) {
        
    }

    public void onDiceRoll(int face) {
        
    }

    public void onSinkTile(GameTile tile) {
        
    }

    public void onEntityAction(GameEntity source, GameEntity target, int action) {
        
    }

    public void onEntitySpawn(GameEntity spawned) {
        
    }

    public void onBoardBoat(PlayerToken player, Boat b) {
        
    }

    public void onUnitDie(GameEntity zombie) {
        
    }

    @Override
    public void onPlayerDismountBoat(PlayerToken player, Boat b) {
        
    }

    public void onInitialTokenPut(PlayerToken pt) {
   
    }
    
}