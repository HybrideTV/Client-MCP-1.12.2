package scclient.mods;

import scclient.hud.HUDManager;
import scclient.mods.impl.ArmorStatus;
import scclient.mods.impl.KeyStrokes;

public class ModInstance
{
    private static KeyStrokes keyStrokes;
    private static ArmorStatus armorStatus;

    public static void register(HUDManager api)
    {
        keyStrokes = new KeyStrokes();
        api.register(keyStrokes);
        armorStatus = new ArmorStatus();
        api.register(armorStatus);
    }
}
