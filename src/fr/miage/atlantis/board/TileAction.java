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
package fr.miage.atlantis.board;

import fr.miage.atlantis.entities.Boat;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.entities.SeaSerpent;
import fr.miage.atlantis.entities.Shark;
import fr.miage.atlantis.entities.Whale;
import fr.miage.atlantis.logic.GameLogic;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe représentant les faces arrières des tiles du plateau de jeu
 *
 * @author AtlantisTeam
 * @version 1.0
 * @date 02/03/2014
 */
public class TileAction {

    /**
     * Constantes representant les faces arrières des Tiles du plateau
     */
    public final static int NONE = -1;
    public final static int ENTITY_SHARK = 0;
    public final static int ENTITY_WHALE = 1;
    public final static int ENTITY_BOAT = 2;
    public final static int ENTITY_SEASERPENT = 3;
    /**
     * Action de déplacer un animal au début de son tour
     */
    public final static int ACTION_MOVE_ANIMAL = 0;
    /**
     * Action déclenchable permettant d'annuler l'action d'un animal sur le
     * joueur
     */
    public final static int ACTION_CANCEL_ANIMAL = 1;
    /**
     * Action immédiate faisant apparaître une nouvelle entité (shark, whale,
     * boat)
     */
    public final static int ACTION_SPAWN_ENTITY = 2;
    /**
     * Action de déplacer un nageur au début de son tour (dauphin)
     */
    public final static int ACTION_BONUS_SWIM = 3;
    /**
     * Action de déplacer un bateau au début de son tour
     */
    public final static int ACTION_BONUS_BOAT = 4;
    /**
     * Action du tourbillon
     */
    public final static int ACTION_WHIRL = 5;
    /**
     * Action de la tile volcan
     */
    public final static int ACTION_VOLCANO = 6;
    //--------------------------------------------------------------------------
    //TileAction sous les Tiles Beach
    //--------------------------------------------------------------------------
    /**
     * Nombre de tile action de type Spawn Animal Shark
     */
    public final static int TILE_COUNT_ANIMAL_SHARK_UNDERBEACHTILE = 3;
    /**
     * Nombre de tile action de type Spawn Animal Whale
     */
    public final static int TILE_COUNT_ANIMAL_WHALE_UNDERBEACHTILE = 3;
    /**
     * Nombre de tile action de type Move Animal Shark
     */
    private final static int TILE_COUNT_MOVE_SHARK_UNDERBEACHTILE = 1;
    /**
     * Nombre de tile action de type Move Animal Whale
     */
    private final static int TILE_COUNT_MOVE_WHALE_UNDERBEACHTILE = 1;
    /**
     * Nombre de tile action de type Move Animal Seaserpent
     */
    private final static int TILE_COUNT_MOVE_SEASERPENT_UNDERBEACHTILE = 1;
    /**
     * Nombre de tile action de type Cancel Animal Shark
     */
    private final static int TILE_COUNT_CANCEL_SHARK_UNDERBEACHTILE = 1;
    /**
     * Nombre de tile action de type Cancel Animal Whale
     */
    private final static int TILE_COUNT_CANCEL_WHALE_UNDERBEACHTILE = 0;
    /**
     * Nombre de tile action de type Spawn Boat
     */
    private final static int TILE_COUNT_SPAWN_BOAT_UNDERBEACHTILE = 1;
    /**
     * Nombre de tile action de type BonusSwim
     */
    private final static int TILE_COUNT_BONUS_SWIM_UNDERBEACHTILE = 3;
    /**
     * Nombre de tile action de type BonusBoat
     */
    private final static int TILE_COUNT_BONUS_BOAT_UNDERBEACHTILE = 2;
    /**
     * Nombre de tile action de type tourbillon
     */
    private final static int TILE_COUNT_WHIRL_UNDERBEACHTILE = 0;
    /**
     * Nombre de tile action de type volcan
     */
    private final static int TILE_COUNT_VOLCANO_UNDERBEACHTILE = 0;
    //--------------------------------------------------------------------------
    //TileAction sous les Tiles Forest
    //--------------------------------------------------------------------------
    /**
     * Nombre de tile de type Spawn Requin
     */
    public final static int TILE_COUNT_ANIMAL_SHARK_UNDERFORESTTILE = 2;
    /**
     * Nombre de tile action de type Spawn Animal Whale
     */
    public final static int TILE_COUNT_ANIMAL_WHALE_UNDERFORESTTILE = 2;
    /**
     * Nombre de tile action de type Move Animal Shark
     */
    private final static int TILE_COUNT_MOVE_SHARK_UNDERFORESTTILE = 1;
    /**
     * Nombre de tile action de type Move Animal Whale
     */
    private final static int TILE_COUNT_MOVE_WHALE_UNDERFORESTTILE = 1;
    /**
     * Nombre de tile action de type Move Animal Seaserpent
     */
    private final static int TILE_COUNT_MOVE_SEASERPENT_UNDERFORESTTILE = 1;
    /**
     * Nombre de tile action de type Cancel Animal Shark
     */
    private final static int TILE_COUNT_CANCEL_SHARK_UNDERFORESTTILE = 1;
    /**
     * Nombre de tile action de type Cancel Animal Whale
     */
    private final static int TILE_COUNT_CANCEL_WHALE_UNDERFORESTTILE = 2;
    /**
     * Nombre de tile action de type Spawn Boat
     */
    private final static int TILE_COUNT_SPAWN_BOAT_UNDERFORESTTILE = 3;
    /**
     * Nombre de tile action de type BonusSwim
     */
    private final static int TILE_COUNT_BONUS_SWIM_UNDERFORESTTILE = 1;
    /**
     * Nombre de tile action de type BonusBoat
     */
    private final static int TILE_COUNT_BONUS_BOAT_UNDERFORESTTILE = 0;
    /**
     * Nombre de tile action de type tourbillon
     */
    private final static int TILE_COUNT_WHIRL_UNDERFORESTTILE = 2;
    /**
     * Nombre de tile action de type volcan
     */
    private final static int TILE_COUNT_VOLCANO_UNDERFORESTTILE = 0;
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    //TileAction sous les Tiles Mountain
    //--------------------------------------------------------------------------
    /**
     * Nombre de tile action de type Spawn Animal Shark
     */
    public final static int TILE_COUNT_ANIMAL_SHARK_UNDERMOUNTAINTILE = 1;
    /**
     * Nombre de tile action de type Spawn Animal Whale
     */
    public final static int TILE_COUNT_ANIMAL_WHALE_UNDERMOUNTAINTILE = 0;
    /**
     * Nombre de tile action de type Move Animal Shark
     */
    private final static int TILE_COUNT_MOVE_SHARK_UNDERMOUNTAINTILE = 0;
    /**
     * Nombre de tile action de type Move Animal Whale
     */
    private final static int TILE_COUNT_MOVE_WHALE_UNDERMOUNTAINTILE = 0;
    /**
     * Nombre de tile action de type Move Animal Seaserpent
     */
    private final static int TILE_COUNT_MOVE_SEASERPENT_UNDERMOUNTAINTILE = 0;
    /**
     * Nombre de tile action de type Cancel Animal Shark
     */
    private final static int TILE_COUNT_CANCEL_SHARK_UNDERMOUNTAINTILE = 1;
    /**
     * Nombre de tile action de type Cancel Animal Whale
     */
    private final static int TILE_COUNT_CANCEL_WHALE_UNDERMOUNTAINTILE = 1;
    /**
     * Nombre de tile action de type Spawn Boat
     */
    private final static int TILE_COUNT_SPAWN_BOAT_UNDERMOUNTAINTILE = 0;
    /**
     * Nombre de tile action de type BonusSwim
     */
    private final static int TILE_COUNT_BONUS_SWIM_UNDERMOUNTAINTILE = 0;
    /**
     * Nombre de tile action de type BonusBoat
     */
    private final static int TILE_COUNT_BONUS_BOAT_UNDERMOUNTAINTILE = 0;
    /**
     * Nombre de tile action de type tourbillon
     */
    private final static int TILE_COUNT_WHIRL_UNDERMOUNTAINTILE = 4;
    /**
     * Nombre de tile action de type volcan
     */
    private final static int TILE_COUNT_VOLCANO_UNDERMOUNTAINTILE = 1;
    /**
     * Defini si l'action est une action a realiser immediatement ou non
     */
    private boolean mIsImmediate;
    /**
     * Defini si l'action se declenche lors d'une attaque adverse
     */
    private boolean mIsTriggerable;
    /**
     * Defini si l'action est l'explosion du volcan
     */
    private boolean mIsVolcano;
    private int mAction;
    private int mEntity;
    private boolean mHasBeenUsed;
    private int mMovesRemaining;
    private GameEntity mInitialEntity;
    private static ArrayList<TileAction> sRandomizerBeach;
    private static ArrayList<TileAction> sRandomizerForest;
    private static ArrayList<TileAction> sRandomizerMountain;
    private static final Logger logger = Logger.getLogger(GameBoard.class.getName());

