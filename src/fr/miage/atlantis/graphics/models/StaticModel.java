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
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;
import com.jme3.util.TangentBinormalGenerator;
import fr.miage.atlantis.graphics.ModelCache;

/**
 *
 */
public class StaticModel extends Node {

    private final static float DEFAULT_SCALE = 5.0f;

    private Spatial mModel;
    private Material mMaterial;
    private Node mModelNode;

    public StaticModel(AssetManager am, String meshName,
            String diffusePath, String normalPath) {
        mModelNode = new Node();
        mModelNode.setShadowMode(ShadowMode.CastAndReceive);

        // On charge le mesh du cache
        Spatial model = ModelCache.getInstance().getModel(meshName);
        if (model == null) {
            System.out.println("LOADING MODEL: " + meshName);
            mModel = am.loadModel(meshName);
            ModelCache.getInstance().putModel(meshName, mModel);
        } else {
            System.out.println("USING MODEL FROM CACHE");
            mModel = model.clone(false);
        }

        // Application de l'échelle par défaut
        mModel.setLocalScale(DEFAULT_SCALE);

        // Application du material. On recherche dans le cache d'abord.
        if (diffusePath != null) {
            String cacheKey = diffusePath + "::" + normalPath;

            Material cachedMat = ModelCache.getInstance().getMaterial(cacheKey);

            if (cachedMat != null) {
                System.out.println("USING CACHED MATERIAL");
                mMaterial = cachedMat.clone();
            } else {
                System.out.println("GENERATING NEW MATERIAL");
                mMaterial = new Material(am, "Common/MatDefs/Light/Lighting.j3md");
                Texture diffuseTex = am.loadTexture(diffusePath);
                diffuseTex.setMinFilter(Texture.MinFilter.Trilinear);
                mMaterial.setTexture("DiffuseMap", diffuseTex);

                if (normalPath != null) {
                    // Il faut calculer les tangentes pour que le lighting
                    // fonctionne avec une texture de normalmap
                    TangentBinormalGenerator.generate(mModel);
                    mMaterial.setTexture("NormalMap", am.loadTexture(normalPath));
                }

                mMaterial.setBoolean("UseMaterialColors", true);
                mMaterial.setColor("Ambient", ColorRGBA.White);
                mMaterial.setColor("Diffuse", ColorRGBA.DarkGray);

                ModelCache.getInstance().putMaterial(cacheKey, mMaterial);
            }

            mModel.setMaterial(mMaterial);
        }

        mModelNode.attachChild(mModel);
        this.attachChild(mModelNode);
    }

    public Spatial getModel() {
        return mModel;
    }

    public Node getModelNode() {
        return mModelNode;
    }

    public Material getMaterial() {
        return mMaterial;
    }


}
