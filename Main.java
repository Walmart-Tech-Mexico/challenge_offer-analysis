import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Reto para el desarrollador:
        // -- 1. Implementar la lógica para leer datos de ofertas desde un archivo (CSV, JSON, etc.).
        // 2. Permitir al usuario especificar criterios de clasificación (por ejemplo, diferentes umbrales de margen de ganancia).
        // 3. Agregar manejo de excepciones para datos de entrada incorrectos o archivos no encontrados.

        System.out.println("Reto: Análisis de Rendimiento de Ofertas");

        // Datos de ofertas (inicialmente hardcodeados, ¡debe leerse de un archivo!)
        // Oferta[] ofertas = new Oferta[] {
        //     new Oferta("ProductoA", "Electrónicos", 100, 120),
        //     new Oferta("ProductoB", "Ropa", 50, 75),
        //     new Oferta("ProductoC", "Electrónicos", 200, 250),
        //     new Oferta("ProductoD", "Hogar", 80, 100)
        // };

        Oferta[] ofertas = leerOfertasDesdeArchivo("data.csv");
        List<Criterio> criterios = new ArrayList<>();
        criterios.add(new Criterio(50, "Alta Efectividad", Operator.GREATER_THAN_OR_EQUAL));
        criterios.add(new Criterio(25, "Efectividad Media"));
        criterios.add(new Criterio(10, "Efectividad Aceptable"));
        criterios.add(new Criterio(5, "Baja Efectividad"));

        // Analizar y clasificar ofertas
        for (Oferta oferta : ofertas) {
            double margenGanancia = oferta.calcularMargenGanancia();
            String clasificacion = oferta.clasificarOferta(criterios);

            System.out.println("Oferta: " + oferta.getNombre() +
                               ", Categoría: " + oferta.getCategoria() +
                               ", Margen de Ganancia: " + margenGanancia + "%" +
                               ", Clasificación: " + clasificacion);
        }
    }

    public static Oferta[] leerOfertasDesdeArchivo(String rutaArchivo) {
        List<Oferta> ofertas = new ArrayList<>();
        
        if (!rutaArchivo.contains(".csv")) {
            System.out.println("Formato de archivo no soportado: " + rutaArchivo);
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String line;
            System.out.println("Leyendo ofertas desde archivo...");

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Oferta oferta = OfertaMapper.fromCsvToOferta(data);

                if (oferta == null) {
                    continue;
                }

                ofertas.add(oferta);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
        
        if (ofertas.isEmpty()) {
            System.out.println("No se encontraron ofertas válidas en el archivo");
            return null;
        }
        return ofertas.toArray(new Oferta[0]);
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

    public String clasificarOferta(List<Criterio> criterios) {
        double margen = calcularMargenGanancia();

        if (criterios.isEmpty()) {
            return criteriosPorDefecto(margen);
        }

        for (Criterio criterio : criterios) {
            if (criterio.cumpleCriterio(margen)) {
                return criterio.getClasificacion();
            }
        }

        return "Sin clasificación, apartir de los criterios dados";
    }

    public String clasificarOferta() {
        return clasificarOferta(new ArrayList<>());
    }

    private String criteriosPorDefecto(double margen) {
        if (margen > 25) {
            return "Alta Efectividad";
        } else if (margen > 10) {
            return "Efectividad Media";
        } else {
            return "Baja Efectividad";
        }
    }
}

class OfertaMapper {
    static Oferta fromCsvToOferta(String[] data) {
        String nombre = data[0];
        String categoria = data[1];
        double costo = 0;
        double precio = 0;
        
        if (data.length < 4) {
            System.out.println("Oferta no válida: " + data);
            return null;
        }

        try {
            costo = Double.parseDouble(data[2]);
            precio = Double.parseDouble(data[3]);
        } catch (NumberFormatException e) {
            System.out.println("Precio o costo no válido: " + data);
            return null;
        }
        
        return new Oferta(nombre, categoria, costo, precio);
    }
}

enum Operator {
    EQUAL,
    GREATER_THAN,
    LESS_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN_OR_EQUAL
}

class Criterio {
    private double margen;
    private String clasificacion;
    private Operator operator;

    public Criterio(double margen, String clasificacion, Operator operator) {
        this.margen = margen;
        this.clasificacion = clasificacion;
        this.operator = operator;
    }

    public Criterio(double margen, String clasificacion) {
        this.margen = margen;
        this.clasificacion = clasificacion;
        operator = Operator.GREATER_THAN;
    }

    public double getMargen() {
        return margen;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public boolean cumpleCriterio(double margenToMatch) {
        switch (operator) {
            case EQUAL:
                return margen == margenToMatch;
            case GREATER_THAN:
                return margenToMatch > margen;
            case LESS_THAN:
                return margenToMatch < margen;
            case GREATER_THAN_OR_EQUAL:
                return margenToMatch >= margen;
            case LESS_THAN_OR_EQUAL:
                return margenToMatch <= margen;
            default:
                return false;
        }
    }
}