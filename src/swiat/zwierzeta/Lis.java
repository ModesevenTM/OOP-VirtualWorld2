package swiat.zwierzeta;

import swiat.Wspolrzedne;
import swiat.Zwierze;

import java.awt.*;

public class Lis extends Zwierze {
    public static final short LIS_SILA = 3;
    private static final short LIS_INICJATYWA = 7;

    public Lis(Wspolrzedne ws)
    {
        super(ws, LIS_SILA, LIS_INICJATYWA);
    }
    public Lis(Wspolrzedne ws, short s, short w)
    {
        super(ws, s, LIS_INICJATYWA, w);
    }
    @Override
    public final String nazwa()
    {
        return "Lis";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.ORANGE;
    }
    @Override
    protected final boolean czyDobryWech()
    {
        return true;
    }
    @Override
    public Lis klonuj(Wspolrzedne ws)
    {
        Lis klon = new Lis(ws);
        klon.setSwiat(getSwiat());
        klon.setWiek(NOWORODEK);
        return klon;
    }
    @Override
    protected final boolean czyOdbilAtak(Zwierze atakujacy)
    {
        return false;
    }
    @Override
    protected final boolean czyMozeUciec()
    {
        return false;
    }
}
