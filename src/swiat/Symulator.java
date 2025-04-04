package swiat;

import swiat.rosliny.*;
import swiat.zwierzeta.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Objects;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Symulator {
    private static final short MIN_WYMIAR = 10;
    private static final short MAX_WYMIAR = 40;
    private static final short INIT_ORGANIZM = -2;
    private Swiat swiat;
    private final JFrame okno;
    private JButton[][] pola;
    private JButton elixir;
    private JLabel ruchCzlowieka;
    private JLabel nrTury;
    private JScrollPane dziennikKontener;
    private JTextArea dziennik;

    public Symulator() {
        okno = new JFrame("Jakub Wojtkowiak, 193546");
        okno.setFocusable(true);
        okno.setSize(500, 300);
        okno.setDefaultCloseOperation(EXIT_ON_CLOSE);
        okno.setLocationRelativeTo(null);

        JLabel labelX = new JLabel("Szerokość okna:");
        labelX.setBounds(10, 10, 100, 30);
        JLabel labelY = new JLabel("Wysokość okna:");
        labelY.setBounds(10, 60, 100, 30);
        JLabel komunikat = new JLabel();
        komunikat.setBounds(10, 110, 500, 30);
        okno.add(labelX);
        okno.add(labelY);
        okno.add(komunikat);

        final JTextField inputX = new JTextField();
        inputX.setBounds(10, 40, 150, 20);
        okno.add(inputX);
        final JTextField inputY = new JTextField();
        inputY.setBounds(10, 90, 150, 20);
        okno.add(inputY);

        JButton b = new JButton("Potwierdź");
        b.setBounds(30, 170, 100, 40);
        okno.add(b);

        okno.setLayout(null);
        okno.setVisible(true);

        b.addActionListener(e -> {
            try
            {
                short x = (short) Integer.parseInt(inputX.getText());
                short y = (short) Integer.parseInt(inputY.getText());
                if (x < MIN_WYMIAR || x > MAX_WYMIAR || y < MIN_WYMIAR || y > MAX_WYMIAR) {
                    inputX.setText("");
                    inputY.setText("");
                    komunikat.setText("Nieprawidłowe wymiary - każdy powinien zawierać się w przedziale <10,40>.");
                } else {
                    okno.setVisible(false);
                    okno.remove(labelX);
                    okno.remove(labelY);
                    okno.remove(komunikat);
                    okno.remove(inputX);
                    okno.remove(inputY);
                    okno.remove(b);
                    wypelnijPola(x, y);
                    okno.setSize(1000, 800);
                    okno.setLocationRelativeTo(null);
                    okno.setVisible(true);
                    swiat = new Swiat(x, y);
                    generujSwiat();
                    rysujInterfejs();
                    rysowanie();
                }
            } catch (Exception ex) {
                inputX.setText("");
                inputY.setText("");
                komunikat.setText("Wystąpił błąd - spróbuj ponownie.");
                ex.printStackTrace();
            }
        });

        okno.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> swiat.setRuchCzlowieka(Swiat.Kierunki.gora);
                    case KeyEvent.VK_DOWN -> swiat.setRuchCzlowieka(Swiat.Kierunki.dol);
                    case KeyEvent.VK_LEFT -> swiat.setRuchCzlowieka(Swiat.Kierunki.lewo);
                    case KeyEvent.VK_RIGHT -> swiat.setRuchCzlowieka(Swiat.Kierunki.prawo);
                }
                ruchCzlowieka.setText("Ruch człowieka: "+swiat.getNazwaRuchu());
                okno.repaint();
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
    private void graj()
    {
        swiat.nowaTura();
        ruchCzlowieka.setText("Człowiek nie ruszy się");
        rysowanie();
        okno.requestFocus();
    }

    private void generujSwiat() {
        Wspolrzedne pozycja = new Wspolrzedne(0, 0);
        Organizm[] szablony = {
                new BarszczSosnowskiego(pozycja),
                new Guarana(pozycja),
                new Mlecz(pozycja),
                new Trawa(pozycja),
                new WilczeJagody(pozycja),
                new Antylopa(pozycja),
                new Lis(pozycja),
                new Owca(pozycja),
                new Wilk(pozycja),
                new Zolw(pozycja)
        };

        /*swiat.dodajOrganizm(new Owca(new Wspolrzedne(0,2)));
        swiat.dodajOrganizm(new Owca(new Wspolrzedne(0,3)));
        swiat.dodajOrganizm(new Owca(new Wspolrzedne(0,4)));
        swiat.dodajOrganizm(new Owca(new Wspolrzedne(1,4)));
        swiat.dodajOrganizm(new Owca(new Wspolrzedne(1,3)));
        swiat.dodajOrganizm(new Owca(new Wspolrzedne(2,4)));
        swiat.dodajOrganizm(new Owca(new Wspolrzedne(2,3)));*/

        for (int i = 0; i < szablony.length * 3; i++) {
            do
                pozycja = new Wspolrzedne((int) (Math.random() * swiat.getSzerokosc()), (int) (Math.random() * swiat.getWysokosc()));
            while (swiat.getZajete(pozycja) != null);
            Organizm org = szablony[i % szablony.length].klonuj(pozycja);
            org.setWiek((short) 0);
            swiat.dodajOrganizm(org);
        }
        do
            pozycja = new Wspolrzedne((int) (Math.random() * swiat.getSzerokosc()), (int) (Math.random() * swiat.getWysokosc()));
        while (swiat.getZajete(pozycja) != null);
        swiat.dodajOrganizm(new Czlowiek(pozycja));
    }

    private void rysowanie() {
        if(swiat.getZycieCzlowieka())
        {
            ruchCzlowieka.setVisible(true);
            if(swiat.getTura() + 1 >= swiat.getKiedyUmiejetnosc() + 2 * Swiat.CZLOWIEK_WZROST)
                elixir.setVisible(true);
        }
        else
        {
            ruchCzlowieka.setVisible(false);
            elixir.setVisible(false);
        }
        nrTury.setText("Tura nr. "+(swiat.getTura()+1));
        wyczyscPrzyciski();
        rysujPlansze();
        wyswietlDziennikTejTury();
    }

    private void wyczyscPrzyciski() {
        for (int i = 0; i < swiat.getWysokosc(); i++)
            for (int j = 0; j < swiat.getSzerokosc(); j++)
                pola[i][j].setBackground(Color.WHITE);
    }

    private void rysujInterfejs() {
        JButton l = new JButton("Wczytaj");
        l.setBounds(850, 10, 100, 40);
        l.addActionListener(e -> odczyt());
        okno.add(l);
        JButton s = new JButton("Zapisz grę");
        s.setBounds(850, 60, 100, 40);
        s.addActionListener(e -> zapis());
        okno.add(s);
        JButton legend = new JButton("Legenda");
        legend.setBounds(850, 110, 100, 40);
        legend.addActionListener(e -> wyswietlLegende());
        okno.add(legend);
        JButton proceed = new JButton("Zatwierdź");
        proceed.setBounds(850, 160, 100, 40);
        proceed.addActionListener(e -> graj());
        okno.add(proceed);
        JButton exit = new JButton("Wyjdź");
        exit.setBounds(850, 210, 100, 40);
        exit.addActionListener(e -> zakonczSymulacje());
        okno.add(exit);
        elixir = new JButton("Eliksir");
        elixir.setBounds(850, 260, 100, 40);
        elixir.addActionListener(e ->
        {
            swiat.uzyjUmiejetnosci();
            elixir.setVisible(false);
            okno.requestFocus();
        });
        okno.add(elixir);
        ruchCzlowieka = new JLabel("Człowiek nie ruszy się");
        ruchCzlowieka.setBounds(850, 320, 250, 50);
        okno.add(ruchCzlowieka);
        nrTury = new JLabel();
        nrTury.setBounds(850, 340, 250, 50);
        okno.add(nrTury);
        dziennik = new JTextArea(20, 20);
        dziennik.setEditable(false);
        dziennikKontener = new JScrollPane(dziennik);
        dziennikKontener.setBounds(700, 400, 250, 250);
        dziennikKontener.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        okno.add(dziennikKontener);
    }

    private void rysujPlansze() {
        for (Organizm organizm : swiat.getOrganizmy())
            pola[organizm.getPolozenie().y][organizm.getPolozenie().x].setBackground(organizm.rysowanie());
    }

    private void dodajOrganizm(String nazwa, int x, int y, short sila, short wiek) {
        Organizm nowy = null;
        if (Objects.equals(nazwa, "Barszcz Sosnowskiego"))
            nowy = new BarszczSosnowskiego(new Wspolrzedne(x, y), wiek);
        else if (Objects.equals(nazwa, "Guarana"))
            nowy = new Guarana(new Wspolrzedne(x, y), wiek);
        else if (Objects.equals(nazwa, "Mlecz"))
            nowy = new Mlecz(new Wspolrzedne(x, y), wiek);
        else if (Objects.equals(nazwa, "Trawa"))
            nowy = new Trawa(new Wspolrzedne(x, y), wiek);
        else if (Objects.equals(nazwa, "Wilcze jagody"))
            nowy = new WilczeJagody(new Wspolrzedne(x, y), wiek);
        else if (Objects.equals(nazwa, "Antylopa"))
            nowy = new Antylopa(new Wspolrzedne(x, y), Antylopa.ANTYLOPA_SILA, wiek);
        else if (Objects.equals(nazwa, "Czlowiek")) {
            nowy = new Czlowiek(new Wspolrzedne(x, y), Czlowiek.CZLOWIEK_SILA, wiek);
            swiat.setZycieCzlowieka(true);
            ruchCzlowieka.setVisible(true);
        } else if (Objects.equals(nazwa, "Lis"))
            nowy = new Lis(new Wspolrzedne(x, y), Lis.LIS_SILA, wiek);
        else if (Objects.equals(nazwa, "Owca"))
            nowy = new Owca(new Wspolrzedne(x, y), Owca.OWCA_SILA, wiek);
        else if (Objects.equals(nazwa, "Wilk"))
            nowy = new Wilk(new Wspolrzedne(x, y), Wilk.WILK_SILA, wiek);
        else if (Objects.equals(nazwa, "Zolw"))
            nowy = new Zolw(new Wspolrzedne(x, y), Zolw.ZOLW_SILA, wiek);
        if(nowy != null)
        {
            if(sila != INIT_ORGANIZM)
                nowy.setSila(sila);
            swiat.dodajOrganizm(nowy);
        }
    }

    private void wyswietlLegende() {
        JDialog dial = new JDialog(okno, "Legenda", true);
        dial.setLocationRelativeTo(okno);
        dial.setLayout(new FlowLayout());
        JTextArea box = new JTextArea();
        box.setBounds(0, 0, 250, 250);
        box.setEditable(false);
        box.setText("""
                Kolory organizmów:
                - kremowy: Barszcz Sosnowskiego
                - czerwony: Guarana
                - żółty: Mlecz
                - zielony: Trawa
                - magenta: Wilcze jagody
                - brązowy: Antylopa
                - czarny: Człowiek
                - pomarańczowy: Lis
                - jasnoszary: Owca
                - ciemnoszary: Wilk
                - ciemnozielony: Zolw""");
        dial.add(box);
        JButton b = new JButton("OK");
        b.addActionListener(g -> dial.dispose());
        dial.add(b);
        dial.setSize(300, 300);
        dial.setVisible(true);
        okno.requestFocus();
    }
    private void wypelnijPola(int x, int y)
    {
        pola = new JButton[y][x];
        for (int i = 0; i < y; i++)
            for (int j = 0; j < x; j++) {
                pola[i][j] = new JButton();
                pola[i][j].setBounds(10 + 15 * j, 10 + 15 * i, 15, 15);
                int finalI = i;
                int finalJ = j;
                pola[i][j].addActionListener(f -> aktywujPole(finalI, finalJ));
                okno.add(pola[i][j]);
            }
    }

    private void aktywujPole(int y, int x) {
        int nX = (pola[y][x].getBounds().x - 10) / 15;
        int nY = (pola[y][x].getBounds().y - 10) / 15;
        if (swiat.getZajete(new Wspolrzedne(nX, nY)) == null) {
            JDialog dial = new JDialog(okno, "Wybór organizmu", true);
            dial.setLocationRelativeTo(okno);
            dial.setLayout(new FlowLayout());
            dial.add(new JLabel("Wybierz organizm z listy:"));
            String[] organizmy = {
                    "Barszcz Sosnowskiego",
                    "Guarana",
                    "Mlecz",
                    "Trawa",
                    "Wilcze jagody",
                    "Antylopa",
                    "Lis",
                    "Owca",
                    "Wilk",
                    "Zolw"
            };
            JComboBox<String> combo = new JComboBox<>(organizmy);
            combo.setBounds(50, 50, 90, 20);
            dial.add(combo);
            JButton bx = new JButton("Zatwierdź");
            bx.addActionListener(g -> {
                dial.dispose();
                dodajOrganizm(combo.getItemAt(combo.getSelectedIndex()), nX, nY, INIT_ORGANIZM, (short) 0);
            });
            dial.add(bx);
            dial.setSize(300, 300);
            dial.setVisible(true);
            rysowanie();
        }
        okno.requestFocus();
    }

    private void wyswietlDziennikTejTury() {
        Vector<String> zdarzenia = swiat.getZdarzenia();
        StringBuilder log = new StringBuilder();
        if (zdarzenia.size() > 0) {
            for (int i = 0; i <= zdarzenia.size(); i++) {
                if (i == 0)
                    log.append(String.format("W turze %d:\n", swiat.getTura()));
                else
                    log.append(zdarzenia.get(i - 1)).append("\n");
            }
        } else {
            log.append("Brak wydarzeń w poprzedniej turze.");
        }

        dziennik.setText(log.toString());
    }

    private void zapis()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Plik zapisu (.sav)", "sav");
        JFileChooser wybor = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        wybor.setDialogTitle("Wybierz miejsce zapisu i nazwę pliku");
        wybor.setFileFilter(filter);
        int dial = wybor.showSaveDialog(null);
        if(dial == JFileChooser.APPROVE_OPTION){
            try{
                String separator = System.getProperty("line.separator"), bufor;
                String nazwa = wybor.getSelectedFile().getPath();
                if(!nazwa.endsWith(".sav"))
                    nazwa += ".sav";
                File plik = new File(nazwa);
                BufferedWriter wyjscie = new BufferedWriter(new FileWriter(plik));
                bufor = String.format("%d %d %d %d", swiat.getSzerokosc(), swiat.getWysokosc(), swiat.getTura(), swiat.getKiedyUmiejetnosc());
                wyjscie.write(bufor);
                Vector<Organizm> organizmy = swiat.getOrganizmy();
                for(int i = 0; i < organizmy.size(); i++)
                {
                    bufor = String.format("%s;%d;%d;%d;%d", organizmy.get(i).nazwa(), organizmy.get(i).getPolozenie().x, organizmy.get(i).getPolozenie().y, organizmy.get(i).getSila(), organizmy.get(i).getWiek());
                    wyjscie.write(separator + bufor);
                }
                wyjscie.close();
            } catch (Exception ex) {ex.printStackTrace();}
        }
        okno.requestFocus();
    }
    private void odczyt()
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Plik zapisu (.sav)", "sav");
        JFileChooser wybor = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        wybor.setDialogTitle("Wybierz plik");
        wybor.setFileFilter(filter);
        int dial = wybor.showOpenDialog(null);
        if(dial == JFileChooser.APPROVE_OPTION){
            try{
                File plik = new File(wybor.getSelectedFile().getPath());
                BufferedReader wejscie = new BufferedReader(new FileReader(plik));
                String linia = wejscie.readLine();
                String[] config = linia.split(" ");
                for (int i = 0; i < swiat.getWysokosc(); i++)
                    for (int j = 0; j < swiat.getSzerokosc(); j++)
                        okno.remove(pola[i][j]);
                swiat.setSzerokosc((short) Integer.parseInt(config[0]));
                swiat.setWysokosc((short) Integer.parseInt(config[1]));
                swiat.setTura((short) Integer.parseInt(config[2]));
                swiat.setKiedyUmiejetnosc((short) Integer.parseInt(config[3]));

                swiat.wyczyscZdarzenia();
                swiat.usunWszystkie();
                swiat.setZycieCzlowieka(false);
                ruchCzlowieka.setVisible(false);

                wypelnijPola(swiat.getSzerokosc(), swiat.getWysokosc());

                while (wejscie.ready()) {
                    linia = wejscie.readLine();
                    String[] elem = linia.split(";");
                    if (elem.length != 5) {
                        break;
                    }
                    int x = Integer.parseInt(elem[1]), y = Integer.parseInt(elem[2]);
                    short sila = (short) Integer.parseInt(elem[3]), wiek = (short) Integer.parseInt(elem[4]);
                    dodajOrganizm(elem[0], x, y, sila, wiek);
                }
                wejscie.close();
                okno.repaint();
                rysowanie();
            } catch (Exception ex) {ex.printStackTrace();}
        }
        okno.requestFocus();
    }

    private void zakonczSymulacje()
    {
        okno.dispose();
    }
}
