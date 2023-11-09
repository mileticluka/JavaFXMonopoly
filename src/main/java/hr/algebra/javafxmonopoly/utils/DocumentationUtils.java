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

    public static void generateHtmlDocumentationFile(String path) throws IOException, ClassNotFoundException {

        Path directoryPath = Paths.get(path).getParent();

        Files.createDirectories(directoryPath);

        List<String> listOfClassFilePaths = Files.walk(Paths.get("target"))
                .map(Path::toString)
                .filter(f -> f.endsWith(".class"))
                .filter(f -> !f.endsWith("module-info.class"))
                .toList();

        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<!DOCTYPE html><html>");

        htmlBuilder.append("<head>");

        htmlBuilder.append("<title>Class Documentation</title>");

        htmlBuilder.append("<style>");
        htmlBuilder.append("body{font-family:'Arial',sans-serif;line-height:0.5; color: #333333;}");
        htmlBuilder.append("div.indent{margin-left:50px;}");
        htmlBuilder.append("div.clazzSection{border-radius:10px; background-color: #f0f0f0; padding: 10px; margin-top: 30px; margin-bottom: 30px; box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19); width: 50%; margin-left:25%}");
        htmlBuilder.append("h2{margin-bottom:25px; color: #0095ff;}");
        htmlBuilder.append("h1{text-align: center;}");
        htmlBuilder.append("</style>");

        htmlBuilder.append("</head>");

        htmlBuilder.append("<body>");

        htmlBuilder.append("<h1>JavaFX Monopoly Documentation</h1>");

        Iterator iter = listOfClassFilePaths.iterator();

        while (iter.hasNext()) {
            String classFilePath = (String) iter.next();
            String[] pathTokens = classFilePath.split("classes");
            String secondToken = pathTokens[1];
            String fqnWithSlashes = secondToken.substring(1, secondToken.lastIndexOf(46));
            String fqn = fqnWithSlashes.replace('\\', '.');
            Class<?> deserializedClass = Class.forName(fqn);
            htmlBuilder.append("<div class=\"clazzSection\">");
            htmlBuilder.append("<h2>").append(deserializedClass.getName()).append("</h2>");

            Field[] fields = deserializedClass.getDeclaredFields();
            Method[] methods = deserializedClass.getDeclaredMethods();

            appendFields(htmlBuilder, fields);
            appendMethods(htmlBuilder, methods);
            htmlBuilder.append("</div class=\"clazzSection\">");
        }

        htmlBuilder.append("</body></html>");

        Files.writeString(Path.of(path), htmlBuilder.toString());
    }

    private static void appendFields(StringBuilder htmlBuilder, Field[] fields) {
        if(fields.length == 0)
        {
            return;
        }
        htmlBuilder.append("<div class=\"indent\"><h3>Fields:</h3><div class=\"indent\">");
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            htmlBuilder.append("<p>").append(getModifiers(modifiers))
                    .append(field.getType().getTypeName()).append(" ").append(field.getName()).append("</p>");
        }
        htmlBuilder.append("</div></div>");
    }

    private static void appendMethods(StringBuilder htmlBuilder, Method[] methods) {
        if(methods.length == 0)
        {
            return;
        }
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
