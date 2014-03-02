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
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.shadow.PssmShadowFilter;
import com.jme3.shadow.PssmShadowRenderer;
import com.jme3.shadow.PssmShadowRenderer.FilterMode;
import com.jme3.texture.Texture2D;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;

/**
 *
 */
public class Environment {

    public Environment(Node parent, AssetManager am, ViewPort viewPort) {
        
        // Mise en place du ciel
        setupSky(parent, am);
        
        // Mise en place du lighting
        setupLight(parent, am, viewPort);
        // Mise en place de l'eau
        setupWater(parent, am, viewPort);
        
    }
    
    private void setupWater(Node parent, AssetManager am, ViewPort viewPort) {
        WaterFilter water = new WaterFilter(parent, new Vector3f(-.5f, -.5f, .5f));

        FilterPostProcessor fpp = new FilterPostProcessor(am);
        fpp.addFilter(water);
        
        water.setWaveScale(0.003f);
        water.setMaxAmplitude(2f);
        water.setFoamExistence(new Vector3f(1f, 4, 0.5f));
        water.setFoamTexture((Texture2D) am.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));
        water.setNormalScale(2.0f);
        water.setReflectionMapSize(256);

        //water.setRefractionConstant(0.25f);
        water.setRefractionStrength(0.2f);
        //water.setFoamHardness(0.6f);

        viewPort.addProcessor(fpp);
    }
    
    private void setupSky(Node parent, AssetManager am) {
        parent.attachChild(SkyFactory.createSky(am,
                "Textures/Sky/Bright/BrightSky.dds", false));
    }
    
    private void setupLight(Node parent, AssetManager assetManager, ViewPort viewPort) {
        // Lumière ambiante, semi-claire
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.Gray);
        parent.addLight(al);
        
        // Lumière directionnelle (soleil)
        DirectionalLight l = new DirectionalLight();
        l.setDirection(new Vector3f(-.5f, -1f, .5f));
        l.setColor(ColorRGBA.White);
        parent.addLight(l);
        
        // Ombres
        PssmShadowRenderer pssmRenderer = new PssmShadowRenderer(assetManager, 1024, 3);
        pssmRenderer.setDirection(new Vector3f(-0.5973172f, -0.56583486f, 0.8846725f).normalizeLocal());
        pssmRenderer.setLambda(0.55f);
        pssmRenderer.setShadowIntensity(0.6f);
        pssmRenderer.setCompareMode(PssmShadowRenderer.CompareMode.Hardware);
        pssmRenderer.setFilterMode(FilterMode.PCF8);
                
        viewPort.addProcessor(pssmRenderer);

        PssmShadowFilter pssmFilter = new PssmShadowFilter(assetManager, 1024, 3);
        pssmFilter.setLambda(0.55f);
        pssmFilter.setShadowIntensity(0.6f);
        pssmFilter.setCompareMode(PssmShadowRenderer.CompareMode.Hardware);
        pssmFilter.setFilterMode(FilterMode.PCF8);
        pssmFilter.setEnabled(false);

        FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
        fpp.setNumSamples(4);
        fpp.addFilter(pssmFilter);

        viewPort.addProcessor(fpp);
    }
}
