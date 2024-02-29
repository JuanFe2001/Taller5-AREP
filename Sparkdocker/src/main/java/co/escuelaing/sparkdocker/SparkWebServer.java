/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package co.escuelaing.sparkdocker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static spark.Spark.port;
import static spark.Spark.get;


import spark.Spark;

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
        String[] result = new String[array.length];
        
        for (int i = 0; i < array.length; i++) {
            boolean isPalindrome = true;
            String string = array[i];
            int stringLength = string.length();
            
            for (int j = 0; j < stringLength / 2; j++) {
                if (string.toLowerCase().charAt(j) != string.toLowerCase().charAt(stringLength - 1 - j)) {
                    isPalindrome = false;
                    break;
                }
            }
            
            result[i] = String.valueOf(isPalindrome);
        }
        
        return result;
    }

    public static double vectorMagnitude(double[] array) {
        int index = 0;
        double result = 0;
        
        for (int i = 0; i < array.length - 1; i += 2) {
            result = Math.sqrt(Math.pow(array[i], 2) + Math.pow(array[i + 1], 2));
            index++;
        }
        
        return result;
    }

    public static double[] doubleArrayParams(String paramsString) {
        String params = paramsString.replace(")", "");
        String[] arrayStrings = params.split(",");
        double[] array = new double[arrayStrings.length];
        
        for (int i = 0; i < arrayStrings.length; i++) {
            array[i] = Double.parseDouble(arrayStrings[i]);
        }
        
        return array;
    }

    public static void mathMethods(double[] array, String command) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < array.length; i++) {
            Method method = Math.class.getMethod(command, double.class);
            array[i] = (double) method.invoke(null, array[i]);
        }
    }

    public static String webClient() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "        <title>Calculadora</title>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    </head>\n" +
                "    <body style=\"display:flex\">\n" +
                "       <div>" +
                "        <h1>Calcular Seno</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-sin\">Número (Si es más de uno, toca separarlos por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-sin\" name=\"name\" value=\"-3.67\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('sin')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-sin\"></div>\n" +
                "       </div>" +
                "       <div>" +
                "        <h1>Calcular Coseno</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-cos\">Número (Si es más de uno, toca separarlos por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-cos\" name=\"name\" value=\"-3.67\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('cos')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-cos\"></div>\n" +
                "       </div>" +
                "       <div>" +
                "        <h1>Determinar si es palíndromo</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-pal\">Cadena (Si es más de una, toca separarlas por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-pal\" name=\"name\" value=\"Reconocer\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('pal')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-pal\"></div>\n" +
                "       </div>" +
                "       <div>" +
                "        <h1>Calcular magnitud del vector</h1>\n" +
                "        <form action=\"/computar\">\n" +
                "            <label for=\"params-vem\">Parametros (Deben estar separados por comas sin espacios):</label><br>\n" +
                "            <input type=\"text\" id=\"params-vem\" name=\"name\" value=\"3,4\"><br><br>\n" +
                "            <input type=\"button\" value=\"Calcular\" onclick=\"loadGetMsg('vem')\">\n" +
                "        </form> \n" +
                "        <h3>Resultado</h3>\n" +
                "        <div id=\"getrespmsg-vem\"></div>\n" +
                "       </div>" +
                "\n" +
                clientJS() +
                "    </body>";
    }

    public static String clientJS() {
        return "        <script>\n" +
                "            function loadGetMsg(command) {\n" +
                "                let number = document.getElementById(\"params-\"+ command).value;\n" +
                "                const xhttp = new XMLHttpRequest();\n" +
                "                xhttp.onload = function() {\n" +
                "                    document.getElementById(\"getrespmsg-\" + command).innerHTML =\n" +
                "                    this.responseText;\n" +
                "                }\n" +
                "                xhttp.open(\"GET\", \"/computar?comando=\" + command + \"(\" + number +\")\");\n" +
                "                xhttp.send();\n" +
                "            }\n" +
                "        </script>\n";
    }
}
