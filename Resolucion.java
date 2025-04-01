import java.util.*;

// Implementa el motor de resolución para inferencia lógica
public class Resolucion {
    public List<Clausula> clausulas = new ArrayList<>(); // Base de conocimiento
    public Clausula vacio = new Clausula(); // Clausula vacía
    private Literal literalMeta = null; // Literal negado de la conjetura (por ejemplo, -Odia(...))

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
            Literal negado = l.negado();
            Clausula negada = new Clausula();
            negada.literales.add(negado);
            clausulas.add(negada);
            literalMeta = negado; // Guardamos el literal meta para priorizarlo en la resolución
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
    public boolean resolver() {
        List<Clausula> yaVistas = new ArrayList<>(clausulas);
        Queue<Clausula> pendientes = new LinkedList<>();
        List<String> paresProcesados = new ArrayList<>();

        Clausula clausulaMeta = null;
        for (Clausula c : clausulas) {
            if (c.literales.contains(literalMeta)) {
                clausulaMeta = c;
                break;
            }
        }

        // FORZAR combinación con cláusula que contenga el literal complementario de literalMeta
        if (clausulaMeta != null) {
            Clausula mejor = null;
            for (Clausula otra : clausulas) {
                if (clausulaMeta.equals(otra)) continue;
                for (Literal li : clausulaMeta.literales) {
                    Literal negado = li.negado();
                    if (otra.literales.contains(negado)) {
                        if (mejor == null || otra.literales.size() < mejor.literales.size()) {
                            mejor = otra;
                        }
                    }
                }
            }
            if (mejor != null) {
                for (Literal li : clausulaMeta.literales) {
                    Literal negado = li.negado();
                    if (mejor.literales.contains(negado)) {
                        System.out.println("Resolviendo primero: " + clausulaMeta + " y " + mejor);
                        Set<Literal> resolvente = new LinkedHashSet<>(clausulaMeta.literales);
                        resolvente.addAll(mejor.literales);
                        resolvente.remove(li);
                        resolvente.remove(negado);
                        Clausula nueva = new Clausula(resolvente);
                        boolean yaExiste = false;
                        for (Clausula vista : yaVistas) {
                            if (vista.equals(nueva)) {
                                yaExiste = true;
                                break;
                            }
                        }
                        if (!yaExiste) {
                            System.out.println("Nueva cláusula generada: " + nueva);
                            if (nueva.literales.isEmpty()) {
                                System.out.println("Se ha derivado la cláusula vacía. Contradicción encontrada. La conjetura es verdadera");
                                return true;
                            }
                            pendientes.add(nueva);
                            yaVistas.add(nueva);
                            clausulas.add(nueva);
                        }
                        break;
                    }
                }
            }
        }

        while (!pendientes.isEmpty()) {
            Clausula actual = pendientes.poll();
            Clausula mejor = null;
            Literal literalSeleccionado = null;
            Literal negadoSeleccionado = null;

            for (Clausula otra : clausulas) {
                if (actual.equals(otra)) continue;
                for (Literal li : actual.literales) {
                    Literal negado = li.negado();
                    if (otra.literales.contains(negado)) {
                        if (mejor == null || otra.literales.size() < mejor.literales.size()) {
                            mejor = otra;
                            literalSeleccionado = li;
                            negadoSeleccionado = negado;
                        }
                    }
                }
            }

            if (mejor != null && literalSeleccionado != null && negadoSeleccionado != null) {
                System.out.println("Resolviendo: " + actual + " y " + mejor);
                Set<Literal> resolvente = new LinkedHashSet<>(actual.literales);
                resolvente.addAll(mejor.literales);
                resolvente.remove(literalSeleccionado);
                resolvente.remove(negadoSeleccionado);

                Clausula nueva = new Clausula(resolvente);
                boolean yaExiste = false;
                for (Clausula vista : yaVistas) {
                    if (vista.equals(nueva)) {
                        yaExiste = true;
                        break;
                    }
                }
                if (!yaExiste) {
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

        System.out.println("No se encontró una cláusula opuesta. Fin del proceso. Conjetura falsa.");
        return false;
    }
}