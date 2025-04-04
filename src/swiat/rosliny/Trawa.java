package swiat.rosliny;

import swiat.Roslina;
import swiat.Wspolrzedne;

import java.awt.*;

public class Trawa extends Roslina {
    private static final short TRAWA_SILA = 0;
    public Trawa(Wspolrzedne ws)
    {
        super(ws, TRAWA_SILA);
    }
    public Trawa(Wspolrzedne ws, short w)
    {
        super(ws, TRAWA_SILA, w);
    }
    @Override
    public final boolean czySmiercionosna()
    {
        return false;
    }
    @Override
    public final String nazwa()
    {
        return "Trawa";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.GREEN;
    }
    @Override
    public Trawa klonuj(Wspolrzedne ws)
    {
        Trawa klon = new Trawa(ws);
        klon.setSwiat(swiat);
        klon.setWiek(NOWORODEK);
        return klon;
    }
}
