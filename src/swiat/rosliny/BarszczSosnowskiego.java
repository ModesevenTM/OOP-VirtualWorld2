package swiat.rosliny;

import swiat.Organizm;
import swiat.Roslina;
import swiat.Wspolrzedne;
import swiat.Zwierze;

import java.awt.*;

public class BarszczSosnowskiego extends Roslina {
    private static final short SOSNOWSKI_SILA = 10;

    public BarszczSosnowskiego(Wspolrzedne ws)
    {
        super(ws, SOSNOWSKI_SILA);
    }
    public BarszczSosnowskiego(Wspolrzedne ws, short w)
    {
        super(ws, SOSNOWSKI_SILA, w);
    }
    @Override
    public void akcja()
    {
        for (int x = polozenie.x - 1; x <= polozenie.x + 1; x++)
        {
            for (int y = polozenie.y - 1; y <= polozenie.y + 1; y++)
            {
                Organizm pozycja = swiat.getZajete(new Wspolrzedne(x,y));
                if (pozycja instanceof Zwierze && pozycja.getZycie())
                {
                    String zdarzenie = String.format("%s(%d,%d) zabil %s(%d,%d)", nazwa(), polozenie.x, polozenie.y, pozycja.nazwa(), pozycja.getPolozenie().x, pozycja.getPolozenie().y);
                    swiat.dodajZdarzenie(zdarzenie);
                    pozycja.setMartwy();
                }
            }
        }
        super.akcja();
    }
    @Override
    public final boolean czySmiercionosna()
    {
        return true;
    }
    @Override
    public final String nazwa()
    {
        return "Barszcz Sosnowskiego";
    }
    @Override
    public final Color rysowanie()
    {
        return new Color(230, 255, 148);
    }
    @Override
    public BarszczSosnowskiego klonuj(Wspolrzedne ws)
    {
        BarszczSosnowskiego klon = new BarszczSosnowskiego(ws);
        klon.setSwiat(swiat);
        klon.setWiek(NOWORODEK);
        return klon;
    }
}
