import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class SimpleHTTPServer {
  public static void main(String[] args) throws Exception {
    final ServerSocket server = new ServerSocket(8080);
    System.out.println("listening for connection on port 8080 .....");
    while (true) {
      try (Socket client = server.accept()) {
        // 1. Read HTTP request from the client socket
        InputStreamReader isr = new InputStreamReader(client.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line = reader.readLine();
        while (line != null && !line.isEmpty()) {
          System.out.println(line);
          line = reader.readLine();
        }
        // 2. Prepare an HTTP response
        Date today = new Date();
        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
        // 3. Send HTTP response to the client
        client.getOutputStream().write(httpResponse.getBytes("UTF-8"));
        // 4. Close the socket (in this case the try/catch will close automatically for us)
      } catch (IOException e) {
        System.err.println(e);
      }
    }
  }
}