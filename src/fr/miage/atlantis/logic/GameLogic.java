/*
 * Copyright (C) 2014 Loris Durand, Guillaume Lesniak, Cristian Sanna,
 *                    Lucie Wiemert
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.miage.atlantis.logic;

import fr.miage.atlantis.GameDice;
import fr.miage.atlantis.Player;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.entities.GameEntity;

/**
 * Classe représentant toute la partie logique du jeu
 * 
 * @author AtlantisTeam
 * @version 1.0
 * @date 03/03/2014  
 */
public abstract class GameLogic implements GameTurnListener {
    
    /**
     * Plateau du jeu
     */
    private GameBoard mBoard;
    
    /**
     * Dés du jeu
     */
    private GameDice mDice;
    
    /**
     * Log des tours de jeu
     */
    private GameLog mLog;
    
    /**
     * Tour de jeu actuel
     */
    private GameTurn mCurrentTurn;
    
    /**
     * Tableau des joueurs
     */
    private Player[] mPlayers;
    
    
    
    /**
     * Constructeur de GameLogic
     * 
     */
    public GameLogic() {
        mBoard = new GameBoard();
        mDice = GameDice.createDefault();
        mLog = new GameLog();
    }
    
    
    
    /**
     * Initialise les joueurs du jeu
     * 
     * @param players Tableau des pseudo des joueurs 
     */
    public void prepareGame(String[] players) {
        mPlayers = new Player[players.length];
        for (int i = 0; i < mPlayers.length; i++) {
            mPlayers[i] = new Player(players[i], i+1);
        }
    }
    
    
    /**
     * Initialise le premier tour de jeu
     * 
     */
    public void startGame() {
        Player p=this.mPlayers[0];
        this.mCurrentTurn=new GameTurn(this,p);
        this.mCurrentTurn.startTurn();        
    }
    
    
    /**
     * Stocke le tour présent, génère le suivant et le démarre via start()
     * 
     */
    public void nextTurn() {        
        this.mLog.logTurn(mCurrentTurn);
        Player p=this.mCurrentTurn.getPlayer();         
        this.mCurrentTurn=new GameTurn(this,this.nextPlayer(p));
        //Lance le nouveau tour 
        this.mCurrentTurn.startTurn();                                    //@TODO : Verifier si cela se fait bien ici    
    }
    
    
    /**
     * Retourne le joueur suivant
     * 
     * @param p Joueur Courant
     * @return Joueur suivant
     */
    public Player nextPlayer(Player p){
        Player next=null;
        for(int i = 0 ; i < this.mPlayers.length ; i++){
            if(p.equals(this.mPlayers[i])){
                if(i + 1 == this.mPlayers.length){
                    next=this.mPlayers[0];
                }else{
                    next=this.mPlayers[i+1];
                }                
            }
        }        
        return next;
    }
    
    
    /**
     * Retourne true si le tour est bien terminé
     * 
     * @return True si le tour est fini, false sinon
     */
    public boolean isFinished() {
        
        
        return false;        
    }
    
    
    /**
     * Actions a effectuer lors du deplacement de ent sur dest.
     * 
     * @param ent Entity a deplacer
     * @param dest Tile de destination
     */
    @Override
    public void onUnitMove(final GameEntity ent, final GameTile dest) {
        ent.moveToTile(this, dest);
    }
    
    
    
    
    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    
    
    public GameBoard getBoard() {
        return mBoard;
    }
    
    public GameTurn getCurrentTurn() {
        return mCurrentTurn;
    }    
    //--------------------------------------------------------------------------
    
    
    
    
    //--------------------------------------------------------------------------
    //METHODES ABSTRAITES                                                      |
    //--------------------------------------------------------------------------
    
    
    /**    
     * Methode permettant de lancer une partie 
     */
    public abstract void boot();
    //--------------------------------------------------------------------------
}
