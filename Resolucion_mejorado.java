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
    public Map<String, String> unificarVariables(String a, String b) {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("a", a);
        mapa.put("b", b);
        return mapa;
    }

    // Sustituye las variables por nombres reales en las cláusulas
    public void sustituirVariables(Map<String, String> variables) {
        List<Clausula> nuevas = new ArrayList<>();
        for (Clausula c : clausulas) {
            Set<Literal> nuevos = new LinkedHashSet<>();
            for (Literal l : c.literales) {
                String[] nuevosArgs = Arrays.stream(l.argumentos)
                        .map(arg -> variables.getOrDefault(arg, arg))
                        .toArray(String[]::new);
                nuevos.add(new Literal(l.predicado, nuevosArgs));
            }
            nuevas.add(new Clausula(nuevos));
        }
        clausulas = nuevas;
    }

    // Aplica el algoritmo de resolución para derivar la contradicción
    // Aplica el algoritmo de resolución para derivar la contradicción
    public boolean resolver() {
        Set<Clausula> yaVistas = new HashSet<>(clausulas);
        Queue<Clausula> pendientes = new LinkedList<>(clausulas);
        Set<String> paresProcesados = new HashSet<>();

        while (!pendientes.isEmpty()) {
            Clausula actual = pendientes.poll();

            // Ordenar la lista de clausulas por número de literales para favorecer resoluciones simples
            List<Clausula> ordenadas = new ArrayList<>(clausulas);
            ordenadas.sort(Comparator.comparingInt(c -> c.literales.size()));

            for (Clausula otra : ordenadas) {
                if (actual.equals(otra)) continue;

                String clave = actual.hashCode() < otra.hashCode()
                    ? actual.hashCode() + "-" + otra.hashCode()
                    : otra.hashCode() + "-" + actual.hashCode();

                if (paresProcesados.contains(clave)) continue;
                paresProcesados.add(clave);

                for (Literal li : actual.literales) {
                    for (Literal lj : otra.literales) {
                        if (li.equals(lj.negado())) {
                            Set<Literal> resolvente = new LinkedHashSet<>(actual.literales);
                            resolvente.addAll(otra.literales);
                            resolvente.remove(li);
                            resolvente.remove(lj);
                            Clausula nueva = new Clausula(resolvente);

                            if (!yaVistas.contains(nueva)) {
                                System.out.println("Nueva cláusula generada: " + nueva);
                                if (nueva.literales.isEmpty()) {
                                    System.out.println("Se ha derivado la cláusula vacía. Contradicción encontrada. La conjetura es verdadera");
                                    return true;
                                }
                                pendientes.add(nueva);
                                yaVistas.add(nueva);
                                clausulas.add(nueva);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("No se encontró una cláusula opuesta. Fin del proceso. Conjetura falsa.");
        return false;
    }

}
