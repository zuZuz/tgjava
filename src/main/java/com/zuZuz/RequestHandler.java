package com.zuZuz;

public class RequestHandler {
    private static final String helpText =
            "Available commands:\n" +
            "/help - print this message\n";

    private static boolean checkCmd(String cmd, String check) {
        return cmd.equalsIgnoreCase(check.substring(0, Math.min(check.length(), cmd.length())));
    }

    private static void parse(String cmd[]) {

    }

    public static String getResponse(String request) {
        String cmd[] = request.split(" " );
        String response = "Please enter command from /help";

        if (checkCmd(cmd[0], "/start")) {
            response = "Greetings! To see available commands please type /help";
        }

        if (checkCmd(cmd[0], "/help")) {
            response = helpText;
        }

        return response;
    }
}