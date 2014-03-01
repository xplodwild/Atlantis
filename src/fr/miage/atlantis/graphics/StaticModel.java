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
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 */
public class StaticModel extends Node {
    
    private final static float DEFAULT_SCALE = 5.0f;

    private Spatial mModel;
    private Material mMaterial;

    public StaticModel(AssetManager am, String meshName,
            String diffusePath, String normalPath) {
        mModel = am.loadModel(meshName);
        mModel.scale(DEFAULT_SCALE);
        
        if (diffusePath != null) {
            mMaterial = new Material(am, "Common/MatDefs/Light/Lighting.j3md");
            mMaterial.setTexture("DiffuseMap", am.loadTexture(diffusePath));
            if (normalPath != null) {
                TangentBinormalGenerator.generate(mModel);
                mMaterial.setTexture("NormalMap", am.loadTexture(normalPath));
            }
            mModel.setMaterial(mMaterial);
        }
        
        this.attachChild(mModel);
    }
    
    
}
