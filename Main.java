public class Main {
    public static void main(String[] args) {
        // Reto para el desarrollador:
        // 1. Implementar la lógica para leer datos de ofertas desde un archivo (CSV, JSON, etc.).
        // 2. Permitir al usuario especificar criterios de clasificación (por ejemplo, diferentes umbrales de margen de ganancia).
        // 3. Agregar manejo de excepciones para datos de entrada incorrectos o archivos no encontrados.

        System.out.println("Reto: Análisis de Rendimiento de Ofertas");

        // Datos de ofertas (inicialmente hardcodeados, ¡debe leerse de un archivo!)
        Oferta[] ofertas = new Oferta[] {
            new Oferta("ProductoA", "Electrónicos", 100, 120),
            new Oferta("ProductoB", "Ropa", 50, 75),
            new Oferta("ProductoC", "Electrónicos", 200, 250),
            new Oferta("ProductoD", "Hogar", 80, 100)
        };

        // Aquí deberías leer las ofertas desde un archivo
        // Oferta[] ofertas = leerOfertasDesdeArchivo("ruta/al/archivo.csv");

        // Analizar y clasificar ofertas
        for (Oferta oferta : ofertas) {
            double margenGanancia = oferta.calcularMargenGanancia();
            String clasificacion = oferta.clasificarOferta();

            System.out.println("Oferta: " + oferta.getNombre() +
                               ", Categoría: " + oferta.getCategoria() +
                               ", Margen de Ganancia: " + margenGanancia + "%" +
                               ", Clasificación: " + clasificacion);
        }
    }

    // Método para leer ofertas desde un archivo (¡implementar!)
    // public static Oferta[] leerOfertasDesdeArchivo(String rutaArchivo) {
    //     // TODO: Implementar la lógica para leer el archivo y crear objetos Oferta
    //     return null;
    // }
}

class Oferta {
    private String nombre;
    private String categoria;
    private double costo;
    private double precio;

    public Oferta(String nombre, String categoria, double costo, double precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.costo = costo;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double calcularMargenGanancia() {
        return ((precio - costo) / costo) * 100;
    }

    public String clasificarOferta() {
        double margen = calcularMargenGanancia();
        // TODO: Permitir que los criterios de clasificación sean configurables
        if (margen > 25) {
            return "Alta Efectividad";
        } else if (margen > 10) {
            return "Efectividad Media";
        } else {
            return "Baja Efectividad";
        }
    }
}