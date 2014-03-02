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
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import com.jme3.shadow.EdgeFilteringMode;
import com.jme3.texture.Texture2D;
import com.jme3.util.SkyFactory;
import com.jme3.water.WaterFilter;
import jme3utilities.sky.SkyControl;
import jme3utilities.sky.Updater;

/**
 *
 */
public class Environment {

    private AmbientLight mAmbient;
    private DirectionalLight mDirectionalLight;
    private SkyControl mSkyControl;

    public Environment(Node parent, AssetManager am, ViewPort viewPort, Camera cam) {

        // Mise en place du lighting
        setupLight(parent);

        // Mise en place du ciel
        setupSky(parent, viewPort, am, cam);

        // Mise en place de l'eau
        setupWater(parent, am, viewPort);

    }

    public void setTime(float time) {
        mSkyControl.getSunAndStars().setHour(time);
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
        water.setUseHQShoreline(true);

        //water.setRefractionConstant(0.25f);
        water.setRefractionStrength(0.2f);
        //water.setFoamHardness(0.6f);

        viewPort.addProcessor(fpp);
    }

    private void setupSky(Node parent, ViewPort viewPort, AssetManager am, Camera cam) {
        parent.attachChild(SkyFactory.createSky(am,
            "Textures/Sky/Bright/BrightSky.dds", false));
        
        boolean starMotion = true;
        boolean bottomDome = true;
        mSkyControl = new SkyControl(am, cam, 0.1f, starMotion, bottomDome);

        // add SkyControl to the root node
        parent.addControl(mSkyControl);

        Updater updater = mSkyControl.getUpdater();
        updater.addViewPort(viewPort);
        updater.setAmbientLight(mAmbient);
        updater.setMainLight(mDirectionalLight);

        float hour = 15f;
        mSkyControl.getSunAndStars().setHour(hour);

        float cloudiness = 0.5f;
        mSkyControl.setCloudiness(cloudiness);
        mSkyControl.setEnabled(true);

        // Ombres
        DirectionalLightShadowRenderer dlsr =
                new DirectionalLightShadowRenderer(am,
                1024, 3);
        dlsr.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
        dlsr.setLight(mDirectionalLight);
        updater.addShadowRenderer(dlsr);
        viewPort.addProcessor(dlsr);
    }

    private void setupLight(Node parent) {
        // Lumière ambiante, semi-claire, compense la sombritude du SkyControl
        mAmbient = new AmbientLight();
        mAmbient.setColor(ColorRGBA.Gray);
        parent.addLight(mAmbient);
        
        // Lumière ambiante contrôlée par SkyControl
        mAmbient = new AmbientLight();
        parent.addLight(mAmbient);

        // Lumière directionnelle (soleil), contrôlé par SkyControl
        mDirectionalLight = new DirectionalLight();
        parent.addLight(mDirectionalLight);

    }
}
