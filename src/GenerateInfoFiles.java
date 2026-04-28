import java.io.PrintWriter;
import java.util.Random;

/**
 * Clase encargada de generar la data de prueba pseudoaleatoria.
 */
public class GenerateInfoFiles {

    private static final String[] NOMBRES = {"Carlos", "Ana", "Luis", "Maria", "Juan", "Elena"};
    private static final String[] APELLIDOS = {"Gomez", "Perez", "Rodriguez", "Martinez", "Soto"};
    private static final String[] PRODUCTOS = {"Laptop", "Mouse", "Teclado", "Monitor", "Webcam"};
    private static final String[] TIPOS_DOC = {"CC", "CE", "TI"};

    public static void main(String[] args) {
        try {
            System.out.println("Iniciando generación de archivos de prueba...");

            createProductsFile(PRODUCTOS.length);
            createSalesManInfoFile(3);

            // 🔥 GENERA EL ARCHIVO OBLIGATORIO
            createConclusionFile();

            System.out.println("✅ Finalización exitosa: Archivos creados en la carpeta del proyecto.");
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
        }
    }

    public static void createProductsFile(int productsCount) throws Exception {
        try (PrintWriter writer = new PrintWriter("productos.csv", "UTF-8")) {
            for (int i = 0; i < productsCount; i++) {
                double precio = 50000 + (new Random().nextDouble() * 1000000);
                writer.println((i + 1) + ";" + PRODUCTOS[i] + ";" + String.format("%.2f", precio));
            }
        }
    }

    public static void createSalesManInfoFile(int salesmanCount) throws Exception {
        Random rand = new Random();
        try (PrintWriter writer = new PrintWriter("vendedores.csv", "UTF-8")) {
            for (int i = 0; i < salesmanCount; i++) {
                long id = 1000000 + rand.nextInt(9000000);
                String nombre = NOMBRES[rand.nextInt(NOMBRES.length)];
                String apellido = APELLIDOS[rand.nextInt(APELLIDOS.length)];
                String tipoDoc = TIPOS_DOC[rand.nextInt(TIPOS_DOC.length)];

                writer.println(tipoDoc + ";" + id + ";" + nombre + ";" + apellido);
                createSalesMenFile(rand.nextInt(5) + 2, nombre, id);
            }
        }
    }

    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws Exception {
        Random rand = new Random();
        String fileName = "vendedor_" + id + ".csv";
        try (PrintWriter writer = new PrintWriter(fileName, "UTF-8")) {
            writer.println(TIPOS_DOC[rand.nextInt(TIPOS_DOC.length)] + ";" + id);
            for (int i = 0; i < randomSalesCount; i++) {
                writer.println((rand.nextInt(PRODUCTOS.length) + 1) + ";" + (rand.nextInt(10) + 1) + ";");
            }
        }
    }

    public static void createConclusionFile() throws Exception {
        try (PrintWriter writer = new PrintWriter("conclusion.txt", "UTF-8")) {

            writer.println("CONCLUSIÓN DEL PROYECTO");
            writer.println();

            writer.println("1. Lo aprendido:");
            writer.println("Durante el desarrollo del proyecto se adquirieron conocimientos fundamentales sobre el manejo de archivos en Java, incluyendo la lectura y escritura de archivos planos (.csv/.txt). Además, se fortalecieron habilidades en el uso de estructuras de datos como mapas (HashMap) para organizar y procesar información. También se comprendió cómo estructurar un programa en diferentes clases con responsabilidades específicas, separando la generación de datos del procesamiento de los mismos.");
            writer.println();

            writer.println("2. Aplicación en la vida profesional:");
            writer.println("Los conocimientos adquiridos pueden aplicarse en el desarrollo de sistemas empresariales, especialmente en áreas como análisis de datos, sistemas de ventas, inventarios y generación de reportes. Este tipo de lógica es útil para automatizar procesos, organizar información y apoyar la toma de decisiones en una empresa. Además, el manejo de archivos y procesamiento de datos es una habilidad clave en el desarrollo de software y en proyectos de ingeniería de sistemas.");
            writer.println();

            writer.println("3. Dificultades:");
            writer.println("Durante el desarrollo del proyecto se presentaron dificultades relacionadas con el manejo de múltiples archivos y la correcta lectura de los datos desde estos. También hubo retos al asegurar la coherencia entre los datos generados y los procesados, así como en el cumplimiento exacto de los requisitos del enunciado. Finalmente, se presentaron algunos errores en la ejecución y organización del código, los cuales fueron solucionados mediante pruebas y ajustes progresivos.");
        }
    }
}
