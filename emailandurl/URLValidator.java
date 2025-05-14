package emailandurl;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class URLValidator {
    private static final List<String> riskyTLDs = Arrays.asList("tk", "ml", "ga", "cf", "gq", "zip", "mov", "top", "xyz", "click", "work", "link", "fit", "cam", "loan", "stream");
    private static final List<String> trustedTLDs = Arrays.asList("com", "org", "net", "edu", "gov", "mil", "fr", "de", "uk", "us", "ca", "eu", "io", "ai", "co");

    public static String[] validate(String input) {
        try {
            URL url = new URL(input);
            String protocol = url.getProtocol();
            String host = url.getHost();

            if (!(protocol.equals("http") || protocol.equals("https"))) {
                return new String[]{"false", "Protocole non sécurisé ou interdit : " + protocol};
            }
            if (host.matches("^(\\d{1,3}\\.){3}\\d{1,3}$")) {
                return new String[]{"false", "Utilisation d’une IP dans l’URL"};
            }

            int dotCount = host.length() - host.replace(".", "").length();
            if (dotCount > 3) {
                return new String[]{"false", "Trop de sous-domaines dans l’URL"};
            }

            String[] parts = host.split("\\.");
            String tld = parts[parts.length - 1].toLowerCase();

            if (host.replace(".", "").length() < 5) {
                return new String[]{"false", "Domaine trop court"};
            }
            if (riskyTLDs.contains(tld)) {
                return new String[]{"false", "TLD à risque : ." + tld};
            }
            if (!trustedTLDs.contains(tld)) {
                return new String[]{"false", "TLD non reconnu : ." + tld};
            }

            return new String[]{"true", "URL bien formée et fiable"};
        } catch (Exception e) {
            return new String[]{"false", "Erreur de parsing ou format invalide"};
        }
    }
}

