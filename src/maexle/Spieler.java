package maexle;

public class Spieler {
    private int leben;
    private String name;
    private boolean ausgeschieden;
    private int wurf;

    public Spieler(String name){
        this.name = name;
        leben = 3;
        ausgeschieden = false;
        wurf = 0;
    }

    public int getWurf() {
        return wurf;
    }

    public void setWurf(int wurf) {
        this.wurf = wurf;
    }

    public void setLeben(int leben){
        this.leben = leben;
    }

    public int getLeben(){
        return leben;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAusgeschieden() {
        return ausgeschieden;
    }

    public void setAusgeschieden(boolean ausgeschieden) {
        this.ausgeschieden = ausgeschieden;
    }
}
