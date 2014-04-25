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

/**
 * Représent l'environnement de jeu
 */
public class Environment {

    private AmbientLight mAmbient;
    private DirectionalLight mDirectionalLight;

    /**
     * Constructeur de l'environnement
     *
     * @param parent noeud de base
     * @param am AssetManager utilisé pour l'asset
     * @param viewPort viewport
     * @param cam caméra
     */
    public Environment(Node parent, AssetManager am, ViewPort viewPort, Camera cam) {

        // Mise en place du lighting
        setupLight(parent, am, viewPort);

        // Mise en place du ciel
        setupSky(parent, viewPort, am, cam);

        // Mise en place de l'eau
        setupWater(parent, am, viewPort);

    }

    /**
     * Affiche l'eau (infinie)
     *
     * @param parent node pour attacher l'eau
     * @param am AssetManager utilisé pour l'asset
     * @param viewPort viewport
     */
    private void setupWater(Node parent, AssetManager am, ViewPort viewPort) {
        WaterFilter water = new WaterFilter(parent, mDirectionalLight.getDirection());

        FilterPostProcessor fpp = new FilterPostProcessor(am);
        fpp.addFilter(water);

        water.setWaveScale(0.003f);
        water.setMaxAmplitude(2f);
        water.setFoamExistence(new Vector3f(1f, 4, 0.5f));
        water.setFoamTexture((Texture2D) am.loadTexture("Common/MatDefs/Water/Textures/foam2.jpg"));
        water.setNormalScale(2.0f);
        water.setReflectionMapSize(128);
        water.setUseHQShoreline(false);

        //water.setRefractionConstant(0.25f);
        water.setRefractionStrength(0.2f);
        //water.setFoamHardness(0.6f);

        viewPort.addProcessor(fpp);
    }

    /**
     * Afficher le ciel (infini)
     *
     * @param parent noeud d'attache
     * @param viewPort viewport
     * @param am AssetManager utilisé pou les assets
     * @param cam caméra associée
     */
    private void setupSky(Node parent, ViewPort viewPort, AssetManager am, Camera cam) {
        parent.attachChild(SkyFactory.createSky(am,
                "Textures/Sky/Bright/BrightSky.dds", false));
    }

    /**
     * Afficher les lumières
     *
     * @param parent noeud d'attache
     * @param am AssetManager utilisé pou les assets
     * @param viewPort viewport
     */
    private void setupLight(Node parent, AssetManager am, ViewPort viewPort) {
        // Lumière ambiante
        mAmbient = new AmbientLight();
        mAmbient.setColor(ColorRGBA.White);
        parent.addLight(mAmbient);

        // Lumière directionnelle (soleil)
        mDirectionalLight = new DirectionalLight();
        mDirectionalLight.setDirection(new Vector3f(-0.4f, -0.3f, -0.2f).normalizeLocal());
        mDirectionalLight.setColor(ColorRGBA.LightGray);
        parent.addLight(mDirectionalLight);

        // Ombres
        DirectionalLightShadowRenderer dlsr =
                new DirectionalLightShadowRenderer(am,
                1024, 3);
        dlsr.setEdgeFilteringMode(EdgeFilteringMode.PCF4);
        dlsr.setLight(mDirectionalLight);
        viewPort.addProcessor(dlsr);
    }
}
