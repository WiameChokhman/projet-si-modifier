package emailandurl;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    private static final List<String> riskyTLDs = Arrays.asList("tk", "ml", "ga", "cf", "gq", "zip", "mov", "top", "xyz", "click", "work", "link", "fit", "cam", "loan", "stream");
    private static final List<String> trustedTLDs = Arrays.asList("com", "org", "net", "edu", "gov", "mil", "fr", "de", "uk", "us", "ca", "eu", "io", "ai", "co");
    private static final List<String> genericEmails = Arrays.asList("admin", "info", "contact", "support", "noreply");

    public static String[] validate(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9.-]+)\\.(\\w{2,})$";
        Matcher matcher = Pattern.compile(regex).matcher(email);
        if (!matcher.matches()) {
            return new String[]{"false", "Format incorrect"};
        }

        String[] parts = email.split("@");
        String username = parts[0];
        String domainPart = parts[1];
        String tld = domainPart.substring(domainPart.lastIndexOf('.') + 1).toLowerCase();

        if (genericEmails.contains(username.toLowerCase())) {
            return new String[]{"false", "Nom d’utilisateur trop générique"};
        }
        if (username.matches(".*\\d{4,}.*")) {
            return new String[]{"false", "Nom d’utilisateur contient trop de chiffres"};
        }
        if (riskyTLDs.contains(tld)) {
            return new String[]{"false", "TLD à risque : ." + tld};
        }
        if (!trustedTLDs.contains(tld)) {
            return new String[]{"false", "TLD non reconnu : ." + tld};
        }
        return new String[]{"true", "E-mail bien formé et domaine correct"};
    }
}

