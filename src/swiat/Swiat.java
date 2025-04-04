package swiat;

import java.util.Vector;

import static swiat.Organizm.NOWORODEK;

public class Swiat {
    public static final int CZLOWIEK_WZROST = 5;
    private short wysokosc, szerokosc;
    private int tura = 0;
    private Vector<Organizm> organizmy = new Vector<>();
    private Vector<String> zdarzenia = new Vector<>();
    private Kierunki ruchCzlowieka = Kierunki.stoi;
    private int kiedyUmiejetnosc = -CZLOWIEK_WZROST * 2;
    private boolean czyCzlowiekZyje = true;

    public Swiat(short sz, short wys)
    {
        szerokosc = sz;
        wysokosc = wys;
    }
    public enum Kierunki {
        stoi,
        gora,
        dol,
        lewo,
        prawo
    };
    public final short getSzerokosc()
    {
        return szerokosc;
    }
    public final short getWysokosc()
    {
        return wysokosc;
    }
    public final int getTura()
    {
        return tura;
    }
    public final Kierunki getRuchCzlowieka()
    {
        return ruchCzlowieka;
    }
    public final String getNazwaRuchu()
    {
        return switch (ruchCzlowieka) {
            case gora -> "góra";
            case dol -> "dół";
            case lewo -> "lewo";
            case prawo -> "prawo";
            default -> "brak";
        };
    }
    public final int getKiedyUmiejetnosc()
    {
        return kiedyUmiejetnosc;
    }
    public final boolean getZycieCzlowieka()
    {
        return czyCzlowiekZyje;
    }

    public void setSzerokosc(short s)
    {
        szerokosc = s;
    }
    public void setWysokosc(short w)
    {
        wysokosc = w;
    }
    public void setTura(int t)
    {
        tura = t;
    }
    public void setRuchCzlowieka(Kierunki k)
    {
        ruchCzlowieka = k;
    }
    public void setKiedyUmiejetnosc(int ku)
    {
        kiedyUmiejetnosc = ku;
    }
    public void setZycieCzlowieka(boolean z)
    {
        czyCzlowiekZyje = z;
    }

    public void dodajOrganizm(Organizm org)
    {
        org.setSwiat(this);
        organizmy.add(org);
    }
    public final Organizm getZajete(Wspolrzedne ws)
    {
        Organizm zwroc = null;
        for (Organizm organizm : organizmy) {
            Wspolrzedne obecny = organizm.getPolozenie();
            if (ws.x == obecny.x && ws.y == obecny.y && (zwroc == null || (organizm.getSila() > zwroc.getSila())))
                zwroc = organizm;
        }
        return zwroc;
    }
    public final Organizm getKolizja(Organizm org)
    {
        for (Organizm organizm : organizmy) {
            if (org != organizm && org.getPolozenie().x == organizm.getPolozenie().x && org.getPolozenie().y == organizm.getPolozenie().y && org.getWiek() > NOWORODEK)
                return organizm;
        }
        return null;
    }
    public final Wspolrzedne getWolneObok(Wspolrzedne ws)
    {
        Vector<Wspolrzedne> kierunki = new Vector<>();
        if (ws.x - 1 >= 0 && getZajete(new Wspolrzedne(ws.x - 1, ws.y)) == null)
            kierunki.add(new Wspolrzedne(ws.x - 1, ws.y));
        if (ws.x + 1 < szerokosc && getZajete(new Wspolrzedne(ws.x + 1, ws.y)) == null)
            kierunki.add(new Wspolrzedne(ws.x + 1, ws.y));
        if (ws.y - 1 >= 0 && getZajete(new Wspolrzedne(ws.x, ws.y - 1)) == null)
            kierunki.add(new Wspolrzedne(ws.x, ws.y - 1));
        if (ws.y + 1 < wysokosc && getZajete(new Wspolrzedne(ws.x, ws.y + 1)) == null)
            kierunki.add(new Wspolrzedne(ws.x, ws.y + 1));

        if (kierunki.size() > 0)
            return kierunki.get((int) (Math.random() * kierunki.size()));
        else
            return ws;
    }
    public Vector<Organizm> getOrganizmy()
    {
        return organizmy;
    }

    public void usunMartwe()
    {
        for (int i = organizmy.size() - 1; i >= 0; i--)
        {
            organizmy.get(i).zmienStatusRozmnozenia();
            if (!organizmy.get(i).getZycie())
                organizmy.remove(i);
        }
    }
    void usunWszystkie()
    {
        organizmy.removeAllElements();
    }

    public Vector<String> getZdarzenia()
    {
        return zdarzenia;
    }
    public void dodajZdarzenie(String zd)
    {
        zdarzenia.add(zd);
    }
    public void wyczyscZdarzenia()
    {
        zdarzenia.removeAllElements();
    }

    public void przeprowadzTure()
    {
        organizmy.sort((pierwszy, drugi) -> {
            if (pierwszy.getInicjatywa() == drugi.getInicjatywa())
                return drugi.getWiek() - pierwszy.getWiek();
            return drugi.getInicjatywa() - pierwszy.getInicjatywa();
        });
        for (int i = 0; i < organizmy.size(); i++) {
            if (organizmy.get(i).getZycie() && organizmy.get(i).getWiek() > NOWORODEK) {
                organizmy.get(i).akcja();
                organizmy.get(i).kolizja();
            }
            organizmy.get(i).zwiekszWiek();
        }
    }
    public void nowaTura()
    {
        wyczyscZdarzenia();
        tura++;
        przeprowadzTure();
        usunMartwe();
    }
    public void uzyjUmiejetnosci()
    {
        if (tura + 1 >= kiedyUmiejetnosc + 2 * CZLOWIEK_WZROST)
            kiedyUmiejetnosc = tura + 1;
    }
}
