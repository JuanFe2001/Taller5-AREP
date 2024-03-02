package co.escuelaing.sparkdocker;

import spark.Spark;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SparkWebServer {

    public static void main(String... args) {
        Spark.port(getPort());

        Spark.get("/calculadora", (req, res) -> webClient());

        Spark.get("/computar", (req, res) -> {
            res.type("application/json");
            return compute(req.queryParams("comando"));
        });
    }

    private static int getPort() {
        return (System.getenv("PORT") != null) ? Integer.parseInt(System.getenv("PORT")) : 4567;
    }

    public static String compute(String query) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String outputLine = "{\"result\":";
        String[] commandAndParams = query.split("\\(");
        String command = commandAndParams[0];

        if (command.equals("pal")) {
            String[] result = palindrome(commandAndParams[1].replace(")", "").split(","));
            outputLine += Arrays.toString(result);
        } else {
            double[] array = doubleArrayParams(commandAndParams[1]);

            if (command.equals("vem")) {
                array = new double[]{vectorMagnitude(array)};
            } else {
                mathMethods(array, command);
            }

            outputLine += Arrays.toString(array);
        }

        return outputLine + "}";
    }

    public static String[] palindrome(String[] array) {
        return Arrays.stream(array)
                .map(s -> String.valueOf(isPalindrome(s)))
                .toArray(String[]::new);
    }

    private static boolean isPalindrome(String string) {
        int stringLength = string.length();
        for (int j = 0; j < stringLength / 2; j++) {
            if (Character.toLowerCase(string.charAt(j)) != Character.toLowerCase(string.charAt(stringLength - 1 - j))) {
                return false;
            }
        }
        return true;
    }

    public static double vectorMagnitude(double[] array) {
        double result = 0;
        for (int i = 0; i < array.length - 1; i += 2) {
            result = Math.sqrt(Math.pow(array[i], 2) + Math.pow(array[i + 1], 2));
        }
        return result;
    }

    public static double[] doubleArrayParams(String paramsString) {
        return Arrays.stream(paramsString.replace(")", "").split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();
    }

    public static void mathMethods(double[] array, String command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Math.class.getMethod(command, double.class);
        for (int i = 0; i < array.length; i++) {
            array[i] = (double) method.invoke(null, array[i]);
        }
    }

    public static String webClient() {
        return "<!DOCTYPE html>\n"
                + "<html lang=\"en\">\n"
                + "    <head>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "        <title>Calculadora</title>\n"
                + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"/styles.css\">\n"
                + "    </head>\n"
                + "    <body>\n"
                + "        <div class=\"calculator-container\">\n"
                + createForm("sin", "Seno")
                + createForm("cos", "Coseno")
                + createForm("pal", "Determinar si es palíndromo")
                + createForm("vem", "Calcular magnitud del vector")
                + "        </div>\n"
                + clientJS()
                + "    </body>\n"
                + "</html>";
    }

    private static String createForm(String command, String title) {
        return "            <div class=\"calculator-form\">\n"
                + "                <h1>" + title + "</h1>\n"
                + "                <form action=\"/computar\">\n"
                + "                    <label for=\"params-" + command + "\">Número (Si es más de uno, toca separarlos por comas sin espacios):</label><br>\n"
                + "                    <input type=\"text\" id=\"params-" + command + "\" name=\"name\" value=\"-3.67\"><br><br>\n"
                + "                    <button type=\"button\" onclick=\"loadGetMsg('" + command + "')\">Calcular</button>\n"
                + "                </form>\n"
                + "                <h3>Resultado</h3>\n"
                + "                <div id=\"getrespmsg-" + command + "\"></div>\n"
                + "            </div>";
    }

    public static String clientJS() {
        return "        <script>\n"
                + "            function loadGetMsg(command) {\n"
                + "                let number = document.getElementById(\"params-\" + command).value;\n"
                + "                const xhttp = new XMLHttpRequest();\n"
                + "                xhttp.onload = function() {\n"
                + "                    document.getElementById(\"getrespmsg-\" + command).innerHTML = this.responseText;\n"
                + "                }\n"
                + "                xhttp.open(\"GET\", \"/computar?comando=\" + command + \"(\" + number + \")\");\n"
                + "                xhttp.send();\n"
                + "            }\n"
                + "        </script>\n";
    }
}
