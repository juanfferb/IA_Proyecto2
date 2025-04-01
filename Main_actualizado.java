import java.util.*;

// Clase principal que ejecuta el motor de inferencia
public class Main {
    public static void main(String[] args) {
        Resolucion kb = new Resolucion(); // Creamos una base de conocimiento

        // Agregamos cláusulas (hechos y reglas) en el orden correcto usando LinkedHashSet
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Hombre", "Marco")))));
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Pompeyano", "Marco")))));
        
        // Regla: Si alguien es Pompeyano, entonces es Romano
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Pompeyano", "a"), new Literal("Romano", "a")))));
        
        // Hecho: César es un gobernante
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Gobernante", "Cesar")))));
        
        // Regla: Si alguien es Romano, entonces debe ser Leal a César o debe Odiar a César
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Romano", "a"), new Literal("Leal", "a", "Cesar"), new Literal("Odia", "a", "Cesar")))));
        
        // Regla: Un Hombre que no sea un Gobernante y que intente asesinar a alguien no es Leal a esa persona
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Hombre", "a"), new Literal("-Gobernante", "b"), new Literal("-IntentaAsesinar", "a", "b"), new Literal("-Leal", "a", "b")))));
        
        // Hecho: Marco intenta asesinar a César
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("IntentaAsesinar", "Marco", "Cesar")))));

<<<<<<< HEAD:java-version/Main.java
        // Mostrar base de conocimiento actual
        System.out.println("Base de conocimiento inicial:");
        kb.mostrarClausulas();

        System.out.println("--------------------------");

        // Declaramos la conjetura a verificar: "Marco odia a César"
        List<Literal> conjetura = List.of(new Literal("Odia", "Marco", "Cesar"));
        kb.negarConjetura(conjetura); // Se niega la conjetura para intentar demostrarla por contradicción

        // Se sustituyen las variables por nombres reales en la base de conocimiento
        Map<String, String> variables = kb.unificarVariables("Marco", "Cesar");
        kb.sustituirVariables(variables);

        // Mostrar la base de conocimiento después de la sustitución
        System.out.println("\nBase de conocimiento después de la sustitución:");
=======
        // Declaramos la conjetura a verificar
        List<Literal> conjetura = List.of(new Literal("Odia", "a", "c"));
        kb.negarConjetura(conjetura); // Se niega la conjetura

        // Mostrar base de conocimiento inicial
        System.out.println("Base de conocimiento inicial:");
        System.out.println("--------------------------");
>>>>>>> 204ecb7 (nueva version):Main_actualizado.java
        kb.mostrarClausulas();

        // Sustitución de variables
        Map<String, String> variables = kb.unificarVariables("Marco", "Cesar");
        kb.sustituirVariables(variables);

        // Mostrar después de sustitución
        System.out.println("--------------------------");
        System.out.println("Después de sustituir variables:");
        kb.mostrarClausulas();
        System.out.println("--------------------------");

<<<<<<< HEAD:java-version/Main.java
        // Inicia el proceso de resolución para verificar la conjetura
        System.out.println("\nResolviendo...\n");
=======
        // Inicia el proceso de resolución
        System.out.println("Resolviendo...");
        System.out.println();
>>>>>>> 204ecb7 (nueva version):Main_actualizado.java
        kb.resolver();
    }
}
