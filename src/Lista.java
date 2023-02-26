public class Lista {
    String[] palabras = {"Hola", "Huevo", "Multicast", "Buenas","Asegurado"};
    int p;

    public Lista(int l) {
        this.p = l;
    }


    public void setVel(int vel) {
        this.p = vel;
    }

    public String agafaVelocitat() {
        setVel((int)(Math.random()* palabras.length));
        return palabras[p];

    }

}
