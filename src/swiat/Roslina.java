package swiat;

public abstract class Roslina extends Organizm{
    private static final short ROZMNOZ_DZIELNIK = 20;
    private static final short ROZMNOZ_SZANSA = 1;
    public Roslina(Wspolrzedne ws, short s)
    {
        super(ws, s, (short) 0);
    }
    public Roslina(Wspolrzedne ws, short s, short w)
    {
        super(ws, s, (short) 0, w);
    }
    @Override
    public void akcja()
    {
        if (Math.random() * ROZMNOZ_DZIELNIK < ROZMNOZ_SZANSA && wiek > NOWORODEK)
        {
            Wspolrzedne obok = swiat.getWolneObok(polozenie);
            if (polozenie.x != obok.x || polozenie.y != obok.y)
            {
                Organizm nowa = klonuj(obok);
                swiat.dodajOrganizm(nowa);
                String zdarzenie = String.format("Nowa roslina: %s (%d, %d)", nazwa(), obok.x, obok.y);
                swiat.dodajZdarzenie(zdarzenie);
            }
        }
    }
    @Override
    public void kolizja() {};
    public void reakcja(Organizm drugi) {};
    public abstract boolean czySmiercionosna();
    @Override
    public void zmienStatusRozmnozenia() {}
}
