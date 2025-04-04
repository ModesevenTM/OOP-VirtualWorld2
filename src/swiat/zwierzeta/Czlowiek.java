package swiat.zwierzeta;

import swiat.Organizm;
import swiat.Swiat;
import swiat.Wspolrzedne;
import swiat.Zwierze;

import java.awt.*;

public class Czlowiek extends Zwierze {
    public static final short CZLOWIEK_SILA = 5;
    private static final short CZLOWIEK_INICJATYWA = 4;
    private static final short CZLOWIEK_WZROST = 5;

    public Czlowiek(Wspolrzedne ws)
    {
        super(ws, CZLOWIEK_SILA, CZLOWIEK_INICJATYWA);
    }
    public Czlowiek(Wspolrzedne ws, short s, short w)
    {
        super(ws, s, CZLOWIEK_INICJATYWA, w);
    }
    @Override
    public void akcja()
    {
        if (swiat.getTura() - swiat.getKiedyUmiejetnosc() <= CZLOWIEK_WZROST)
        {
            String zdarzenie;
            if (swiat.getTura() == swiat.getKiedyUmiejetnosc())
            {
                zdarzenie = String.format("%s (%d, %d) - sila: %d->%d", nazwa(), polozenie.x, polozenie.y, sila, sila + CZLOWIEK_WZROST);
                sila += CZLOWIEK_WZROST;
            }
            else
            {
                zdarzenie = String.format("%s (%d, %d) - sila: %d->%d", nazwa(), polozenie.x, polozenie.y, sila, sila - 1);
                sila--;
            }
            swiat.dodajZdarzenie(zdarzenie);
        }

        poprzednie = polozenie;
        switch (swiat.getRuchCzlowieka())
        {
            case gora:
            {
                if (polozenie.y - 1 >= 0)
                    polozenie = new Wspolrzedne(polozenie.x, polozenie.y - 1);
                break;
            }
            case dol:
            {
                if (polozenie.y + 1 < swiat.getWysokosc())
                    polozenie = new Wspolrzedne(polozenie.x, polozenie.y + 1);
                break;
            }
            case lewo:
            {
                if (polozenie.x - 1 >= 0)
                    polozenie = new Wspolrzedne(polozenie.x - 1, polozenie.y);
                break;
            }
            case prawo:
            {
                if (polozenie.x + 1 < swiat.getSzerokosc())
                    polozenie = new Wspolrzedne(polozenie.x + 1, polozenie.y);
                break;
            }
            default:
                break;
        }
        if (polozenie.x != poprzednie.x || polozenie.y != poprzednie.y)
        {
            String zdarzenie = String.format("%s (%d,%d) -> (%d,%d)", nazwa(), poprzednie.x, poprzednie.y, polozenie.x, polozenie.y);
            swiat.dodajZdarzenie(zdarzenie);
        }
    }
    @Override
    public void zwiekszWiek()
    {
        super.zwiekszWiek();
        swiat.setRuchCzlowieka(Swiat.Kierunki.stoi);
    }
    @Override
    public void setMartwy()
    {
        super.setMartwy();
        swiat.setZycieCzlowieka(false);
    }
    @Override
    public final String nazwa()
    {
        return "Czlowiek";
    }
    @Override
    public final Color rysowanie()
    {
        return Color.BLACK;
    }
    @Override
    public Czlowiek klonuj(Wspolrzedne ws)
    {
        Czlowiek klon = new Czlowiek(ws);
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
