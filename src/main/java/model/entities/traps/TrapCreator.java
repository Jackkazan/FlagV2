package model.entities.traps;

import model.entities.Prototype;
import model.tile.MapManager;

import java.util.ArrayList;
import java.util.List;

import static view.GamePanel.tileSize;

public class TrapCreator {

    static TrapPrototype prototypeManager;

    public static List<Trap> createTraps(MapManager mapManager){

        List<Trap> trapList = new ArrayList<>();

        String spikeStaticImage = "/traps/Spike/spikeAnimations_00.png";

        String spikeAnimation_1="/traps/Spike/spikeAnimations_00.png";
        String spikeAnimation_2="/traps/Spike/spikeAnimations_01.png";
        String spikeAnimation_3="/traps/Spike/spikeAnimations_02.png";
        String spikeAnimation_4="/traps/Spike/spikeAnimations_03.png";
        String spikeAnimation_5="/traps/Spike/spikeAnimations_04.png";
        String spikeAnimation_6="/traps/Spike/spikeAnimations_05.png";
        String spikeAnimation_7="/traps/Spike/spikeAnimations_06.png";
        String spikeAnimation_8="/traps/Spike/spikeAnimations_07.png";
        String spikeAnimation_9="/traps/Spike/spikeAnimations_08.png";
        String spikeAnimation_10="/traps/Spike/spikeAnimations_09.png";
        String spikeAnimation_11="/traps/Spike/spikeAnimations_10.png";
        String spikeAnimation_12="/traps/Spike/spikeAnimations_11.png";

        Trap spike1 = new Trap.TrapBuilder(16,44)
                .setName("Spike1")
                .setDamage(2)
                .setStaticImage(spikeStaticImage)
                .set12AnimateImages(spikeAnimation_1 ,spikeAnimation_2 ,spikeAnimation_3 ,spikeAnimation_4 ,
                        spikeAnimation_5 ,spikeAnimation_6 ,spikeAnimation_7 ,spikeAnimation_8 ,
                        spikeAnimation_9,spikeAnimation_10 ,spikeAnimation_11 ,spikeAnimation_12)
                .setImageDimension(32,32)
                .setScale(1)
                .setAttackArea(16,44,16,16)
                .setContainedMap(mapManager.getTileManagerDungeonSud())
                .build();

        prototypeManager = new TrapPrototype(spike1);

        Prototype spike2 = prototypeManager.createTrap("Spike2",18,44);


        trapList.add(spike1);
        trapList.add((Trap) spike2);

        return trapList;
    }
}
