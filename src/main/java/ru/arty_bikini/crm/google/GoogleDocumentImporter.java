package ru.arty_bikini.crm.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import kotlin.text.Charsets;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


@Component
public class GoogleDocumentImporter {

    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_READONLY);
    private static final String SERVICE_ACCOUNT_FILE_PATH = "./google/modular-temple-368606-f747c540acf6.json";

    private Credential getServiceAccount(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(SERVICE_ACCOUNT_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + SERVICE_ACCOUNT_FILE_PATH);
        }

        GoogleCredential googleCredential = GoogleCredential
                .fromStream(in, HTTP_TRANSPORT, JSON_FACTORY)
                .createScoped(SCOPES);

        System.out.println(googleCredential.getServiceAccountScopes());

        return googleCredential;
    }

    //@PostConstruct
    public String importSheet(String fileId) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getServiceAccount(HTTP_TRANSPORT))
                .build();

        InputStream execute = service.files()
                .export(fileId,  "text/csv")
                .executeMediaAsInputStream();

        byte[] data = execute.readAllBytes();

        String file = new String(data, Charsets.UTF_8);

        System.out.println(file);

        return file;
    }
}