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

package fr.miage.atlantis;

import fr.miage.atlantis.board.GameTile;
import fr.miage.atlantis.board.TileAction;
import fr.miage.atlantis.entities.GameEntity;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.logic.GameLogic;

/**
 * Main Game Engine loop class
 */
public class Game3DLogic extends GameLogic {
    
    private Game3DRenderer mRenderer;
    
    public Game3DLogic() {
        super();
        mRenderer = new Game3DRenderer(this);
    }
    
    @Override
    public void boot() {
        mRenderer.start();
    }

    public void onTurnStart(Player p) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onPlayTileAction(TileAction t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onUnitMove(GameEntity ent, GameTile dest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onDiceRoll(int face) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onSinkTile(GameTile tile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEntityAction(GameEntity source, GameEntity target, int action) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}