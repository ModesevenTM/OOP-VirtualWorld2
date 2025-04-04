package swiat.zwierzeta;

import swiat.Wspolrzedne;
import swiat.Zwierze;

import java.awt.*;

public class Wilk extends Zwierze {
    public static final short WILK_SILA = 9;
    private static final short WILK_INICJATYWA = 5;
    public Wilk(Wspolrzedne ws)
    {
        super(ws, WILK_SILA, WILK_INICJATYWA);
    }
    public Wilk(Wspolrzedne ws, short s, short w)
    {
        super(ws, s, WILK_INICJATYWA, w);
    }
    @Override
    public final String nazwa()
    {
        return "Wilk";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.GRAY;
    }
    @Override
    public Wilk klonuj(Wspolrzedne ws)
    {
        Wilk klon = new Wilk(ws);
        klon.setSwiat(getSwiat());
        klon.setWiek(NOWORODEK);
        return klon;
    }
    @Override
    protected boolean czyOdbilAtak(Zwierze atakujacy)
    {
        return false;
    }
    @Override
    protected final boolean czyMozeUciec()
    {
        return false;
    }
    @Override
    protected final boolean czyDobryWech()
    {
        return false;
    }
}
