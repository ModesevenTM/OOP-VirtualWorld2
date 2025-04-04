package swiat.rosliny;

import swiat.Roslina;
import swiat.Wspolrzedne;

import java.awt.*;

public class WilczeJagody extends Roslina {
    private static final short WILCZE_JAGODY_SILA = 99;
    public WilczeJagody(Wspolrzedne ws)
    {
        super(ws, WILCZE_JAGODY_SILA);
    }
    public WilczeJagody(Wspolrzedne ws, short w)
    {
        super(ws, WILCZE_JAGODY_SILA, w);
    }
    @Override
    public final boolean czySmiercionosna()
    {
        return true;
    }
    @Override
    public final String nazwa()
    {
        return "Wilcze jagody";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.MAGENTA;
    }
    @Override
    public WilczeJagody klonuj(Wspolrzedne ws)
    {
        WilczeJagody klon = new WilczeJagody(ws);
        klon.setSwiat(swiat);
        klon.setWiek(NOWORODEK);
        return klon;
    }
}
