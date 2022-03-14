package scclient.hud;

public interface IRenderConfig
{
    public void save(ScreenPosition pos);

    public ScreenPosition load();
}
