package myproject;

import myproject.data.MessageModel;
import myproject.services.Service;

/**
 * The application.
 */
public final class MyApplication {

    /**
     * Run the application.
     *
     * @param args command line arguments are ignored
     */
    public static void main(final String[] args) {
        Service.printMessage(new MessageModel("007.26"));
    }
}