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
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import java.io.IOException;

/**
 *
 */
public class EmptyTileModel extends Node {

    private static final class HexagonMesh extends Mesh {

        float a;
        float rt = (float) Math.sqrt(3);

        /**
         *
         * @param a_value : Note see 30-60-90 triangle
         */
        public HexagonMesh(float a_value) {
            super();
            a = a_value;
            updateGeometry();
        }

        public void updateGeometry() {
            float[] vert = {
                0, 0, a * rt, //1
                a, 0, 0, //2
                3 * a, 0, 0, //3
                4 * a, 0, a * rt, //4
                3 * a, 0, 2 * a * rt, //5
                a, 0, 2 * a * rt}; //6

            float[] norm = {
                0, 1, 0,
                0, 1, 0,
                0, 1, 0,
                0, 1, 0};

            int[] index = {
                0, 1, 5,
                4, 3, 2,
                2, 1, 0,
                5, 4, 3
            };
            setBuffer(VertexBuffer.Type.Position, 3, BufferUtils.createFloatBuffer(vert));
            setBuffer(VertexBuffer.Type.Normal, 3, BufferUtils.createFloatBuffer(norm));
            setBuffer(VertexBuffer.Type.Index, 3, BufferUtils.createIntBuffer(index));

            updateBound();
            updateCounts();

        }

        public void read(JmeImporter e) throws IOException {
            super.read(e);
            InputCapsule capsule = e.getCapsule(this);
            a = capsule.readFloat("a", 0);
            rt = capsule.readFloat("rt", 0);
        }

        public void write(JmeExporter e) throws IOException {
            super.write(e);
            OutputCapsule capsule = e.getCapsule(this);
            capsule.write(a, "a", 0);
            capsule.write(rt, "rt", 0);
        }
    }
    
    public EmptyTileModel(AssetManager assetManager) {
        HexagonMesh h = new HexagonMesh(60.0f);
        h.setMode(Mesh.Mode.Lines);
        h.setLineWidth(30);
        
        Geometry modelGrid = new Geometry("Grid", h);
        modelGrid.setLocalScale(0.1f);
        
        Material matGrid = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        matGrid.setColor("Color", ColorRGBA.Blue);
        modelGrid.setMaterial(matGrid);
        
        attachChild(modelGrid);
    }
}
