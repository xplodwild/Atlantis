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
import com.jme3.effect.ParticleEmitter;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.scene.Node;

/**
 *
 */
public class ParticlesFactory {

    public static Node makeWaterSplash(AssetManager assetManager) {
        // On charge l'effet "pré-fabriqué" en j3o
        Node loadedNode = (Node) assetManager.loadModel("Scenes/fx_WaterSplash.j3o");

        // On applique la texture
        Material debris_mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        debris_mat.setTexture("Texture", assetManager.loadTexture("Effects/Smoke/Smoke.png"));
        ParticleEmitter emitter = (ParticleEmitter) loadedNode.getChild(0);
        emitter.setMaterial(debris_mat);
        emitter.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        emitter.getMaterial().getAdditionalRenderState().setDepthWrite(true);
        emitter.getMaterial().getAdditionalRenderState().setAlphaTest(true);
        emitter.getMaterial().getAdditionalRenderState().setAlphaFallOff(0.1f);
        emitter.setLocalScale(10);

        // One-shot: On emit toutes les particules, et on s'arrête
        emitter.setParticlesPerSec(0.0f);

        return loadedNode;
    }

    public static void emitAllParticles(Node effect) {
        ParticleEmitter emitter = (ParticleEmitter) effect.getChild(0);
        emitter.emitAllParticles();
    }

}
