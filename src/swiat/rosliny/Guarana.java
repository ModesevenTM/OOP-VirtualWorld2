package swiat.rosliny;

import swiat.Organizm;
import swiat.Roslina;
import swiat.Wspolrzedne;

import java.awt.*;

public class Guarana extends Roslina {
    private static final short GUARANA_SILA = 0;
    private static final short GUARANA_KOLIZJA = 3;
    public Guarana(Wspolrzedne ws)
    {
        super(ws, GUARANA_SILA);
    }
    public Guarana(Wspolrzedne ws, short w)
    {
        super(ws, (short)0, w);
    }
    @Override
    public void reakcja(Organizm drugi)
    {
        String zdarzenie = String.format("%s (%d, %d) - sila: %d->%d", drugi.nazwa(), drugi.getPolozenie().x, drugi.getPolozenie().y, drugi.getSila(), drugi.getSila() + GUARANA_KOLIZJA);
        swiat.dodajZdarzenie(zdarzenie);
        drugi.setSila((short) (drugi.getSila() + GUARANA_KOLIZJA));
    }
    @Override
    public final boolean czySmiercionosna()
    {
        return false;
    }
    @Override
    public final String nazwa()
    {
        return "Guarana";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.RED;
    }
    @Override
    public Guarana klonuj(Wspolrzedne ws)
    {
        Guarana klon = new Guarana(ws);
        klon.setSwiat(swiat);
        klon.setWiek(NOWORODEK);
        return klon;
    }
}
