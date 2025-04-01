import java.util.*;

// Clase principal que ejecuta el motor de inferencia
public class Main {
    public static void main(String[] args) {
        Resolucion kb = new Resolucion(); // Creamos una base de conocimiento

        // Agregamos cláusulas (hechos y reglas)
        kb.agregarClausula(new Clausula(Set.of(new Literal("Hombre", "Marco"))));
        kb.agregarClausula(new Clausula(Set.of(new Literal("Pompeyano", "Marco"))));
        kb.agregarClausula(new Clausula(Set.of(new Literal("Romano", "a"), new Literal("-Pompeyano", "a"))));
        kb.agregarClausula(new Clausula(Set.of(new Literal("Gobernante", "Cesar"))));
        kb.agregarClausula(new Clausula(Set.of(new Literal("Leal", "a", "Cesar"), new Literal("Odia", "a", "Cesar"), new Literal("-Romano", "a"))));
        kb.agregarClausula(new Clausula(Set.of(new Literal("-Leal", "a", "b"), new Literal("-Hombre", "a"), new Literal("-IntentaAsesinar", "a", "b"), new Literal("-Gobernante", "b"))));
        kb.agregarClausula(new Clausula(Set.of(new Literal("IntentaAsesinar", "Marco", "Cesar"))));

                // Mostrar base de conocimiento actual
        System.out.println("Base de conocimiento inicial:");
        kb.mostrarClausulas();

        System.out.println("--------------------------");

        // Declaramos la conjetura a verificar
        List<Literal> conjetura = List.of(new Literal("Odia", "Marco", "Cesar"));
        kb.negarConjetura(conjetura); // Se niega la conjetura

        // Se sustituyen las variables por nombres reales
        Map<String, String> variables = kb.unificarVariables("Marco", "Cesar");
        kb.sustituirVariables(variables);

        // Mostrar la base de conocimiento después de la sustitución
        System.out.println("\nBase de conocimiento después de la sustitución:");
        kb.mostrarClausulas();

        System.out.println("--------------------------");





        // Inicia el proceso de resolución
        System.out.println("\nResolviendo...\n");
        kb.resolver();
    }
}