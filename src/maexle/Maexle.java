package maexle;

import java.util.Random;

public class Maexle {

    private final int numberOfPlayers;
    private Random wuerfelEins;
    private Random wuerfelZwei;
    private int currentValue;
    private Spieler[] spieler;
    private int newValue;
    private int countAusgeschieden;
    private Spieler höchsterWurf;
    private int wurf;

    public Maexle(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        wuerfelZwei = new Random();
        wuerfelEins = new Random();
        currentValue = newValue = wurf = 0;

        erstelleSpieler();
        printWinner();
    }

    // schreibt Gewinner in die Konsole
    private void printWinner(){
        System.out.println(play());
    }

    // zeigt alle Spieler an, die noch am Spiel teilnehmen + wie viel Leben sie noch haben
    private void zeigeSpieler(){
        for(Spieler p : spieler)
            if(!p.isAusgeschieden())
                if(p.getLeben() > 0)
                    System.out.println(p.getName() + "spielt mit und hat: " + p.getLeben() + " Leben übrig");
    }

    // erstellt die Spieler und speichert sie in einem Array
    private void erstelleSpieler(){
        spieler = new Spieler[numberOfPlayers];

        for(int i = 0; i < numberOfPlayers; i++)
            spieler[i] = new Spieler("Spieler " + (i+1));

        zeigeSpieler();
    }
    // generiert zwei Random zahlen und bildet den daraus entstehenden Maexle-Wurf
    private int wuerfeln(){
        int zahlEins = wuerfelEins.nextInt((6 - 1) + 1) + 1;
        int zahlZwei = wuerfelZwei.nextInt((6 - 1) + 1) + 1;

        if(zahlEins > zahlZwei)
            return zahlEins*10 + zahlZwei;
        return zahlZwei*10 + zahlEins;
    }

    // Spielablauf -> gibt am Ende Gewinner zurück
    public String play(){
        // falls nur ein Spieler vorhanden ist wird dieser als Gewinner zurückgegeben
        if(numberOfPlayers == 1)
            return spieler[0].getName() + " hat gewonnen";

        // sonst: wiederholt solange bis 1 Spieler übrig ist
        while(countAusgeschieden < numberOfPlayers -1){
            // count ist der Index des nachfolgenden Spielers
            int count = 0;
            for(Spieler p : spieler){
                count++;
                if(count == 10)
                    count = 0;
                // checkt, ob der aktuelle Spieler ausgeschieden ist oder nur noch ein Spieler Leben übrig hat
                if(!p.isAusgeschieden() && countAusgeschieden < numberOfPlayers - 1){
                    if(dice(currentValue, p.getName())){
                        currentValue = newValue;
                        p.setWurf(newValue);
                        if(newValue == 21){
                            // while-Schleife: sucht den nächsten Spieler, der noch mindestens ein Leben übrig hat und zieht ein Leben ab -> Vorwurf war Mäxle
                            while(spieler[count].getLeben() == 0){
                                if(count+1 >= numberOfPlayers)
                                    count = 0;
                                else
                                    count++;
                            }
                            spieler[count].setLeben(spieler[count].getLeben() -1);
                            resetCurrentValue();
                        }
                    }
                    // tritt ein, wenn der vorherige Wurf nicht überboten werden kann
                    else{
                        // der aktuelle Spieler verliert ein Leben und die vorher geworfenen Augenzahlen werden gelöscht
                        p.setLeben(p.getLeben() - 1);
                        for(Spieler sp : spieler)
                            sp.setWurf(0);
                        // falls ein Spieler auf 0 Leben fällt, erhöht sich die Anzahl an ausgeschiedenen Spielern und der Spieler ist ausgeschieden
                        if(p.getLeben() == 0){
                            countAusgeschieden++;
                            p.setAusgeschieden(true);
                            wurf = currentValue;
                        }
                        resetCurrentValue();
                    }
                    // zeigt den Spieler mit dem höchsten Wurf, sofern nicht bereits alle anderen Spieler verloren haben
                    if(countAusgeschieden < numberOfPlayers -1){
                        höchsterWurf = zeigeHöchstenWurf();
                        zeigeSpieler();
                        if(höchsterWurf != null)
                            System.out.println(höchsterWurf.getName() + " hat " + höchsterWurf.getWurf() + " gewürfelt und damit den höchsten Wurf");
                    }
                }
                checkAusgeschieden();
            }
        }

        // gibt den Gewinner mit seinem letzten Wurf zurück an das System.out.println() in der Methode printWinner()
        for(Spieler p : spieler){
            if(!p.isAusgeschieden())
                return p.getName() + " hat gewonnen und als letztes " + wurf + " gewürfelt.";
        }
        return "Fehler";
    }

