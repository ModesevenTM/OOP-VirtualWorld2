package swiat;

import java.util.Objects;
import java.util.Vector;

public abstract class Zwierze extends Organizm {
    protected short zasieg = 1;

    public Zwierze(Wspolrzedne ws, short s, short i)
    {
        super(ws, s, i);
    }
    public Zwierze(Wspolrzedne ws, short s, short i, short w)
    {
        super(ws, s, i, w);
    }
    @Override
    public void akcja() {
        if (!czyDobryWech() || !czyWszyscySilniejsi()) {
            Wspolrzedne nowe = polozenie;
            Vector<Wspolrzedne> kierunki = new Vector<>();
            if (polozenie.x - zasieg >= 0)
                kierunki.add(new Wspolrzedne(polozenie.x - zasieg, polozenie.y));
            if (polozenie.x + zasieg < swiat.getSzerokosc())
                kierunki.add(new Wspolrzedne(polozenie.x + zasieg, polozenie.y));
            if (polozenie.y - zasieg >= 0)
                kierunki.add(new Wspolrzedne(polozenie.x, polozenie.y - zasieg));
            if (polozenie.y + zasieg < swiat.getWysokosc())
                kierunki.add(new Wspolrzedne(polozenie.x, polozenie.y + zasieg));
            if (kierunki.size() > 0) {
                while (true) {
                    nowe = kierunki.get((int) (Math.random() * kierunki.size()));
                    poprzednie = polozenie;
                    polozenie = nowe;
                    if (czyDobryWech() && swiat.getKolizja(this) != null && swiat.getKolizja(this).getSila() > sila)
                        polozenie = poprzednie;
                    else {
                        String zdarzenie = String.format("%s (%d,%d) -> (%d,%d)", nazwa(), poprzednie.x, poprzednie.y, polozenie.x, polozenie.y);
                        swiat.dodajZdarzenie(zdarzenie);
                        break;
                    }
                }
            }
        }
    }
    @Override
    public void kolizja()
    {
        Organizm kolidujacy = swiat.getKolizja(this);
        if (kolidujacy != null)
        {
            if (Objects.equals(this.nazwa(), kolidujacy.nazwa()))
                rozmnoz((Zwierze) kolidujacy);
		    else if (kolidujacy instanceof Zwierze)
                walcz((Zwierze)kolidujacy);
            else
                zjedz((Roslina)kolidujacy);
        }
    }

    public void walcz(Zwierze kolidujacy)
    {
        if (!ucieknij() && !kolidujacy.ucieknij())
        {
            if (sila < kolidujacy.sila) {
                if (czyOdbilAtak(kolidujacy))
                kolidujacy.polozenie = kolidujacy.poprzednie;
			else
                {
                    String zdarzenie = String.format("%s zabil %s (%d, %d)", kolidujacy.nazwa(), nazwa(), polozenie.x, polozenie.y);
                    swiat.dodajZdarzenie(zdarzenie);
                    setMartwy();
                }
            }
            else {
                if (kolidujacy.czyOdbilAtak(this))
                    polozenie = poprzednie;
			else
                {
                    String zdarzenie = String.format("%s zabil %s (%d, %d)", nazwa(), kolidujacy.nazwa(), polozenie.x, polozenie.y);
                    swiat.dodajZdarzenie(zdarzenie);
                    kolidujacy.setMartwy();
                }
            }
        }
    }
    public void zjedz(Roslina kolidujacy)
    {
        if (kolidujacy.getZycie())
        {
            kolidujacy.reakcja(this);
            kolidujacy.setMartwy();
            String zdarzenie = String.format("%s zjadl %s (%d, %d)", nazwa(), kolidujacy.nazwa(), polozenie.x, polozenie.y);
            swiat.dodajZdarzenie(zdarzenie);
            if (kolidujacy.czySmiercionosna())
            {
                zdarzenie = String.format("%s zabil %s (%d, %d)", kolidujacy.nazwa(), nazwa(), polozenie.x, polozenie.y);
                swiat.dodajZdarzenie(zdarzenie);
                setMartwy();
            }
        }
    }

    public final boolean czyWszyscySilniejsi()
    {
        Vector<Wspolrzedne> mozliwosci = new Vector<>();
        mozliwosci.add(new Wspolrzedne(polozenie.x - 1, polozenie.y));
        mozliwosci.add(new Wspolrzedne(polozenie.x + 1, polozenie.y));
        mozliwosci.add(new Wspolrzedne(polozenie.x, polozenie.y - 1));
        mozliwosci.add(new Wspolrzedne(polozenie.x, polozenie.y + 1));

        for (Wspolrzedne ws : mozliwosci)
        {
            Organizm pozycja = swiat.getZajete(ws);
            if (pozycja != this && (pozycja == null || pozycja.getSila() <= sila))
                return false;
        }
        return true;
    }
    @Override
    public void zmienStatusRozmnozenia() {
        poRozmnozeniu = false;
    }
    protected abstract boolean czyOdbilAtak(Zwierze atakujacy);
    protected abstract boolean czyMozeUciec();
    protected abstract boolean czyDobryWech();

    protected boolean ucieknij()
    {
        if (czyMozeUciec())
        {
            Wspolrzedne ucieczka = swiat.getWolneObok(polozenie);
            if (ucieczka.x != polozenie.x || ucieczka.y != polozenie.y)
            {
                String zdarzenie = String.format("%s uciekl [(%d,%d)->(%d,%d)]", nazwa(), polozenie.x, polozenie.y, ucieczka.x, ucieczka.y);
                polozenie = ucieczka;
                swiat.dodajZdarzenie(zdarzenie);
                return true;
            }
        }
        return false;
    }
    private void rozmnoz(Zwierze rodzic)
    {
        if (rodzic.getWiek() > NOWORODEK) {
            polozenie = poprzednie;
            Wspolrzedne wolne = swiat.getWolneObok(rodzic.polozenie);
            if ((wolne.x != rodzic.polozenie.x || wolne.y != rodzic.polozenie.y) && !poRozmnozeniu && !rodzic.poRozmnozeniu)
            {
                Zwierze dziecko = (Zwierze) klonuj(wolne);
                swiat.dodajOrganizm(dziecko);
                String zdarzenie = String.format("Nowe zwierze: %s (%d, %d)", nazwa(), wolne.x, wolne.y);
                swiat.dodajZdarzenie(zdarzenie);
                poRozmnozeniu = true;
                rodzic.poRozmnozeniu = true;
            }
        }
    }
}
