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

import fr.miage.atlantis.logic.GameLogic;
import java.util.ArrayList;
import java.util.Random;

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
     * Action de la tile volcan
     */
    public final static int ACTION_VOLCANO = 5;
    /**
     * Nombre de tile action de type Spawn Animal Shark
     */
    public final static int TILE_COUNT_ANIMAL_SHARK = 6;
    /**
     * Nombre de tile action de type Spawn Animal Whale
     */
    public final static int TILE_COUNT_ANIMAL_WHALE = 5;
    /**
     * Nombre de tile action de type Move Animal
     */
    private final static int TILE_COUNT_MOVE_ANIMAL = 16;
    /**
     * Nombre de tile action de type CancelAnimal
     */
    private final static int TILE_COUNT_CANCEL_ANIMAL = 11;
    /**
     * Nombre de tile action de type SpawnBoat
     */
    private final static int TILE_COUNT_SPAWN_BOAT = 2;                                   //@TODO : Trouver le nombre exact de ce type de tile
    /**
     * Nombre de tile action de type BonusSwim
     */
    private final static int TILE_COUNT_BONUS_SWIM = 3;                                   //@TODO : Trouver le nombre exact de ce type de tile
    /**
     * Nombre de tile action de type BonusBoat
     */
    private final static int TILE_COUNT_BONUS_BOAT = 3;                                   //@TODO : Trouver le nombre exact de ce type de tile
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
    private static ArrayList<TileAction> sRandomizer;

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

        public static TileAction createVolcano() {
            return new TileAction(ACTION_VOLCANO, NONE, false, false, true);
        }
    }

    /**
     * Genere une action au hasard à placer sous un tile
     *
     * @return A random ActionTile
     */
    public static TileAction generateRandomTileAction() {
        if (TileAction.sRandomizer == null) {
            sRandomizer = new ArrayList<TileAction>();

            for (int i = 0; i <= TileAction.TILE_COUNT_ANIMAL_SHARK; i++) {
                sRandomizer.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_SHARK));
                for (int j = 0; j <= TileAction.TILE_COUNT_CANCEL_ANIMAL; j++) {
                    sRandomizer.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_SHARK));
                }
                for (int k = 0; k <= TileAction.TILE_COUNT_MOVE_ANIMAL; k++) {
                    sRandomizer.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_SHARK));
                }
            }

            for (int i = 0; i <= TileAction.TILE_COUNT_ANIMAL_WHALE; i++) {
                sRandomizer.add(TileAction.Factory.createSpawnEntity(TileAction.ENTITY_WHALE));

                for (int j = 0; j <= TileAction.TILE_COUNT_CANCEL_ANIMAL; j++) {
                    sRandomizer.add(TileAction.Factory.createCancelAnimal(TileAction.ENTITY_WHALE));
                }
                for (int k = 0; k <= TileAction.TILE_COUNT_MOVE_ANIMAL; k++) {
                    sRandomizer.add(TileAction.Factory.createMoveAnimal(TileAction.ENTITY_WHALE));
                }
            }

            for (int i = 0; i <= TileAction.TILE_COUNT_SPAWN_BOAT; i++) {
                sRandomizer.add(TileAction.Factory.createBonusBoat());
            }

            for (int i = 0; i <= TileAction.TILE_COUNT_BONUS_SWIM; i++) {
                sRandomizer.add(TileAction.Factory.createBonusSwim());
            }
        }

        int random = new Random().nextInt(TileAction.sRandomizer.size());
        TileAction retour = TileAction.sRandomizer.get(random);
        TileAction.sRandomizer.remove(random);

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
        }
    }

    private void performActionSpawnEntity(GameTile tile, GameLogic logic) {

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

    public int getAnimal() {
        return mEntity;
    }
    //--------------------------------------------------------------------------
}
