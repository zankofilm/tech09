package ir.scooljavanrood.madreseyar.teacher;

import org.json.JSONObject;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/** Direct Android -> original Madreseyar JSON API. No embedded browser, no local DB mirror. */
final class SchoolApi {
    static final String BASE = "https://scooljavanrood.ir/app";
    private SchoolApi() {}
    static class HttpError extends Exception {
        final int code;
        HttpError(int status, String message) { super(message); code = status; }
    }
    static JSONObject request(String path, String method, String bearer, JSONObject body) throws Exception {
        if (!path.startsWith("/api/")) throw new IllegalArgumentException("Unexpected API path");
        URL url = new URL(BASE + path);
        HttpURLConnection c = (HttpURLConnection) url.openConnection();
        c.setInstanceFollowRedirects(false);
        c.setConnectTimeout(14000);
        c.setReadTimeout(18000);
        c.setRequestMethod(method);
        c.setRequestProperty("Accept", "application/json");
        c.setRequestProperty("Cache-Control", "no-store");
        if (bearer != null && !bearer.isEmpty()) c.setRequestProperty("Authorization", "Bearer " + bearer);
        if (body != null) {
            c.setDoOutput(true);
            c.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            try (OutputStream stream = c.getOutputStream()) {
                stream.write(body.toString().getBytes(StandardCharsets.UTF_8));
            }
        }
        try {
            int status = c.getResponseCode();
            InputStream in = status >= 400 ? c.getErrorStream() : c.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            if (in != null) try (InputStream stream = in) {
                byte[] block = new byte[4096]; int n;
                while ((n = stream.read(block)) != -1) {
                    if (out.size() + n > ("/api/state".equals(path) ? 20 : 5) * 1024 * 1024) throw new Exception("پاسخ سرور بیش از حد بزرگ است.");
                    out.write(block, 0, n);
                }
            }
            String raw = out.toString("UTF-8");
            JSONObject obj;
            try { obj = new JSONObject(raw); }
            catch (Exception ex) { throw new Exception("پاسخ سرور معتبر نیست؛ مسیر API را بررسی کنید."); }
            if (status >= 300) throw new HttpError(status, obj.optString("error", "مسیر سرویس در دسترس نیست."));
            return obj;
        } finally { c.disconnect(); }
    }
}