    // prüft, ob der Wurf des aktuellen Spielers höher als der Wurf zuvor ist und gibt dies in die Konsole aus
    private boolean dice(int currentValue, String playerAtMove) {
        if (currentValue != 0)
            System.out.println("aktueller Wurf: " + currentValue);
        else
            System.out.println("Das Spiel oder die Runde beginnt gerade.");

        newValue = wuerfeln();
        System.out.println("neuer Wurf von " + playerAtMove + ": " + newValue);

        if (newValue == 21) {
            System.out.println("Mäxle!");
            return true;
        }
        else if (isPasch(newValue) && !isPasch(currentValue))
            return returnHigher(playerAtMove);
        else if (isPasch(currentValue) && !isPasch(newValue))
            return returnLower(playerAtMove);
        else {
            if (newValue > currentValue)
                return returnHigher(playerAtMove);
            else
                return returnLower(playerAtMove);
        }
    }

    // checkt wie viele Spieler ausgeschieden sind, passt den counter dafür an und setzt ausgeschieden = true, falls nötig
    private void checkAusgeschieden(){
        countAusgeschieden = 0;
        for(Spieler p : spieler){
            if(p.getLeben() <= 0)
            {
                p.setAusgeschieden(true);
                countAusgeschieden++;
            }

        }

    }

    // gibt den Spieler zurück, der aktuell den höchsten Wurf geworfen hat
    private Spieler zeigeHöchstenWurf(){
        int wurf = 0;
        if(currentValue != 0){
            for(Spieler p : spieler)
                if(isHigher(p.getWurf(), wurf))
                    wurf = p.getWurf();

            for(Spieler p : spieler)
                if(p.getWurf() == wurf)
                    return p;
        }
       return null;
    }

    // setzt die currentValue = 0
    private void resetCurrentValue(){
        currentValue = 0;
    }

    // return und Ausgabe, falls der Wurf höher ist
    private boolean returnHigher(String playerAtMove){
        if (currentValue != 0) {
            System.out.println("Der Wurf von " + playerAtMove + " war höher.");
        }
        return true;
    }

    // return und Ausgabe, falls der Wurf niedriger ist
    private boolean returnLower(String playerAtMove){
        if(currentValue != 0){
            System.out.println("Der Wurf von " + playerAtMove + " war kleiner oder gleich dem vorherigen Wurf.");
        }
        return false;
    }

    // prüft, ob der Wurf ein Pasch ist
    private boolean isPasch(int value){
        if(value == 11 || value == 22 || value == 33 || value == 44 || value == 55 || value == 66)
            return true;
        else
            return false;
    }

    // prüft, ob der Wurf höher ist oder nicht
    private boolean isHigher(int spielerWurf, int vorherigerWurf){
        if(newValue == 21){
            return true;
        }
        else if(isPasch(spielerWurf) && !isPasch(vorherigerWurf))
        {
            return true;
        }
        else if(isPasch(vorherigerWurf) && !isPasch(spielerWurf)){
            return false;
        }
        else{
            if(spielerWurf > vorherigerWurf){
                return true;
            }
            else
            {
                return false;
            }
        }
    }

}
