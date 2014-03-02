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

package fr.miage.atlantis.graphics.models;

import com.jme3.asset.AssetManager;

/**
 *
 */
public class TileModel extends StaticModel {
    
    public TileModel(int height, AssetManager am) {
        super(am, getModelPathFromHeight(height),
                getTexturePathFromHeight(height),
                getNormalPathFromHeight(height));
        
        // Les modèles de tiles hexagonaux sont décalées de l'origine, et du 
        // coup ne collent pas avec les tiles empty. On les recentre manuellement.
        setLocalTranslation(12, 0, 10);
    }
    
    private static String getModelPathFromHeight(int height) {
        String path = null;
        switch (height) {
            case 1:
                path = "Models/beach_tile.mesh.xml";
                break;
                
            case 2:
                path = "Models/jungle_tile.mesh.xml";
                break;
                
            case 3:
                path = "Models/rock_tile.mesh.xml";
                break;
        }
        return path;
    }
    
    private static String getTexturePathFromHeight(int height) {
        String path = null;
        switch (height) {
            case 1:
                path = "Textures/sand.jpg";
                break;
                
            case 2:
                path = "Textures/grass.jpg";
                break;
                
            case 3:
                path = "Textures/rock.jpg";
                break;
        }
        return path;
    }
    
    private static String getNormalPathFromHeight(int height) {
        String path = null;
        switch (height) {
            case 1:
                path = "Textures/sand_normal.jpg";
                break;
                
            case 2:
                path = "Textures/grass_normal.jpg";
                break;
                
            case 3:
                path = "Textures/rock_normal.jpg";
                break;
        }
        return path;
    }
}
