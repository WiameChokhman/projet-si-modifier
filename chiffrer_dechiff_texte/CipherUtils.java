package chiffrer_dechiff_texte;
//(logique de chiffrement)+ fonction
public class CipherUtils {
    public static boolean validateKey(String algo, String key) {
        return switch (algo) {
            case "Caesar" -> key.matches("\\d+");
            case "Vigenère" -> key.matches("[a-zA-Z]+");
            case "ROT13" -> true;
            default -> false;
        };
    }
    public static String encrypt(String text, String algo, String key) {
        return switch (algo) {
            case "Caesar" -> caesarCipher(text, Integer.parseInt(key));
            case "ROT13" -> caesarCipher(text, 13);
            case "Vigenère" -> vigenereCipher(text, key, true);
            default -> text;
        };
    }
    public static String decrypt(String text, String algo, String key) {
        return switch (algo) {
            case "Caesar" -> caesarCipher(text, -Integer.parseInt(key));
            case "ROT13" -> caesarCipher(text, 13);
            case "Vigenère" -> vigenereCipher(text, key, false);
            default -> text;
        };
    }
    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) ((c - base + shift + 26) % 26 + base);
            }
            result.append(c);
        }
        return result.toString();
    }
    public static String vigenereCipher(String text, String key, boolean encrypt) {
        StringBuilder result = new StringBuilder();
        key = key.toLowerCase();
        int keyIndex = 0;
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                int shift = key.charAt(keyIndex % key.length()) - 'a';
                if (!encrypt) shift = -shift;
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                result.append((char) ((c - base + shift + 26) % 26 + base));
                keyIndex++;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}

