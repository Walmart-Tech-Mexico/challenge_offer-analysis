import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Reto para el desarrollador:
        // 1. Implementar la lógica para leer datos de ofertas desde un archivo (CSV, JSON, etc.).
        // 2. Permitir al usuario especificar criterios de clasificación (por ejemplo, diferentes umbrales de margen de ganancia).
        // 3. Agregar manejo de excepciones para datos de entrada incorrectos o archivos no encontrados.

        System.out.println("Reto: Análisis de Rendimiento de Ofertas");

        // Datos de ofertas (inicialmente hardcodeados, ¡debe leerse de un archivo!)
//        Oferta[] ofertas = new Oferta[] {
//            new Oferta("ProductoA", "Electrónicos", 100, 120),
//            new Oferta("ProductoB", "Ropa", 50, 75),
//            new Oferta("ProductoC", "Electrónicos", 200, 250),
//            new Oferta("ProductoD", "Hogar", 80, 100)
//        };

        // Aquí deberías leer las ofertas desde un archivo
        Oferta[] ofertas = leerOfertasDesdeArchivo("./ofertas.csv");

        // Analizar y clasificar ofertas
        for (Oferta oferta : ofertas) {
            double margenGanancia = oferta.calcularMargenGanancia();
            String clasificacion = oferta.clasificarOferta(25,10);

            System.out.println("Oferta: " + oferta.getNombre() +
                               ", Categoría: " + oferta.getCategoria() +
                               ", Margen de Ganancia: " + margenGanancia + "%" +
                               ", Clasificación: " + clasificacion);
        }
    }

    // Método para leer ofertas desde un archivo (¡implementar!)
    public static Oferta[] leerOfertasDesdeArchivo(String rutaArchivo) {
    //     // TODO: Implementar la lógica para leer el archivo y crear objetos Oferta
        List<Oferta> ofertas = new ArrayList<>();
        String separator=",";
        int errors=0;
        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            while (br.ready()) {
                String[] tokens= br.readLine().split(separator);
                if(tokens.length!=4){
                    System.out.println("Error al parsear oferta" + Arrays.toString(tokens));
                    errors++;
                    continue;
                }
                ofertas.add(new Oferta(tokens[0].trim(), tokens[1].trim(), Integer.parseInt(tokens[2].trim()), Integer.parseInt(tokens[3].trim())));
            }
        } catch (Exception e) {
            System.out.println("Error al abrir el archivo");
            e.printStackTrace();
        }
        System.out.println(String.format("Se han harcodeado %s ofertas exitosamente y se encontro error en %s ofertas",ofertas.size(), errors));
        return ofertas.toArray(new Oferta[ofertas.size()]);
    }
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

    public String clasificarOferta(int umbralMargenAlta,int umbralMargenMedia) {
        double margen = calcularMargenGanancia();
        // TODO: Permitir que los criterios de clasificación sean configurables
        if (margen > umbralMargenAlta) {
            return "Alta Efectividad";
        } else if (margen > umbralMargenMedia) {
            return "Efectividad Media";
        } else {
            return "Baja Efectividad";
        }
    }
}