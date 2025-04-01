import java.util.*;

// Clase principal que ejecuta el motor de inferencia
public class Main {
    public static void main(String[] args) {
        Resolucion kb = new Resolucion(); // Creamos una base de conocimiento

        // Agregamos cláusulas (hechos y reglas) en el orden correcto usando LinkedHashSet
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Americano", "x"),new Literal("-Arma", "y"), new Literal("-Vende", "x", "y", "z"), new Literal("-Hostil", "z"), new Literal("Criminal", "x")))));
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Enemigo", "Nono", "America")))));
        

        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Tiene", "Nono", "Mi")))));
        

        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Misil", "Mi")))));
        

        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Misil", "x"), new Literal("-Tiene", "Nono", "x"), new Literal("Vende", "West", "x", "Nono")))));
        
        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("Americano", "West")))));
        

        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Enemigo", "x", "America"), new Literal("Hostil", "x")))));

        kb.agregarClausula(new Clausula(new LinkedHashSet<>(List.of(new Literal("-Misil", "m"), new Literal("Arma", "m")))));

        // Mostrar base de conocimiento actual
        System.out.println("Base de conocimiento inicial:");
        kb.mostrarClausulas();

        System.out.println("--------------------------");

        // Declaramos la conjetura a verificar
        List<Literal> conjetura = List.of(new Literal("Criminal", "West"));
        kb.negarConjetura(conjetura); // Se niega la conjetura


        // Mostrar la base de conocimiento después de la sustitución
        System.out.println("\nBase de conocimiento después de la sustitución:");
        kb.mostrarClausulas();

        System.out.println("--------------------------");

        // Inicia el proceso de resolución
        System.out.println("\nResolviendo...\n");
        kb.resolver();
    }
}