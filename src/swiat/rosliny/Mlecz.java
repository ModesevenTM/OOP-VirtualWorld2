package swiat.rosliny;

import swiat.Roslina;
import swiat.Wspolrzedne;

import java.awt.*;

public class Mlecz extends Roslina {
    private static final short MLECZ_SILA = 0;
    private static final short MLECZ_AKCJA = 3;
    public Mlecz(Wspolrzedne ws)
    {
        super(ws, MLECZ_SILA);
    }
    public Mlecz(Wspolrzedne ws, short w)
    {
        super(ws, MLECZ_SILA, w);
    }
    @Override
    public void akcja()
    {
        for (int i = 0; i < MLECZ_AKCJA; i++)
            super.akcja();
    }
    @Override
    public final boolean czySmiercionosna()
    {
        return false;
    }
    @Override
    public final String nazwa()
    {
        return "Mlecz";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.YELLOW;
    }
    @Override
    public Mlecz klonuj(Wspolrzedne ws)
    {
        Mlecz klon = new Mlecz(ws);
        klon.setSwiat(swiat);
        klon.setWiek(NOWORODEK);
        return klon;
    }
}
