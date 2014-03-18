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

package fr.miage.atlantis.gui.console;

import com.jme3.scene.Node;
import fr.miage.atlantis.Game3DLogic;
import fr.miage.atlantis.graphics.Game3DRenderer;
import fr.miage.atlantis.graphics.hud.HudAnimator;
import fr.miage.atlantis.graphics.models.DiceModel;

/**
 *
 */
public class GuiConsole extends Game3DRenderer {
    
    

    private static final boolean DEBUG_SHOW_STATS = false;
    private Node mSceneNode;
    private Game3DLogic mParent;    
    private DiceModel mDiceModel;  
    private HudAnimator mHudAnimator;

    public GuiConsole(Game3DLogic parent) {
        super(parent);        
        mHudAnimator = new HudAnimator();

    }
    
    

    @Override
    public void simpleInitApp() {        
        super.simpleInitApp();

    }


 
}
