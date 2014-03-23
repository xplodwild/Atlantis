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

import com.jme3.animation.LoopMode;
import com.jme3.cinematic.MotionPath;
import com.jme3.cinematic.events.MotionEvent;
import com.jme3.math.Quaternion;
import com.jme3.math.Spline;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 *
 */
public class CamConstants {

    public static void moveMenu(Spatial camNode, Camera cam) {
        if (camNode.getNumControls() > 1) {
            camNode.removeControl(camNode.getControl(1));
        }

        final Vector3f finalPos = new Vector3f(-242.6336f, 49.71627f, 591.2137f);

        MotionPath path = new MotionPath();
        path.setCycle(false);
        path.addWayPoint(cam.getLocation());
        Vector3f camPosCopy = new Vector3f(cam.getLocation());
        path.addWayPoint(camPosCopy.interpolate(finalPos, 0.75f));
        path.addWayPoint(camPosCopy.interpolate(finalPos, 0.90f));
        path.addWayPoint(finalPos);
        path.setCurveTension(0.83f);
        path.setPathSplineType(Spline.SplineType.Bezier);

        MotionEvent cameraMotionControl = new MotionEvent(camNode, path);
        cameraMotionControl.setLoopMode(LoopMode.DontLoop);
        cameraMotionControl.setInitialDuration(3.5f);
        cameraMotionControl.setRotation(new Quaternion(0.08739185f, 0.22561216f, -0.020325268f, 0.97007674f));
        cameraMotionControl.setDirectionType(MotionEvent.Direction.Rotation);

        cameraMotionControl.play();
    }

    public static void moveAboveBoard(Spatial camNode, Camera cam) {
        if (camNode.getNumControls() > 1) {
            camNode.removeControl(camNode.getControl(1));
        }

        final Vector3f finalPos = new Vector3f(-398.292f, 572.2102f, 176.78018f);

        MotionPath path = new MotionPath();
        path.setCycle(false);
        path.addWayPoint(cam.getLocation());
        Vector3f camPosCopy = new Vector3f(cam.getLocation());
        path.addWayPoint(camPosCopy.interpolate(finalPos, 0.75f));
        path.addWayPoint(camPosCopy.interpolate(finalPos, 0.90f));
        path.addWayPoint(finalPos);
        path.setCurveTension(0.83f);
        path.setPathSplineType(Spline.SplineType.Bezier);

        MotionEvent cameraMotionControl = new MotionEvent(camNode, path);
        cameraMotionControl.setLoopMode(LoopMode.DontLoop);
        cameraMotionControl.setInitialDuration(1.5f);
        cameraMotionControl.setRotation(new Quaternion(0.43458012f, 0.5573096f, -0.4326719f, 0.5597688f));
        cameraMotionControl.setDirectionType(MotionEvent.Direction.Rotation);

        cameraMotionControl.play();
    }

}
