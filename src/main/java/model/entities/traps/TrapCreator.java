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

        Trap spike1 = new Trap.TrapBuilder(24,44)
                .setName("Spike1")
                .setDamage(2)
                .setStaticImage(spikeStaticImage)
                .set12AnimateImages(spikeAnimation_1 ,spikeAnimation_2 ,spikeAnimation_3 ,spikeAnimation_4 ,
                        spikeAnimation_5 ,spikeAnimation_6 ,spikeAnimation_7 ,spikeAnimation_8 ,
                        spikeAnimation_9,spikeAnimation_10 ,spikeAnimation_11 ,spikeAnimation_12)
                .setImageDimension(32,32)
                .setScale(2)
                .setAttackArea(24,44,64,64)
                .setContainedMap(mapManager.getTileManagerDungeonSud())
                .build();

        prototypeManager = new TrapPrototype(spike1);

        //prima fila orrizontale
        Prototype spike2 = prototypeManager.createTrap("Spike2",28,44);
        Prototype spike3 = prototypeManager.createTrap("Spike3",32,44);
        Prototype spike4 = prototypeManager.createTrap("Spike4",36,44);
        Prototype spike5 = prototypeManager.createTrap("Spike5",40,44);
        Prototype spike6 = prototypeManager.createTrap("Spike6",44,44);
        Prototype spike7 = prototypeManager.createTrap("Spike7",48,44);

        //prima fila verticale
        Prototype spike8 = prototypeManager.createTrap("Spike8",48,40);
        Prototype spike9 = prototypeManager.createTrap("Spike9",48,36);
        Prototype spike10 = prototypeManager.createTrap("Spike10",48,32);
        Prototype spike11 = prototypeManager.createTrap("Spike11",48,28);

        //seconda fila orizzantale
        Prototype spike12 = prototypeManager.createTrap("Spike12",44,28);
        Prototype spike13 = prototypeManager.createTrap("Spike13",40,28);
        Prototype spike14 = prototypeManager.createTrap("Spike14",36,28);
        Prototype spike15 = prototypeManager.createTrap("Spike15",32,28);
        Prototype spike16 = prototypeManager.createTrap("Spike16",28,28);
        Prototype spike17 = prototypeManager.createTrap("Spike17",24,28);
        Prototype spike18 = prototypeManager.createTrap("Spike18",20,28);
        Prototype spike19 = prototypeManager.createTrap("Spike19",16,28);

        //seconda fila verticale
        Prototype spike20 = prototypeManager.createTrap("Spike20",16,24);
        Prototype spike21 = prototypeManager.createTrap("Spike21",16,20);
        Prototype spike22 = prototypeManager.createTrap("Spike22",16,16);
        Prototype spike23 = prototypeManager.createTrap("Spike23",16,12);

        //terza fila orizzontale
        Prototype spike24 = prototypeManager.createTrap("Spike24",20,12);
        Prototype spike25 = prototypeManager.createTrap("Spike25",24,12);
        Prototype spike26 = prototypeManager.createTrap("Spike26",28,12);
        Prototype spike27 = prototypeManager.createTrap("Spike27",32,12);
        Prototype spike28 = prototypeManager.createTrap("Spike28",36,12);
        Prototype spike29 = prototypeManager.createTrap("Spike29",40,12);
        Prototype spike30 = prototypeManager.createTrap("Spike30",44,12);
        Prototype spike31 = prototypeManager.createTrap("Spike31",48,12);



        trapList.add(spike1);
        trapList.add((Trap) spike2);
        trapList.add((Trap) spike3);
        trapList.add((Trap) spike4);
        trapList.add((Trap) spike5);
        trapList.add((Trap) spike6);
        trapList.add((Trap) spike7);
        trapList.add((Trap) spike8);
        trapList.add((Trap) spike9);
        trapList.add((Trap) spike10);
        trapList.add((Trap) spike11);
        trapList.add((Trap) spike12);
        trapList.add((Trap) spike13);
        trapList.add((Trap) spike14);
        trapList.add((Trap) spike15);
        trapList.add((Trap) spike16);
        trapList.add((Trap) spike17);
        trapList.add((Trap) spike18);
        trapList.add((Trap) spike19);
        trapList.add((Trap) spike20);
        trapList.add((Trap) spike21);
        trapList.add((Trap) spike22);
        trapList.add((Trap) spike23);
        trapList.add((Trap) spike24);
        trapList.add((Trap) spike25);
        trapList.add((Trap) spike26);
        trapList.add((Trap) spike27);
        trapList.add((Trap) spike28);
        trapList.add((Trap) spike29);
        trapList.add((Trap) spike30);
        trapList.add((Trap) spike31);

        return trapList;
    }
}
