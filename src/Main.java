import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Procesando reportes...");

            // Cargar Catálogos
            Map<String, Double> preciosProds = new HashMap<>();
            Map<String, String> nombresProds = new HashMap<>();
            Map<String, Integer> cantidadesVendidas = new HashMap<>();
            cargarProductos(preciosProds, nombresProds, cantidadesVendidas);

            Map<String, String> infoVendedores = cargarVendedores();
            Map<String, Double> recaudadoVendedor = new HashMap<>();

            // Procesar archivos de ventas (vendedor_*.csv)
            File carpeta = new File(".");
            File[] archivosVentas = carpeta.listFiles((dir, name) -> name.startsWith("vendedor_") && name.endsWith(".csv"));

            if (archivosVentas != null) {
                for (File f : archivosVentas) {
                    procesarArchivo(f, preciosProds, recaudadoVendedor, cantidadesVendidas);
                }
            }

            // Generar Reportes
            generarReporteVendedores(infoVendedores, recaudadoVendedor);
            generarReporteProductos(nombresProds, preciosProds, cantidadesVendidas);

            System.out.println("✅ Finalización exitosa: Reportes generados.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    private static void cargarProductos(Map<String, Double> precios, Map<String, String> nombres, Map<String, Integer> ventas) throws IOException {
        Files.lines(Paths.get("productos.csv")).forEach(linea -> {
            String[] d = linea.split(";");
            precios.put(d[0], Double.parseDouble(d[2].replace(",", ".")));
            nombres.put(d[0], d[1]);
            ventas.put(d[0], 0);
        });
    }

    private static Map<String, String> cargarVendedores() throws IOException {
        Map<String, String> vends = new HashMap<>();
        Files.lines(Paths.get("vendedores.csv")).forEach(linea -> {
            String[] d = linea.split(";");
            vends.put(d[1], d[2] + " " + d[3]);
        });
        return vends;
    }

    private static void procesarArchivo(File f, Map<String, Double> precios, Map<String, Double> recaudado, Map<String, Integer> ventasTotal) throws IOException {
        List<String> lineas = Files.readAllLines(f.toPath());
        String idVend = lineas.get(0).split(";")[1];
        double total = 0;

        for (int i = 1; i < lineas.size(); i++) {
            String[] d = lineas.get(i).split(";");
            String idProd = d[0];
            int cant = Integer.parseInt(d[1]);
            total += precios.getOrDefault(idProd, 0.0) * cant;
            ventasTotal.put(idProd, ventasTotal.getOrDefault(idProd, 0) + cant);
        }
        recaudado.put(idVend, recaudado.getOrDefault(idVend, 0.0) + total);
    }

    private static void generarReporteVendedores(Map<String, String> nombres, Map<String, Double> totales) throws IOException {
        List<Map.Entry<String, Double>> lista = new ArrayList<>(totales.entrySet());
        lista.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        try (PrintWriter pw = new PrintWriter("reporte_vendedores.csv")) {
            for (Map.Entry<String, Double> e : lista) {
                pw.println(nombres.get(e.getKey()) + ";" + String.format("%.2f", e.getValue()));
            }
        }
    }

    private static void generarReporteProductos(Map<String, String> nombres, Map<String, Double> precios, Map<String, Integer> ventas) throws IOException {
        List<String> idsOrdenados = ventas.keySet().stream()
                .sorted((a, b) -> ventas.get(b).compareTo(ventas.get(a)))
                .collect(Collectors.toList());

        try (PrintWriter pw = new PrintWriter("reporte_productos.csv")) {
            for (String id : idsOrdenados) {
                pw.println(nombres.get(id) + ";" + String.format("%.2f", precios.get(id)));
            }
        }
    }
}
