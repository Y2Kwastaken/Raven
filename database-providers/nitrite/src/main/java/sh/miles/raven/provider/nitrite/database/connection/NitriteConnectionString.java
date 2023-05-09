package sh.miles.raven.provider.nitrite.database.connection;

import java.io.File;
import java.util.regex.Pattern;

import com.google.common.base.Preconditions;

public class NitriteConnectionString {

    public static final String CONNECTION_STRING = "nitrite:%s@%s:%s";
    @SuppressWarnings("java:S5867") // Unicode characters should not be used for ':' (U+003A) in URIs
    public static final Pattern CONNECTION_STRING_PATTERN = Pattern.compile("^(\\S+?):(\\S+?)(?::|$)(.*)");

    private final String filePath;
    private final String username;
    private final String password;

    public NitriteConnectionString(String connectionString) {
        final String[] parsedConnectionString = parseConnectionString(connectionString);
        this.username = parsedConnectionString[0];
        this.password = parsedConnectionString[1];
        this.filePath = parsedConnectionString[2];
    }

    public NitriteConnectionString(String filePath, String username, String password) {
        this.filePath = filePath;
        this.username = username;
        this.password = password;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getConnectionString() {
        return String.format(CONNECTION_STRING, username, filePath, password);
    }

    /**
     * Parses a Nitrite connection string into its components.
     * e.g.
     * {@code String connectionString = "nitrite:username@password/path/to/file.db"}
     * would be parsed into {@code String[] parsed = {"username", "password",
     * "/path/to/file.db"}}`
     * 
     * 
     * @param connectionString A valid Nitrite connection string in the format of
     *                         {@link #CONNECTION_STRING}
     * @return
     */
    public static String[] parseConnectionString(String connectionString) {
        final var matcher = CONNECTION_STRING_PATTERN.matcher(connectionString);
        final String[] parsed = new String[3];

        if (matcher.find()) {
            Preconditions.checkArgument(matcher.group(1).equals("nitrite"),
                    "Connection string does not start with nitrite cannot be null");

            final String[] usernamePassword = matcher.group(2).split("@");
            Preconditions.checkArgument(usernamePassword.length == 2,
                    "Connection string does not contain a username and password");
            parsed[0] = usernamePassword[0];
            parsed[1] = usernamePassword[1];
            parsed[2] = matcher.group(3);
            if (parsed[2] == null || parsed[2].isEmpty()) {
                throw new IllegalStateException("Connection string does not contain a file path");
            }
            // validate parsed[2] is not a FILE it MUST BE A FOLDER
            if (new File(parsed[2]).isFile()) {
                throw new IllegalStateException("Connection string must contain a folder path not a file path");
            }
        }

        return parsed;
    }

}
