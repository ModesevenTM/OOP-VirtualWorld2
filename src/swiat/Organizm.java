package swiat;

import java.awt.*;

public abstract class Organizm {
    public static final short NOWORODEK = -1;
    protected short sila, inicjatywa;
    protected short wiek = 0;
    protected Wspolrzedne polozenie, poprzednie;
    protected boolean zycie = true;
    protected boolean poRozmnozeniu = false;
    protected Swiat swiat = null;

    Organizm(Wspolrzedne ws, short s, short i)
    {
        polozenie = ws;
        poprzednie = ws;
        sila = s;
        inicjatywa = i;
    }
    Organizm(Wspolrzedne ws, short s, short i, short w)
    {
        polozenie = ws;
        poprzednie = ws;
        sila = s;
        inicjatywa = i;
        wiek = w;
    }

    public abstract void akcja();
    public abstract void kolizja();
    public abstract Color rysowanie();
    public abstract String nazwa();

    public final short getSila()
    {
        return sila;
    }
    public final short getInicjatywa()
    {
        return inicjatywa;
    }
    public final short getWiek()
    {
        return wiek;
    }
    public final Wspolrzedne getPolozenie()
    {
        return polozenie;
    }
    public final boolean getZycie()
    {
        return zycie;
    }
    public Swiat getSwiat()
    {
        return swiat;
    }

    public void setSila(short s)
    {
        sila = s;
    }
    public void setWiek(short w)
    {
        wiek = w;
    }
    public void setMartwy()
    {
        zycie = false;
    }
    public void setSwiat(Swiat sw)
    {
        swiat = sw;
    }

    public void zwiekszWiek()
    {
        wiek++;
    }
    public abstract void zmienStatusRozmnozenia();
    public abstract Organizm klonuj(Wspolrzedne ws);
}
