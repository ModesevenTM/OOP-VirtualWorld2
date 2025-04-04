package swiat.zwierzeta;

import swiat.Wspolrzedne;
import swiat.Zwierze;

import java.awt.*;

public class Zolw extends Zwierze {
    public static final short ZOLW_SILA = 2;
    private static final short ZOLW_INICJATYWA = 1;
    private static final short ZOLW_DZIELNIK_SZANS = 4;
    private static final short ZOLW_SZANSE = 1;
    private static final short ZOLW_ODPARCIE = 5;
    public Zolw(Wspolrzedne ws)
    {
        super(ws, ZOLW_SILA, ZOLW_INICJATYWA);
    }
    public Zolw(Wspolrzedne ws, short s, short w)
    {
        super(ws, s, ZOLW_INICJATYWA, w);
    }
    @Override
    public void akcja()
    {
        if (Math.random() * ZOLW_DZIELNIK_SZANS < ZOLW_SZANSE)
            super.akcja();
    }
    @Override
    public final String nazwa()
    {
        return "Zolw";
    }
    @Override
    public final Color rysowanie()
    {
        return new Color(0, 97, 8);
    }
    @Override
    public Zolw klonuj(Wspolrzedne ws)
    {
        Zolw klon = new Zolw(ws);
        klon.setSwiat(getSwiat());
        klon.setWiek(NOWORODEK);
        return klon;
    }
    @Override
    protected final boolean czyOdbilAtak(Zwierze atakujacy)
    {
        return atakujacy.getSila() < ZOLW_ODPARCIE;
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
