package de.h7r.beandiff.logger;

public interface DiffLogger {

    public static final DiffLogger STDOUT_LOGGER = new DiffLogger () {

                                                     public void log (String s) {
                                                         System.out.println (s);

                                                     }
                                                 };

    public static final DiffLogger NOOP_LOGGER   = new DiffLogger () {

                                                     public void log (String s) {

                                                     }
                                                 };

    void log (String s);

}
