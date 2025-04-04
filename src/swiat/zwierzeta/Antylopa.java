package swiat.zwierzeta;

import swiat.Wspolrzedne;
import swiat.Zwierze;

import java.awt.*;

public class Antylopa extends Zwierze {
    public static final short ANTYLOPA_SILA = 4;
    private static final short ANTYLOPA_INICJATYWA = 4;
    private static final short ANTYLOPA_ZASIEG = 2;
    private static final short ANTYLOPA_DZIELNIK = 2;
    private static final short ANTYLOPA_SZANSE = 1;

    public Antylopa(Wspolrzedne ws)
    {
        super(ws, ANTYLOPA_SILA, ANTYLOPA_INICJATYWA);
        zasieg = ANTYLOPA_ZASIEG;
    }
    public Antylopa(Wspolrzedne ws, short s, short w)
    {
        super(ws, s, ANTYLOPA_INICJATYWA, w);
        zasieg = ANTYLOPA_ZASIEG;
    }
    @Override
    public final String nazwa()
    {
        return "Antylopa";
    }
    @Override
    public final Color rysowanie()
    {
        return new Color(171, 118, 2);
    }
    @Override
    public Antylopa klonuj(Wspolrzedne ws)
    {
        Antylopa klon = new Antylopa(ws);
        klon.setSwiat(getSwiat());
        klon.setWiek(NOWORODEK);
        return klon;
    }
    @Override
    protected final boolean czyMozeUciec()
    {
        return Math.random() * ANTYLOPA_DZIELNIK < ANTYLOPA_SZANSE;
    }
    @Override
    protected final boolean czyOdbilAtak(Zwierze atakujacy)
    {
        return false;
    }
    @Override
    protected final boolean czyDobryWech()
    {
        return false;
    }
}