    private TileAction(int action, int entity, boolean isImmediate,
            boolean isTriggerable, boolean isVolcano) {
        mAction = action;
        mEntity = entity;
        mIsImmediate = isImmediate;
        mIsTriggerable = isTriggerable;
        mIsVolcano = isVolcano;
        mHasBeenUsed = false;
    }

    public void decreaseMovesRemaining() {
        mMovesRemaining--;
    }

    public int getMovesRemaining() {
        return mMovesRemaining;
    }

    public GameEntity getInitialEntity() {
        return mInitialEntity;
    }

    public void setInitialEntity(GameEntity ent) {
        mInitialEntity = ent;
    }

    /**
     * Indique si oui ou non la tile a fini d'être utilisée
     *
     * @return true si la tile est finie d'être utilisée
     */
    public boolean hasBeenUsed() {
        return mHasBeenUsed;
    }

    /**
     * Notifie la tile qu'elle a été complètement utilisée (l'action a terminé)
     */
    public void setUsed() {
        mHasBeenUsed = true;
    }

    public final static class Factory {

        public static TileAction createMoveAnimal(int entity) {
            return new TileAction(ACTION_MOVE_ANIMAL, entity, false, false, false);
        }

        public static TileAction createCancelAnimal(int entity) {
            return new TileAction(ACTION_CANCEL_ANIMAL, entity, false, true, false);
        }

