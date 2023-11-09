package hr.algebra.javafxmonopoly.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class DocumentationUtils {

    public static void generateHtmlDocumentationFile(URI path) throws IOException, ClassNotFoundException {

        Path directoryPath = Paths.get(path).getParent();

        Files.createDirectories(directoryPath);

        List<String> listOfClassFilePaths = Files.walk(Paths.get("target"))
                .map(Path::toString)
                .filter(f -> f.endsWith(".class"))
                .filter(f -> !f.endsWith("module-info.class"))
                .toList();

        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<!DOCTYPE html><html><head><title>Class Documentation</title><style>");
        htmlBuilder.append("body{margin:20px;font-family:'Arial',sans-serif;line-height:0.5;}");
        htmlBuilder.append("div.indent{margin-left:50px;}");
        htmlBuilder.append("h2{margin-bottom:25px; margin-top:50px;}");
        htmlBuilder.append("</style></head><body>");

        Iterator var2 = listOfClassFilePaths.iterator();

        while (var2.hasNext()) {
            String classFilePath = (String) var2.next();
            String[] pathTokens = classFilePath.split("classes");
            String secondToken = pathTokens[1];
            String fqnWithSlashes = secondToken.substring(1, secondToken.lastIndexOf(46));
            String fqn = fqnWithSlashes.replace('\\', '.');
            Class<?> deserializedClass = Class.forName(fqn);

            htmlBuilder.append("<h2>").append(deserializedClass.getName()).append("</h2>");

            Field[] fields = deserializedClass.getDeclaredFields();
            Method[] methods = deserializedClass.getDeclaredMethods();

            appendFields(htmlBuilder, fields);
            appendMethods(htmlBuilder, methods);
        }

        htmlBuilder.append("</body></html>");

        Files.write(Path.of(path), htmlBuilder.toString().getBytes());
    }

    private static void appendFields(StringBuilder htmlBuilder, Field[] fields) {
        htmlBuilder.append("<div class=\"indent\"><h3>Fields:</h3><div class=\"indent\">");
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            htmlBuilder.append("<p>").append(getModifiers(modifiers))
                    .append(field.getType().getTypeName()).append(" ").append(field.getName()).append("</p>");
        }
        htmlBuilder.append("</div></div>");
    }

    private static void appendMethods(StringBuilder htmlBuilder, Method[] methods) {
        htmlBuilder.append("<div class=\"indent\"><h3>Methods:</h3><div class=\"indent\">");
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            htmlBuilder.append("<p>").append(getModifiers(modifiers))
                    .append(method.getReturnType().getTypeName()).append(" ").append(method.getName())
                    .append("()</p>");
        }
        htmlBuilder.append("</div></div>");
    }

    private static String getModifiers(int modifiers) {
        StringBuilder result = new StringBuilder();

        if (Modifier.isPublic(modifiers)) {
            result.append("public ");
        } else if (Modifier.isPrivate(modifiers)) {
            result.append("private ");
        } else if (Modifier.isProtected(modifiers)) {
            result.append("protected ");
        }

        if (Modifier.isStatic(modifiers)) {
            result.append("static ");
        }

        if (Modifier.isFinal(modifiers)) {
            result.append("final ");
        }

        return result.toString();
    }
}
