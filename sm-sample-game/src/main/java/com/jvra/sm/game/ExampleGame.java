package com.jvra.sm.game;

/**
 * Created by Jansel R. Abreu (Vanwolf) on 7/21/2014.
 */
public class ExampleGame {
    public static void main(String... args) {

        HightScore hc = new HightScore("ExampleGame");
        if (args.length == 0)
            usage();

        if (args[0].equals("get"))
            System.out.println("score = " + hc.getScore());
        else if (args[0].equals("set"))
            hc.setScore(Integer.parseInt(args[1]));
        else
            usage();
    }

    public static void usage() {
        System.out.println("ExampleGame get");
        System.out.println("ExampleGame set <param>");
        System.out.println();
        System.exit(0);
    }
}
