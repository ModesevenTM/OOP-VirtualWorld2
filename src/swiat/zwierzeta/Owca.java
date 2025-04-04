package swiat.zwierzeta;

import swiat.Wspolrzedne;
import swiat.Zwierze;

import java.awt.*;

public class Owca extends Zwierze {
    public static final short OWCA_SILA = 4;
    private static final short OWCA_INICJATYWA = 4;

    public Owca(Wspolrzedne ws)
    {
        super(ws, OWCA_SILA, OWCA_INICJATYWA);
    }
    public Owca(Wspolrzedne ws, short s, short w)
    {
        super(ws, s, OWCA_INICJATYWA, w);
    }
    @Override
    public final String nazwa()
    {
        return "Owca";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.LIGHT_GRAY;
    }
    @Override
    public Owca klonuj(Wspolrzedne ws)
    {
        Owca klon = new Owca(ws);
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
    @Override
    protected final boolean czyDobryWech()
    {
        return false;
    }
}
