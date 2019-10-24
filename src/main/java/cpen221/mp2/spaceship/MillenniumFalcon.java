package cpen221.mp2.spaceship;

import cpen221.mp2.controllers.GathererStage;
import cpen221.mp2.controllers.HunterStage;
import cpen221.mp2.controllers.Spaceship;
import cpen221.mp2.graph.ImGraph;
import cpen221.mp2.models.Link;
import cpen221.mp2.models.Planet;
import cpen221.mp2.models.PlanetStatus;
import cpen221.mp2.util.Heap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An instance implements the methods needed to complete the mission.
 */
public class MillenniumFalcon implements Spaceship  {
    long startTime = System.nanoTime(); // start time of rescue phase

    @Override
    public void hunt(HunterStage state) {

        while(!state.onKamino()){
            int id=state.currentID();
            PlanetStatus[] nei=state.neighbors();
            double max=0;
            PlanetStatus nextPlanet = null;
            for(int i=0; i<nei.length;i++){
                double temp=nei[i].signal();
                if(temp>=max){
                    max=temp;
                    nextPlanet=nei[i];
                }
            }

            state.moveTo(nextPlanet.id());


        }

    }

    @Override
    public void gather(GathererStage state) {
        // TODO: Implement this method
    }

}
