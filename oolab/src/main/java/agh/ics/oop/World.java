package agh.ics.oop;

public class World {
    public static void run(String[] args) {
        System.out.println("Zwierzak idzie do przodu");
        for(int i=0; i<args.length-1; i++) {
            System.out.print(args[i]+", ");
        }
        if(args.length > 0) {
            System.out.println(args[args.length - 1]);
        }
    }
    public static void main(String[] args) {
        System.out.println("System wystartował");
        run(args);
        System.out.println("System zakończył działanie");
    }
}
