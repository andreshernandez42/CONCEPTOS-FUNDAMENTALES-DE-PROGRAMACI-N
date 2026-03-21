import java.io.PrintWriter;
import java.util.Random;

/**
 * Clase encargada de generar la data de prueba pseudoaleatoria.
 * Requisito: Primera entrega del proyecto.
 */
public class GenerateInfoFiles {

    private static final String[] NOMBRES = {"Carlos", "Ana", "Luis", "Maria", "Juan", "Elena"};
    private static final String[] APELLIDOS = {"Gomez", "Perez", "Rodriguez", "Martinez", "Soto"};
    private static final String[] PRODUCTOS = {"Laptop", "Mouse", "Teclado", "Monitor", "Webcam"};
    private static final String[] TIPOS_DOC = {"CC", "CE", "TI"};

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando generación de archivos de prueba...");
            
            // 5b. Crear archivo de productos (ejemplo: 5 productos)
            createProductsFile(PRODUCTOS.length);
            
            // 5c. Crear archivo de información de vendedores (ejemplo: 3 vendedores)
            createSalesManInfoFile(3);

            System.out.println("✅ Finalización exitosa: Archivos creados en la carpeta del proyecto.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    // 5b. Crea archivo de información de productos
    public static void createProductsFile(int productsCount) throws Exception {
        try (PrintWriter writer = new PrintWriter("productos.csv", "UTF-8")) {
            for (int i = 0; i < productsCount; i++) {
                double precio = 50000 + (new Random().nextDouble() * 1000000);
                writer.println((i + 1) + ";" + PRODUCTOS[i] + ";" + String.format("%.2f", precio));
            }
        }
    }

    // 5c. Crea archivo de información de vendedores
    public static void createSalesManInfoFile(int salesmanCount) throws Exception {
        Random rand = new Random();
        try (PrintWriter writer = new PrintWriter("vendedores.csv", "UTF-8")) {
            for (int i = 0; i < salesmanCount; i++) {
                long id = 1000000 + rand.nextInt(9000000);
                String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[rand.nextInt(APELLIDOS.length)];
                String tipoDoc = TIPOS_DOC[rand.nextInt(TIPOS_DOC.length)];
                
                writer.println(tipoDoc + ";" + id + ";" + nombre + ";" + apellido);
                
                // 5a. Crear el archivo de ventas individual para este vendedor
                createSalesMenFile(rand.nextInt(5) + 2, nombre, id);
            }
        }
    }

    // 5a. Crea un archivo de ventas pseudoaleatorio para un vendedor específico
    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws Exception {
        Random rand = new Random();
        String fileName = "vendedor_" + id + ".csv";
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            // Formato: TipoDoc;NumDoc en la primera línea
            writer.println(TIPOS_DOC[rand.nextInt(TIPOS_DOC.length)] + ";" + id);
            for (int i = 0; i < randomSalesCount; i++) {
                // Formato: IDProducto;Cantidad;
                writer.println((rand.nextInt(PRODUCTOS.length) + 1) + ";" + (rand.nextInt(10) + 1) + ";");
            }
        }
    }
}