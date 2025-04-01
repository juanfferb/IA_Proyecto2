import java.util.LinkedHashMap;
import java.util.Map;

public class Unificador {

    public static Map<String, String> unificar(Literal l1, Literal l2) {
        if (!l1.predicado.equals(l2.predicado) || l1.argumentos.length != l2.argumentos.length) {
            return null;
        }

        Map<String, String> sustituciones = new LinkedHashMap<>();

        for (int i = 0; i < l1.argumentos.length; i++) {
            String arg1 = l1.argumentos[i];
            String arg2 = l2.argumentos[i];

            // Si son iguales, no hay nada que hacer
            if (arg1.equals(arg2)) continue;

            if (esVariable(arg1)) {
                sustituciones.put(arg1, arg2);
            } else if (esVariable(arg2)) {
                sustituciones.put(arg2, arg1);
            } else {
                return null; // conflicto: constantes distintas
            }
        }

        return sustituciones;
    }

    public static boolean esVariable(String s) {
        return s.matches("^[a-z]$"); // Por convención, variables son letras minúsculas sueltas
    }

    public static Literal aplicarSustitucion(Literal l, Map<String, String> sustituciones) {
        String[] nuevosArgs = new String[l.argumentos.length];
        for (int i = 0; i < l.argumentos.length; i++) {
            String arg = l.argumentos[i];
            nuevosArgs[i] = sustituciones.getOrDefault(arg, arg);
        }
        return new Literal(l.predicado, nuevosArgs);
    }
}