        public static TileAction createSpawnEntity(int entity) {
            return new TileAction(ACTION_SPAWN_ENTITY, entity, true, false, false);
        }

        public static TileAction createBonusBoat() {
            return new TileAction(ACTION_BONUS_BOAT, NONE, false, false, false);
        }

        public static TileAction createBonusSwim() {
            return new TileAction(ACTION_BONUS_SWIM, NONE, false, false, false);
        }

        public static TileAction createWhirl() {
            return new TileAction(ACTION_WHIRL, NONE, true, false, false);
        }

        public static TileAction createVolcano() {
            return new TileAction(ACTION_VOLCANO, NONE, false, false, true);
        }
    }

    /**
     * Genere une action au hasard à placer sous un tile beach
     *
     * @return A random ActionTile
     */
    public static TileAction generateRandomTileActionBeach() {
        if (TileAction.sRandomizerBeach == null || TileAction.sRandomizerBeach.isEmpty()) {
            sRandomizerBeach = new ArrayList<TileAction>();

            for (int i = 0; i < TileAction.TILE_COUNT_ANIMAL_SHARK_UNDERBEACHTILE; i++) {
                sRandomizerBeach.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_SHARK));
            }

            for (int j = 0; j < TileAction.TILE_COUNT_CANCEL_SHARK_UNDERBEACHTILE; j++) {
                sRandomizerBeach.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_SHARK));
            }

            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_SHARK_UNDERBEACHTILE; k++) {
                sRandomizerBeach.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SHARK));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_ANIMAL_WHALE_UNDERBEACHTILE; i++) {
                sRandomizerBeach.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_WHALE));
            }

            for (int j = 0; j < TileAction.TILE_COUNT_CANCEL_WHALE_UNDERBEACHTILE; j++) {
                sRandomizerBeach.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_WHALE));
            }

            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_WHALE_UNDERBEACHTILE; k++) {
                sRandomizerBeach.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_WHALE));
            }

            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_SEASERPENT_UNDERBEACHTILE; k++) {
                sRandomizerBeach.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SEASERPENT));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_SPAWN_BOAT_UNDERBEACHTILE; i++) {
                sRandomizerBeach.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_BOAT));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_BONUS_SWIM_UNDERBEACHTILE; i++) {
                sRandomizerBeach.add(TileAction.Factory.createBonusSwim());
            }

            for (int i = 0; i < TileAction.TILE_COUNT_BONUS_BOAT_UNDERBEACHTILE; i++) {
                sRandomizerBeach.add(TileAction.Factory.createBonusBoat());
            }
            for (int i = 0; i < TileAction.TILE_COUNT_WHIRL_UNDERBEACHTILE; i++) {
                sRandomizerBeach.add(TileAction.Factory.createWhirl());
            }
            for (int i = 0; i < TileAction.TILE_COUNT_VOLCANO_UNDERBEACHTILE; i++) {
                sRandomizerBeach.add(TileAction.Factory.createVolcano());
            }


        }

        int random = new Random().nextInt(TileAction.sRandomizerBeach.size());
        TileAction retour = TileAction.sRandomizerBeach.get(random);
        TileAction.sRandomizerBeach.remove(random);

        return retour;
    }

    /**
     * Genere une action au hasard à placer sous un tile montagne
     *
     * @return A random ActionTile
     */
    public static TileAction generateRandomTileActionMountain() {
        if (TileAction.sRandomizerMountain == null || TileAction.sRandomizerMountain.isEmpty()) {
            sRandomizerMountain = new ArrayList<TileAction>();

            for (int i = 0; i < TileAction.TILE_COUNT_ANIMAL_SHARK_UNDERMOUNTAINTILE; i++) {
                sRandomizerMountain.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_SHARK));
            }
            for (int j = 0; j < TileAction.TILE_COUNT_CANCEL_SHARK_UNDERMOUNTAINTILE; j++) {
                sRandomizerMountain.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_SHARK));
            }
            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_SHARK_UNDERMOUNTAINTILE; k++) {
                sRandomizerMountain.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SHARK));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_ANIMAL_WHALE_UNDERMOUNTAINTILE; i++) {
                sRandomizerMountain.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_WHALE));
            }
            for (int j = 0; j < TileAction.TILE_COUNT_CANCEL_WHALE_UNDERMOUNTAINTILE; j++) {
                sRandomizerMountain.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_WHALE));
            }
            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_WHALE_UNDERMOUNTAINTILE; k++) {
                sRandomizerMountain.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_WHALE));
            }

            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_SEASERPENT_UNDERMOUNTAINTILE; k++) {
                sRandomizerMountain.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SEASERPENT));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_SPAWN_BOAT_UNDERMOUNTAINTILE; i++) {
                sRandomizerMountain.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_BOAT));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_BONUS_SWIM_UNDERMOUNTAINTILE; i++) {
                sRandomizerMountain.add(TileAction.Factory.createBonusSwim());
            }

            for (int i = 0; i < TileAction.TILE_COUNT_BONUS_BOAT_UNDERMOUNTAINTILE; i++) {
                sRandomizerMountain.add(TileAction.Factory.createBonusBoat());
            }

            for (int i = 0; i < TileAction.TILE_COUNT_WHIRL_UNDERMOUNTAINTILE; i++) {
                sRandomizerMountain.add(TileAction.Factory.createWhirl());
            }

            for (int i = 0; i < TileAction.TILE_COUNT_VOLCANO_UNDERMOUNTAINTILE; i++) {
                sRandomizerMountain.add(TileAction.Factory.createVolcano());
            }
        }

        int random = new Random().nextInt(TileAction.sRandomizerMountain.size());
        TileAction retour = TileAction.sRandomizerMountain.get(random);
        TileAction.sRandomizerMountain.remove(random);

        return retour;
    }

    /**
     * Genere une action au hasard à placer sous un tile foret
     *
     * @return A random ActionTile
     */
    public static TileAction generateRandomTileActionForest() {
        if (TileAction.sRandomizerForest == null || TileAction.sRandomizerForest.isEmpty()) {
            sRandomizerForest = new ArrayList<TileAction>();

            for (int i = 0; i < TileAction.TILE_COUNT_ANIMAL_SHARK_UNDERFORESTTILE; i++) {
                sRandomizerForest.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_SHARK));
            }
            for (int j = 0; j < TileAction.TILE_COUNT_CANCEL_SHARK_UNDERFORESTTILE; j++) {
                sRandomizerForest.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_SHARK));
            }
            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_SHARK_UNDERFORESTTILE; k++) {
                sRandomizerForest.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SHARK));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_ANIMAL_WHALE_UNDERFORESTTILE; i++) {
                sRandomizerForest.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_WHALE));
            }
            for (int j = 0; j < TileAction.TILE_COUNT_CANCEL_WHALE_UNDERFORESTTILE; j++) {
                sRandomizerForest.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_WHALE));
            }
            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_WHALE_UNDERFORESTTILE; k++) {
                sRandomizerForest.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_WHALE));
            }

            for (int k = 0; k < TileAction.TILE_COUNT_MOVE_SEASERPENT_UNDERFORESTTILE; k++) {
                sRandomizerForest.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SEASERPENT));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_SPAWN_BOAT_UNDERFORESTTILE; i++) {
                sRandomizerForest.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_BOAT));
            }

            for (int i = 0; i < TileAction.TILE_COUNT_BONUS_SWIM_UNDERFORESTTILE; i++) {
                sRandomizerForest.add(TileAction.Factory.createBonusSwim());
            }

            for (int i = 0; i < TileAction.TILE_COUNT_BONUS_BOAT_UNDERFORESTTILE; i++) {
                sRandomizerForest.add(TileAction.Factory.createBonusBoat());
            }

            for (int i = 0; i < TileAction.TILE_COUNT_WHIRL_UNDERFORESTTILE; i++) {
                sRandomizerForest.add(TileAction.Factory.createWhirl());
            }
            for (int i = 0; i < TileAction.TILE_COUNT_VOLCANO_UNDERFORESTTILE; i++) {
                sRandomizerForest.add(TileAction.Factory.createVolcano());
            }
        }

        int random = new Random().nextInt(TileAction.sRandomizerForest.size());
        TileAction retour = TileAction.sRandomizerForest.get(random);
        TileAction.sRandomizerForest.remove(random);

        return retour;
    }

    /**
     * Defini la logique de jeu à lancer lors de l'utilisation de l'action d'un
     * tile
     *
     * @param tile Tile d'action, si l'action est immédiate
     * @param logic Logique a executer à l'utilisation
     * @return true si l'action s'est lancée, false si elle ne s'est pas lancée
     * (par exemple, si c'est un mouvement de baleine mais qu'il n'y a pas de
     * baleine dans le plateau)
     */
    public void use(GameTile tile, GameLogic logic) {
        switch (mAction) {
            case ACTION_SPAWN_ENTITY:
                performActionSpawnEntity(tile, logic);
                break;

            case ACTION_CANCEL_ANIMAL:
                performActionCancelAnimal(logic);
                break;

            case ACTION_MOVE_ANIMAL:
                performActionMoveAnimal(logic);
                break;

            case ACTION_BONUS_BOAT:
                performActionBonusBoat(logic);
                break;

            case ACTION_BONUS_SWIM:
                performActionBonusSwim(logic);
                break;

            case ACTION_WHIRL:
                performActionWhirl(tile, logic);
                break;

            case ACTION_VOLCANO:
                performActionVolcano(logic);
                break;

            default:
                throw new IllegalStateException("Unknown haxion: Action " + mAction);
        }
    }

    /**
     * Retourne si oui ou non cette action peut être utilisée (c'est-à-dire
     * qu'il existe des entités sur lesquelles l'action de cette classe est
     * possible)
     *
     * @return true si l'action a une utilité dans l'état actuel du jeu
     */
    public boolean canBeUsed(GameLogic logic) {
        Class entClass = null;
        switch (mEntity) {
            case ENTITY_SEASERPENT:
                entClass = SeaSerpent.class;
                break;

            case ENTITY_SHARK:
                entClass = Shark.class;
                break;

            case ENTITY_WHALE:
                entClass = Whale.class;
                break;
        }

        switch (mAction) {
            case ACTION_BONUS_BOAT:
                // Fonctionne si on a un bateau sur le plateau
                return logic.getBoard().hasEntityOfType(Boat.class);

            case ACTION_BONUS_SWIM:
                // Fonctionne si le joueur a un nageur
                return logic.getCurrentTurn().getPlayer().hasSwimmer();

            case ACTION_CANCEL_ANIMAL:
                // Déclenché différé
                return false;

            case ACTION_MOVE_ANIMAL:
                // Fonctionne si il y a un animal du type indiqué
                return logic.getBoard().hasEntityOfType(entClass);

            case ACTION_SPAWN_ENTITY:
                // Les spawn fonctionnent forcément
                return true;

            case ACTION_VOLCANO:
                // Le volcan fonctionne forcément
                return true;

            case ACTION_WHIRL:
                // Les tourbillons fonctionnnent forcément
                return true;
        }

        return false;
    }

    /**
     * Lance l'action d'une tile d'annulation de mouvement d'animal
     * @param logic La logique du jeu
     */
    private void performActionCancelAnimal(GameLogic logic) {
        logic.onCancelAction();
    }

    /**
     * Lance l'action d'une tile volcan
     *
     * @param logic Logique du jeu
     */
    private void performActionVolcano(GameLogic logic) {
        // TOUT QUI EXPLOSE OMG
        logic.onTileVolcano();
    }

    /**
     * Lance l'action d'une tile tourbillon
     *
     * @param tile La tile tourbillon
     * @param logic Logique du jeu
     */
    private void performActionWhirl(GameTile tile, GameLogic logic) {
        // Tourbillon: Tout ce qui est sur la tile et les tiles adjacentes meurent (absolument tout)
        // et c'est GameLogic qui s'en charge pour gérer les animations
        logic.onTileWhirl(tile);
    }

    /**
     * Lance l'action de bonus de déplacement de bateau
     *
     * @param logic Logique du jeu
     */
    private void performActionBonusBoat(GameLogic logic) {
        // Bonus 3 déplacements de bateau
        // Les TileAction étant sélectionnées au début d'un tour, on a déjà une requête de picking
        // en cours (pour les joueurs ou un bateau du joueur). On l'annule donc.
        logic.cancelPick();

        // On lance ensuite une requête de picking
        GameLogic.EntityPickRequest request = new GameLogic.EntityPickRequest();
        request.avoidEntity = null;
        request.pickNearTile = null;
        request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_BOAT_WITHOUT_ROOM
                | GameLogic.EntityPickRequest.FLAG_PICK_BOAT_WITH_ROOM;
        request.player = logic.getCurrentTurn().getPlayer();

        mMovesRemaining = 3;

        logic.requestPick(request, null);
    }

    /**
     * Lance l'action de bonus de nage des bateaux
     *
     * @param logic Logique du jeu
     */
    private void performActionBonusSwim(GameLogic logic) {
        // Bonus 3 déplacements d'un nageur.
        // Les TileAction étant sélectionnées au début d'un tour, on a déjà une requête de picking
        // en cours (pour les joueurs ou un bateau du joueur). On l'annule donc.
        logic.cancelPick();

        // On lance ensuite une requête de picking
        GameLogic.EntityPickRequest request = new GameLogic.EntityPickRequest();
        request.avoidEntity = null;
        request.pickNearTile = null;
        request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_SWIMMER;
        request.player = logic.getCurrentTurn().getPlayer();

        mMovesRemaining = 3;

        logic.requestPick(request, null);
    }

    /**
     * Lance l'action de téléportation d'un animal
     *
     * @param logic Logique du jeu
     */
    private void performActionMoveAnimal(GameLogic logic) {
        // Téléportation! BZZZIIIOOOUUUUU
        // Les TileAction étant sélectionnées au début d'un tour, on a déjà une requête de picking
        // en cours (pour les joueurs ou un bateau du joueur). On l'annule donc.
        logic.cancelPick();

        // On lance ensuite une requête de picking en fonction de l'animal à bouger
        GameLogic.EntityPickRequest request = new GameLogic.EntityPickRequest();
        request.avoidEntity = null;
        request.pickNearTile = null;

        switch (mEntity) {
            case ENTITY_SEASERPENT:
                request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_SEASERPENT;
                break;

            case ENTITY_SHARK:
                request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_SHARK;
                break;

            case ENTITY_WHALE:
                request.pickingRestriction = GameLogic.EntityPickRequest.FLAG_PICK_WHALE;
                break;

            default:
                throw new IllegalArgumentException("Unknown MoveAnimal entity: " + mEntity);
        }

        logic.requestPick(request, null);
    }

    /**
     * Lance l'action de spawning d'une entité
     *
     * @param tile La tile où l'entité va spawn
     * @param logic Logique du jeu
     */
    private void performActionSpawnEntity(GameTile tile, GameLogic logic) {
        // On spawne l'entité
        GameEntity spawnedEntity = null;

        switch (mEntity) {
            case ENTITY_BOAT:
                spawnedEntity = new Boat();
                break;

            case ENTITY_SHARK:
                spawnedEntity = new Shark();
                break;

            case ENTITY_WHALE:
                spawnedEntity = new Whale();
                break;

            default:
                throw new IllegalArgumentException("Unknown spawn entity type: " + mEntity);
        }

        // On spawne une première fois l'entité pour que onEntitySpawn mette l'entité au bon
        // endroit sur le rendu. Ensuite, on relance moveToTile avec la logic pour effectivement
        // lancer les événements. Cela permet d'être sûr que la nouvelle entité est bien affichée
        // avant que d'autres animations se lancent.
        logger.log(Level.FINE, "Spawned entity: Moving to tile", new Object[]{});
        spawnedEntity.moveToTile(null, tile);
        logic.onEntitySpawn(spawnedEntity);
        logger.log(Level.FINE, "Spawned entity: Triggering events", new Object[]{});
        spawnedEntity.moveToTile(logic, tile);
    }

    //--------------------------------------------------------------------------
    //GETTERS                                                                  |
    //--------------------------------------------------------------------------
    public boolean isImmediate() {
        return mIsImmediate;
    }

    public boolean isTriggerable() {
        return mIsTriggerable;
    }

    public boolean isVolcano() {
        return mIsVolcano;
    }

    public int getAction() {
        return mAction;
    }

    public int getEntity() {
        return mEntity;
    }
    //--------------------------------------------------------------------------

    @Override
    public String toString() {
        return "TileAction(action=" + mAction + ", entity=" + mEntity + ", isImmediate=" + mIsImmediate + ", isTriggerable=" + mIsTriggerable + ")";
    }
}
