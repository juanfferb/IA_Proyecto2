import java.util.*;

// Implementa el motor de resolución para inferencia lógica
public class Resolucion {
    public List<Clausula> clausulas = new ArrayList<>(); // Base de conocimiento
    public Clausula vacio = new Clausula(); // Clausula vacía
    private Literal literalMeta = null; // Literal negado de la conjetura (por ejemplo, -Odia(...))

    public void agregarClausula(Clausula c) {
        clausulas.add(c);
    }

    public void mostrarClausulas() {
        for (Clausula c : clausulas) {
            System.out.println(c);
        }
    }

    public void negarConjetura(List<Literal> conjetura) {
        for (Literal l : conjetura) {
            Literal negado = l.negado();
            Clausula negada = new Clausula();
            negada.literales.add(negado);
            clausulas.add(negada);
            literalMeta = negado;
        }
    }

    public Map<String, String> unificarVariables(String a, String b) {
        Map<String, String> mapa = new HashMap<>();
        mapa.put("a", a);
        mapa.put("b", b);
        return mapa;
    }

    public void sustituirVariables(Map<String, String> variables) {
        List<Clausula> nuevas = new ArrayList<>();
        for (Clausula c : clausulas) {
            List<Literal> nuevos = new ArrayList<>();
            for (Literal l : c.literales) {
                String[] nuevosArgs = Arrays.stream(l.argumentos)
                        .map(arg -> variables.getOrDefault(arg, arg))
                        .toArray(String[]::new);
                nuevos.add(new Literal(l.predicado, nuevosArgs));
            }
            Clausula nueva = new Clausula();
            nueva.literales.addAll(nuevos);
            nuevas.add(nueva);
        }
        clausulas = nuevas;
    }

    public boolean resolver() {
        List<Clausula> yaVistas = new ArrayList<>(clausulas);
        Queue<Clausula> pendientes = new LinkedList<>();

        Clausula clausulaMeta = null;
        for (Clausula c : clausulas) {
            if (c.literales.contains(literalMeta)) {
                clausulaMeta = c;
                break;
            }
        }

        if (clausulaMeta != null) {
            for (Clausula otra : new ArrayList<>(clausulas)) {
                if (clausulaMeta.equals(otra)) continue;
                if (otra.literales.contains(literalMeta.negado())) {
/*                     System.out.println("\n--------------------------");
                    System.out.println("Resolviendo primero: " + clausulaMeta + " y " + otra); */
                    List<Literal> resolvente = new ArrayList<>();
                    for (Literal l : clausulaMeta.literales) {
                        if (!l.equals(literalMeta)) resolvente.add(l);
                    }
                    for (Literal l : otra.literales) {
                        if (!l.equals(literalMeta.negado())) resolvente.add(l);
                    }
                    Clausula nueva = new Clausula();
                    nueva.literales.addAll(resolvente);

                    System.out.println("Nueva cláusula generada: " + nueva);
                    if (nueva.literales.isEmpty()) {
                        System.out.println("Se ha derivado la cláusula vacía. Contradicción encontrada. La conjetura es verdadera");
                        return true;
                    }
                    pendientes.add(nueva);
                    yaVistas.add(nueva);
                    clausulas.add(nueva);
                    break;
                }
            }
        }

        while (!pendientes.isEmpty()) {
            Clausula actual = pendientes.poll();
            for (Clausula otra : new ArrayList<>(clausulas)) {
                if (actual.equals(otra)) continue;

                for (Literal li : actual.literales) {
                    Literal negado = li.negado();
                    if (otra.literales.contains(negado)) {
/*                         System.out.println("\n--------------------------");
                        System.out.println("Resolviendo: " + actual + " y " + otra); */
                        List<Literal> resolvente = new ArrayList<>();
                        for (Literal l : actual.literales) {
                            if (!l.equals(li)) resolvente.add(l);
                        }
                        for (Literal l : otra.literales) {
                            if (!l.equals(negado)) resolvente.add(l);
                        }

                        Clausula nueva = new Clausula();
                        nueva.literales.addAll(resolvente);
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

        System.out.println("No se encontró una cláusula opuesta. Fin del proceso. Conjetura falsa.");
        return false;
    }
}
