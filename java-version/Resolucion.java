
// Resolucion.java
import java.util.*;

// Implementa el motor de resolución para inferencia lógica
public class Resolucion {
    public List<Clausula> clausulas = new ArrayList<>(); // Base de conocimiento
    public Clausula vacio = new Clausula(); // Clausula vacía

    // Añade una cláusula a la base
    public void agregarClausula(Clausula c) {
        clausulas.add(c);
    }

    // Muestra las cláusulas actuales
    public void mostrarClausulas() {
        for (Clausula c : clausulas) {
            System.out.println(c);
        }
    }

    // Nega una conjetura añadiendo sus literales negados como cláusulas
    public void negarConjetura(List<Literal> conjetura) {
        for (Literal l : conjetura) {
            Clausula negada = new Clausula();
            negada.literales.add(l.negado());
            clausulas.add(negada);
        }
    }

    // Asocia letras con nombres reales (ej. a->Marco)
    public Map<String, String> unificarVariables(String... args) {
        Map<String, String> mapa = new HashMap<>();
        String letras = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < args.length; i++) {
            mapa.put(String.valueOf(letras.charAt(i)), args[i]);
        }
        return mapa;
    }

    // Sustituye las variables por nombres reales en las cláusulas
    public void sustituirVariables(Map<String, String> variables) {
        List<Clausula> nuevas = new ArrayList<>();
        for (Clausula c : clausulas) {
            Set<Literal> nuevos = new HashSet<>();
            for (Literal l : c.literales) {
                String[] nuevosArgs = Arrays.stream(l.argumentos).map(arg -> variables.getOrDefault(arg, arg)).toArray(String[]::new);
                nuevos.add(new Literal(l.predicado, nuevosArgs));
            }
            nuevas.add(new Clausula(nuevos));
        }
        clausulas = nuevas;
    }

    // Aplica el algoritmo de resolución para derivar la contradicción
    public boolean resolver() {
        while (!clausulas.isEmpty()) {
            Clausula primera = clausulas.get(0); // Tomamos la primera cláusula
            Literal literalOpuesto = null;
            Clausula segunda = null;

            // Buscamos un literal opuesto en otra cláusula
            for (Literal l : primera.literales) {
                for (Clausula c : clausulas) {
                    if (c == primera) continue;
                    for (Literal l2 : c.literales) {
                        if (l.equals(l2.negado())) {
                            literalOpuesto = l;
                            segunda = c;
                            break;
                        }
                    }
                    if (segunda != null) break;
                }
                if (segunda != null) break;
            }

            // Si no hay cláusula opuesta, no se puede probar la conjetura
            if (segunda == null) {
                System.out.println("--------------------------");
                System.out.println("No se encontró una cláusula opuesta. Fin del proceso. Conjetura falsa.");
                return false;
            }

            // Generar cláusula resolvente
            Set<Literal> union = new HashSet<>(primera.literales);
            union.addAll(segunda.literales);
            union.remove(literalOpuesto);
            union.remove(literalOpuesto.negado());
            Clausula resolvente = new Clausula(union);

            // Si se deriva la cláusula vacía, hay contradicción
            if (resolvente.literales.isEmpty()) {
                System.out.println("--------------------------");
                System.out.println("Se ha derivado la cláusula vacía. Contradicción encontrada. La conjetura es verdadera");
                return true;
            }

            // Remover las dos cláusulas utilizadas y agregar la nueva
            clausulas.remove(primera);
            clausulas.remove(segunda);
            clausulas.add(resolvente);
            System.out.println("Nueva cláusula generada: " + resolvente);
        }
        return false;
    }
}
