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
     * Action déclenchable permettant d'annuler l'action d'un animal sur le joueur
     */
    public final static int ACTION_CANCEL_ANIMAL = 1;
    /**
     * Action immédiate faisant apparaître une nouvelle entité (shark, whale, boat)
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

    /** Nombre de tile action de type Spawn Animal Shark     */
    public final static int TILE_COUNT_ANIMAL_SHARK_UNDERBEACHTILE = 3;
    /** Nombre de tile action de type Spawn Animal Whale     */
    public final static int TILE_COUNT_ANIMAL_WHALE_UNDERBEACHTILE = 3;
    /** Nombre de tile action de type Move Animal Shark     */
    private final static int TILE_COUNT_MOVE_SHARK_UNDERBEACHTILE = 1;
    /** Nombre de tile action de type Move Animal Whale     */
    private final static int TILE_COUNT_MOVE_WHALE_UNDERBEACHTILE = 1;
    /** Nombre de tile action de type Move Animal Seaserpent     */
    private final static int TILE_COUNT_MOVE_SEASERPENT_UNDERBEACHTILE = 1;
    /** Nombre de tile action de type Cancel Animal Shark     */
    private final static int TILE_COUNT_CANCEL_SHARK_UNDERBEACHTILE = 1;
    /** Nombre de tile action de type Cancel Animal Whale     */
    private final static int TILE_COUNT_CANCEL_WHALE_UNDERBEACHTILE = 0;
    /** Nombre de tile action de type Spawn Boat     */
    private final static int TILE_COUNT_SPAWN_BOAT_UNDERBEACHTILE = 1;
    /** Nombre de tile action de type BonusSwim     */
    private final static int TILE_COUNT_BONUS_SWIM_UNDERBEACHTILE = 3;
    /** Nombre de tile action de type BonusBoat     */
    private final static int TILE_COUNT_BONUS_BOAT_UNDERBEACHTILE = 2;
    /** Nombre de tile action de type tourbillon     */
    private final static int TILE_COUNT_WHIRL_UNDERBEACHTILE = 0;
    /** Nombre de tile action de type volcan     */
    private final static int TILE_COUNT_VOLCANO_UNDERBEACHTILE = 0;

    //--------------------------------------------------------------------------
    //TileAction sous les Tiles Forest
    //--------------------------------------------------------------------------

    /** Nombre de tile de type Spawn Requin*/
    public final static int TILE_COUNT_ANIMAL_SHARK_UNDERFORESTTILE = 2;
    /** Nombre de tile action de type Spawn Animal Whale */
    public final static int TILE_COUNT_ANIMAL_WHALE_UNDERFORESTTILE = 2;
    /** Nombre de tile action de type Move Animal Shark  */
    private final static int TILE_COUNT_MOVE_SHARK_UNDERFORESTTILE = 1;
    /** Nombre de tile action de type Move Animal Whale  */
    private final static int TILE_COUNT_MOVE_WHALE_UNDERFORESTTILE = 1;
    /** Nombre de tile action de type Move Animal Seaserpent    */
    private final static int TILE_COUNT_MOVE_SEASERPENT_UNDERFORESTTILE = 1;
    /** Nombre de tile action de type Cancel Animal Shark    */
    private final static int TILE_COUNT_CANCEL_SHARK_UNDERFORESTTILE = 1;
    /** Nombre de tile action de type Cancel Animal Whale     */
    private final static int TILE_COUNT_CANCEL_WHALE_UNDERFORESTTILE = 2;
    /** Nombre de tile action de type Spawn Boat     */
    private final static int TILE_COUNT_SPAWN_BOAT_UNDERFORESTTILE = 3;
    /** Nombre de tile action de type BonusSwim     */
    private final static int TILE_COUNT_BONUS_SWIM_UNDERFORESTTILE = 1;
    /** Nombre de tile action de type BonusBoat     */
    private final static int TILE_COUNT_BONUS_BOAT_UNDERFORESTTILE = 0;
    /** Nombre de tile action de type tourbillon     */
    private final static int TILE_COUNT_WHIRL_UNDERFORESTTILE = 2;
    /** Nombre de tile action de type volcan     */
    private final static int TILE_COUNT_VOLCANO_UNDERFORESTTILE = 0;
    //--------------------------------------------------------------------------


    //--------------------------------------------------------------------------
    //TileAction sous les Tiles Mountain
    //--------------------------------------------------------------------------

    /** Nombre de tile action de type Spawn Animal Shark     */
    public final static int TILE_COUNT_ANIMAL_SHARK_UNDERMOUNTAINTILE = 1;
    /** Nombre de tile action de type Spawn Animal Whale     */
    public final static int TILE_COUNT_ANIMAL_WHALE_UNDERMOUNTAINTILE = 0;
    /** Nombre de tile action de type Move Animal Shark     */
    private final static int TILE_COUNT_MOVE_SHARK_UNDERMOUNTAINTILE = 0;
    /** Nombre de tile action de type Move Animal Whale     */
    private final static int TILE_COUNT_MOVE_WHALE_UNDERMOUNTAINTILE = 0;
    /** Nombre de tile action de type Move Animal Seaserpent     */
    private final static int TILE_COUNT_MOVE_SEASERPENT_UNDERMOUNTAINTILE = 0;
    /** Nombre de tile action de type Cancel Animal Shark     */
    private final static int TILE_COUNT_CANCEL_SHARK_UNDERMOUNTAINTILE = 1;
    /** Nombre de tile action de type Cancel Animal Whale     */
    private final static int TILE_COUNT_CANCEL_WHALE_UNDERMOUNTAINTILE = 1;
    /** Nombre de tile action de type Spawn Boat     */
    private final static int TILE_COUNT_SPAWN_BOAT_UNDERMOUNTAINTILE = 0;
    /** Nombre de tile action de type BonusSwim     */
    private final static int TILE_COUNT_BONUS_SWIM_UNDERMOUNTAINTILE = 0;
    /** Nombre de tile action de type BonusBoat     */
    private final static int TILE_COUNT_BONUS_BOAT_UNDERMOUNTAINTILE = 0;
    /** Nombre de tile action de type tourbillon     */
    private final static int TILE_COUNT_WHIRL_UNDERMOUNTAINTILE = 4;
    /** Nombre de tile action de type volcan     */
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
        if (TileAction.sRandomizerBeach == null) {
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
        if (TileAction.sRandomizerMountain == null) {
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
        if (TileAction.sRandomizerForest == null) {
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
     * @TODO : Implementer (le reste de) la methode
     *
     * Defini la logique de jeu à lancer lors de l'utilisation de l'action d'un tile
     *
     * @param tile Tile d'action
     * @param logic Logique a executer à l'utilisation
     */
    public void use(GameTile tile, GameLogic logic) {
        switch (mAction) {
            case ACTION_SPAWN_ENTITY:
                performActionSpawnEntity(tile, logic);
                break;

            default:
                throw new UnsupportedOperationException("Not implemented yet: Action " + mAction);
        }
    }

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
        return "TileAction(action=" + mAction + ", entity="+mEntity+", isImmediate=" + mIsImmediate + ", isTriggerable=" + mIsTriggerable + ")";
    }
}
