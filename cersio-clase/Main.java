import java.util.*;

// Clase principal que ejecuta el motor de inferencia
public class Main {
    public static void main(String[] args) {
        Resolucion kb = new Resolucion(); // Creamos una base de conocimiento

        // Agregamos cláusulas (hechos y reglas)
        // Hecho: César es un gobernante
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Misil", "Mi")))));
        
        // Regla: Si alguien es Romano, entonces debe ser Leal a César o debe Odiar a César
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Misil", "x"), new Literal("-Tiene", "Nono", "x"), new Literal("Vende", "West", "x", "Nono")))));
        
        // Regla: Un Hombre que no sea un Gobernante y que intente asesinar a alguien no es Leal a esa persona
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("America", "West")))));
        
        // Hecho: Marco intenta asesinar a César
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Enemigo", "x", "America"), new Literal("Hostil", "x")))));

        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Misil", "m"), new Literal("Arma", "m")))));
        // Declaramos la conjetura a verificar
        List<Literal> conjetura = List.of(new Literal("Odia", "Marco", "Cesar"));
        kb.negarConjetura(conjetura); // Se niega la conjetura

        // Mostrar base de conocimiento inicial
        System.out.println("Base de conocimiento inicial:");
        System.out.println("--------------------------");
        kb.mostrarClausulas();

        // Sustitución de variables
        Map<String, String> variables = kb.unificarVariables("Marco", "Cesar");
        kb.sustituirVariables(variables);

        // Mostrar después de sustitución
        System.out.println("--------------------------");
        System.out.println("Después de sustituir variables:");
        kb.mostrarClausulas();
        System.out.println("--------------------------");

        // Inicia el proceso de resolución
        System.out.println("Resolviendo...");
        System.out.println();
        kb.resolver();
    }
}
