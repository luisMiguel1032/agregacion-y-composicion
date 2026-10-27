import java.util.Arrays;
import java.util.Comparator;

public class Main {

    // b) la laptop con mas aplicaciones (la primera que lo cumpla)
    static Laptop conMasApps(Laptop[] v) {
        Laptop mayor = v[0];
        for (int i = 1; i < v.length; i++) {
            if (v[i].getCantApps() > mayor.getCantApps()) {
                mayor = v[i];
            }
        }
        return mayor;
    }

    // c) agregar una aplicacion a la laptop de modelo X
    static void agregarAppAModelo(Laptop[] v, String modelo, Aplicacion app) {
        for (Laptop l : v) {
            if (l.getModelo().equalsIgnoreCase(modelo)) {
                if (!l.agregarApp(app)) {
                    System.out.println("La laptop " + modelo + " ya tiene 50 apps.");
                }
            }
        }
    }

    // d) eliminar todas las apps de las laptops con pantalla de ancho Y
    static void eliminarAppsPorAncho(Laptop[] v, int ancho) {
        for (Laptop l : v) {
            if (l.getPantalla().getAncho() == ancho) {
                l.eliminarApps();
            }
        }
    }

    // e) mostrar todas las laptops que tienen el maximo de apps (cubre empates)
    static void mostrarMasApps(Laptop[] v) {
        int max = conMasApps(v).getCantApps();
        for (Laptop l : v) {
            if (l.getCantApps() == max) {
                l.mostrar();
            }
        }
    }

    // f) ordenar las apps de cada laptop
    static void ordenarAppsDeTodas(Laptop[] v) {
        for (Laptop l : v) {
            l.ordenarApps();
        }
    }

    public static void main(String[] args) {
        // a) instanciar N laptops en un vector
        int N = 4;
        Laptop[] laptops = new Laptop[N];
        laptops[0] = new Laptop("HP Pavilion", "LED", 1920, 1080);
        laptops[1] = new Laptop("Dell XPS", "OLED", 2560, 1600);
        laptops[2] = new Laptop("Lenovo X1", "IPS", 1366, 768);
        laptops[3] = new Laptop("Asus Zenbook", "OLED", 1920, 1080);

        laptops[0].agregarApp(new Aplicacion("Chrome", 500));
        laptops[0].agregarApp(new Aplicacion("Word", 800));
        laptops[0].agregarApp(new Aplicacion("Zoom", 300));
        laptops[0].agregarApp(new Aplicacion("Excel", 800));

        laptops[1].agregarApp(new Aplicacion("Photoshop", 2500));
        laptops[1].agregarApp(new Aplicacion("Chrome", 500));

        laptops[2].agregarApp(new Aplicacion("Teams", 600));
        laptops[2].agregarApp(new Aplicacion("Word", 800));
        laptops[2].agregarApp(new Aplicacion("Excel", 800));
        laptops[2].agregarApp(new Aplicacion("Spotify", 300));

        laptops[3].agregarApp(new Aplicacion("Chrome", 500));

        System.out.println("=== a) Laptops creadas ===");
        for (Laptop l : laptops) l.mostrar();

        System.out.println("\n=== b) Laptop con mas aplicaciones ===");
        conMasApps(laptops).mostrar();

        System.out.println("\n=== c) Agregar VLC a la laptop modelo 'Dell XPS' ===");
        agregarAppAModelo(laptops, "Dell XPS", new Aplicacion("VLC", 150));
        laptops[1].mostrar();

        System.out.println("\n=== d) Eliminar apps de laptops con ancho 1920 ===");
        eliminarAppsPorAncho(laptops, 1920);
        for (Laptop l : laptops) l.mostrar();

        System.out.println("\n=== e) Laptop(s) con mas aplicaciones ===");
        mostrarMasApps(laptops);

        System.out.println("\n=== f) Apps ordenadas (peso desc, empate por nombre) ===");
        ordenarAppsDeTodas(laptops);
        for (Laptop l : laptops) l.mostrar();
    }
}

class Pantalla {
    private String tipo;
    private int ancho;
    private int alto;

    public Pantalla(String tipo, int ancho, int alto) {
        this.tipo = tipo;
        this.ancho = ancho;
        this.alto = alto;
    }

    public String getTipo() { return tipo; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }

    @Override
    public String toString() {
        return tipo + " " + ancho + "x" + alto;
    }
}

class Aplicacion {
    private String nombre;
    private int tamanio;

    public Aplicacion(String nombre, int tamanio) {
        this.nombre = nombre;
        this.tamanio = tamanio;
    }

    public String getNombre() { return nombre; }
    public int getTamanio() { return tamanio; }

    @Override
    public String toString() {
        return nombre + " (" + tamanio + " MB)";
    }
}

class Laptop {
    private String modelo;
    private int cantApps;
    private Aplicacion[] apps;
    private Pantalla pantalla;   // composicion: la Laptop crea su Pantalla

    public Laptop(String modelo, String tipo, int ancho, int alto) {
        this.modelo = modelo;
        this.cantApps = 0;
        this.apps = new Aplicacion[50];
        this.pantalla = new Pantalla(tipo, ancho, alto);
    }

    public String getModelo() { return modelo; }
    public int getCantApps() { return cantApps; }
    public Pantalla getPantalla() { return pantalla; }

    // agregacion: la Aplicacion existe fuera de la Laptop
    public boolean agregarApp(Aplicacion app) {
        if (cantApps >= apps.length) {
            return false;
        }
        apps[cantApps++] = app;
        return true;
    }

    public void eliminarApps() {
        for (int i = 0; i < cantApps; i++) {
            apps[i] = null;
        }
        cantApps = 0;
    }

    // peso (tamanio) decreciente; si empatan, por nombre
    public void ordenarApps() {
        Arrays.sort(apps, 0, cantApps,
                Comparator.comparingInt(Aplicacion::getTamanio).reversed()
                          .thenComparing(Aplicacion::getNombre));
    }

    public void mostrar() {
        System.out.println("Laptop " + modelo + " | Pantalla: " + pantalla
                + " | Apps: " + cantApps);
        for (int i = 0; i < cantApps; i++) {
            System.out.println("   - " + apps[i]);
        }
    }
}
