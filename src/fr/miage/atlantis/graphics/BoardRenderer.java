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

package fr.miage.atlantis.graphics;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import fr.miage.atlantis.board.GameBoard;
import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.WaterTile;
import fr.miage.atlantis.graphics.models.EmptyTileModel;
import fr.miage.atlantis.graphics.models.TileModel;

/**
 *
 */
public class BoardRenderer extends Node {
    
    public final static String DATA_TILE = "tile";
    
    private final static boolean DEBUG_ITERATION = true;
    private final static float TILE_SIZE = 20.0f;
    private final static float GRID_HEIGHT = 1.0f;

    private float mTileOffset = 0.0f;
    private AssetManager mAssetManager;
    
    public BoardRenderer(AssetManager am) {
        mAssetManager = am;
    }
    
    /**
     * Parcours le board passé en paramètre et crée toutes les nodes 3D en
     * fonction de l'état actuel du board.
     * @param board Le board a rendre
     */
    public void renderBoard(final GameBoard board) {
        GameTile currentTile = board.getFirstTile();
        GameTile rowHeadTile = currentTile;
        
        if (currentTile == null) {
            throw new IllegalStateException("Impossible de rendre un board vide");
        }
        
        int x = 0, y = 0;
        while (true) {
            int initialX = x;
            
            // On ajoute la tile présente
            addTileToRender(currentTile, x, y);
            if (DEBUG_ITERATION) System.out.println("Adding self tile " + x + "," + y + " (" + currentTile.getName() + ")");
            
            // On ajoute toutes les tiles à droite
            while (currentTile.getRightTile() != null) {
                x++;
                currentTile = currentTile.getRightTile();
                addTileToRender(currentTile, x, y);
                if (DEBUG_ITERATION) System.out.println("Adding right row tile " + x + "," + y + " (" + currentTile.getName() + ")");
            }
            
            // On ajoute toutes les tiles à gauche
            currentTile = rowHeadTile;
            x = initialX;
            
            while (currentTile.getLeftTile() != null) {
                x--;
                currentTile = currentTile.getLeftTile();
                addTileToRender(currentTile, x, y);
                if (DEBUG_ITERATION) System.out.println("Adding left row tile " + x + "," + y + " (" + currentTile.getName() + ")");
            }
            
            // On cherche l'élément du dessous. On est déjà à gauche, donc
            // on continue à droite tant qu'on a rien en dessous.
            boolean belowFound = false;
            while (!belowFound && currentTile != null) {
                if (currentTile.getLeftBottomTile() != null) {
                    if (DEBUG_ITERATION) System.out.println("Going below left of " + currentTile.getName());
                    rowHeadTile = currentTile.getLeftBottomTile();
                    currentTile = rowHeadTile;
                    mTileOffset -= TILE_SIZE / 2.0f;
                    
                    y++;
                    belowFound = true;
                } else if (currentTile.getRightBottomTile() != null) {
                    if (DEBUG_ITERATION) System.out.println("Going below right of " + currentTile.getName());
                    rowHeadTile = currentTile.getRightBottomTile();
                    currentTile = rowHeadTile;
                    mTileOffset += TILE_SIZE / 2.0f;
                    
                    y++;
                    belowFound = true;
                } else {
                    currentTile = currentTile.getRightTile();
                }
            }
            
            if (!belowFound) {
                // Il n'y a plus rien en dessous, on s'arrête
                break;
            }
        }
    }
    
    /**
     * Ajoute une tile au rendu à l'endroit singulier spécifié. Les coordonnées
     * sont converties automatiquement en coordonnées de l'univers 3D
     * @param tile La tile à ajouter
     * @param x L'emplacement X sur le GameBoard
     * @param y L'emplacement Y sur le GameBoard
     */
    public void addTileToRender(GameTile tile, int x, int y) {
        Node output;
        
        // Si c'est une tile au dessus de l'eau, on utilise un mesh avec la
        // texture qui va bien. Sinon, on fait un contour seulement.
        if (tile.getHeight() > 0) {
            output = new TileModel(tile.getHeight(), mAssetManager);
        } else {
            // On détermine la couleur en fonction du type de tile
            ColorRGBA color = ColorRGBA.White;
            if (tile.getHeight() < 0) {
                color = ColorRGBA.Red;
            } else if (tile.getHeight() == 0) {
                WaterTile wt = (WaterTile) tile;
                color = wt.isLandingTile() ? ColorRGBA.Blue : ColorRGBA.Cyan;
                color = wt.isBeginningWithSeaShark() ? ColorRGBA.Magenta : color;
            }

            output = new EmptyTileModel(mAssetManager, color);
        }

        // On positionne la tile
        output.setLocalTranslation(y * -TILE_SIZE,
                GRID_HEIGHT,
                x * TILE_SIZE + mTileOffset);
        
        // On l'attache à cette Node
        output.setUserData(DATA_TILE, tile);
        attachChild(output);
    }
}
