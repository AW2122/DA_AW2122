package EjerciciosNC;

public class Titulo {
    protected String texto;
    protected int coordX;
    protected int coordY;

    public Titulo() {

    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
    public String getText() {
        return texto;
    }
    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }
    public int getCoordX() {
        return coordX;
    }
    public void setCoordY() {
        this.coordY = coordY;
    }
    public int getCoordY() {
        return coordY;
    }
    public void Mostrar() {
        System.out.println(texto);
    }

    public static void main(String[] args) {
        Titulo t1 = new Titulo();

    }
}
