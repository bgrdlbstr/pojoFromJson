package uk.co.bigredlobster.pojoFromJson;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.bigredlobster.pojoFromJson.json.WebApp;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.base.Preconditions.checkNotNull;

public class Main {
    public static void main(String[] args) {
        System.out.println("Run the build to make me compile");

        try {
            final String content = getFileContentAsString();

            final ObjectMapper mapper = new ObjectMapper();
            final Reader reader = new StringReader(content);
            final WebApp webApp = mapper.readValue(reader, WebApp.class);

            final String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(webApp);
            System.out.println("webApp = " + indented);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't read input file", e);
        }
    }

    private static String getFileContentAsString() {
        final ClassLoader classLoader = Main.class.getClassLoader();
        try {
            final URI uri = classLoader.getResource("json/web-app.json").toURI();
            checkNotNull(uri);
            final Path path = Paths.get(uri);
            return new String(Files.readAllBytes(path));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load json file", e);
        }
    }
}
