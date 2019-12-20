package model;

import controller.Controller1;

import java.util.HashMap;

/**
 * Created by 28609 on 3/2/2017.
 * this class is mainly used to create a MusicModel1. with a static method create.
 */
public class MusicModel1Creator {


    /**
     * this static method is design to create a MusicModel1 with input volume to determine its volume.
     *
     * @param volume given the size of this music.
     * @return a musicModel1.
     */
    public static MusicModel1 create(int volume) {
        HashMap<Integer, HashMap<Integer, Beat>> wanted = Controller1.createBlankMap();

        return new MusicModel1(wanted, volume, 300);


    }
}
