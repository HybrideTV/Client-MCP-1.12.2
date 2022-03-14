package scclient.c;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import scclient.FileManager;
import scclient.SplashProgress;
import scclient.event.EventManager;
import scclient.event.EventTarget;
import scclient.event.impl.ClientTickEvent;
import scclient.hud.HUDManager;
import scclient.mods.ModInstance;

public class Client
{
    private static final Client INSTANCE = new Client();
    private Minecraft mc = Minecraft.getMinecraft();

    private HUDManager hudManager;

    public static final Client getInstance()
    {
        return INSTANCE;
    }

    public void init()
    {
        FileManager.init();
        SplashProgress.setProgress(1, "Initialisation du jeu");
        this.discordRPC();
        EventManager.register(this);
    }

    public void start()
    {
        hudManager = HUDManager.getInstance();
        ModInstance.register(hudManager);
    }

    public void shutdown()
    {
    }

    @EventTarget
    public void onTick(ClientTickEvent e)
    {
        if (Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POS.isPressed())
        {
            hudManager.openConfigScreen();
        }
    }

    private void discordRPC()
    {
        DiscordRPC discord = DiscordRPC.INSTANCE;
        String applicationId = "797405130706124821";
        String steamId = "";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        discord.Discord_Initialize(applicationId, handlers, true, steamId);
        DiscordRichPresence drp = new DiscordRichPresence();
        drp.startTimestamp = System.currentTimeMillis() / 1000;
        drp.largeImageKey = "logo";
        drp.largeImageText = "discord.gg/87z7f6g";
        drp.details = "StudyCorp Client";
        drp.state = "Dans les menus";
        new Thread(() ->
        {
            System.out.println("THREAD STARTED");

            while (!Thread.currentThread().isInterrupted())
            {
                try
                {
                    if (Minecraft.getMinecraft().isSingleplayer())
                    {
                        drp.details = "Joue en solo";
                        drp.state = null;
                        discord.Discord_UpdatePresence(drp);
                    }
                    else if (!Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().getConnection() != null)
                    {
                        drp.details = "Joue en multi";
                        drp.state = "En ligne: " + mc.getConnection().getPlayerInfoMap().size();
                        discord.Discord_UpdatePresence(drp);
                    }
                    else
                    {
                        drp.details = "Hors jeu";
                    }

                    Thread.sleep(5000L);
                }
                catch (InterruptedException ignored)
                {
                }
            }
        }, "DISCORD-RPC-Thread").start();
        discord.Discord_UpdatePresence(drp);
    }
}
