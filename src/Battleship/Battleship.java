package Battleship;

import java.io.*;
import java.util.Scanner;

public class Battleship
{
    private Player user;
    private Player computer;

    private final Scanner sc = new Scanner(System.in);
    public void run()
    {
        System.out.println("================================");
        System.out.println("Welcome to Battleship.Battleship.");
        System.out.println("================================");
        System.out.println("First you need to choose the location of your ships.");

        user = new Player();

        chooseShips();

        System.out.println("Hit enter to see your ship locations.");
        sc.next();

        user.showShipGrid();

        computer = new Player();

        System.out.println("Hit enter for the enemy to choose their ship locations.");
        sc.next();
        chooseComputerShips();
        computer.showShipGrid();
        System.out.println("The enemy has placed their ships.");
        System.out.println("Hit enter to start guessing.");
        sc.next();
        guessing();

    }

    private void chooseShips()
    {
        for (int i = 0; i < Player.numShips(); i++)
        {
            System.out.println("Hit enter to place the next ship.");
            sc.next();
            System.out.println("Your current grid of ships.");
            user.showShipGrid();
            System.out.println("\n");
            System.out.println("Now you need to place a ship of length " + Player.getShipLength(i));
            while (true)
            {
                System.out.println("Which row? (A-J) ");
                int row = (int) sc.next().charAt(0) - 65;

                System.out.println("Which column? (1-10) ");
                int col = sc.nextInt();

                System.out.println("Horizontal (0) or vertical (1)? ");
                int dir = sc.nextInt();

                if (placeShip(new Ship(Player.getShipLength(i)), row, col, dir, user))
                {
                    System.out.println("Invalid ship placement!");
                }
                else
                {
                    break;
                }
            }
        }
    }

    private void chooseComputerShips()
    {
        for (int i = 0; i < Player.numShips(); i++)
        {
            while (true)
            {
                int row = Randomizer.nextInt(0, 9);
                int col = Randomizer.nextInt(1, 10);
                int dir = Randomizer.nextInt(0, 1);
                if (!placeShip(new Ship(Player.getShipLength(i)), row, col, dir, computer))
                {
                    break;
                }
            }
        }
    }

    private void guessing()
    {
        while (user.getHitSections() != user.getShipSums() && computer.getHitSections() != computer.getShipSums())
        {
            System.out.println("Hit enter for your turn.");
            sc.next();
            System.out.println("Enemy grid.");
            computer.showPlayerGrid();
            System.out.println("It's your turn to guess");

            System.out.println("Which row? (A-J) ");
            int row = (int) sc.next().charAt(0) - 65;

            System.out.println("Which column? (1-10) ");
            int col = sc.nextInt()-1;

            if (computer.attacked(row, col))
            {
                System.out.println("You got a hit!");
            }
            else
            {
                System.out.println("Nope, that was a miss.");
            }
            computer.showPlayerGrid();

            if (computer.shipSunk() > 0)
            {
                int len = computer.shipSunk();
                computer.setShipSunk(0);
                System.out.println("You sunk a ship of length " + len + "!");
            }

            System.out.println("Total Hits = " + computer.getHitSections() + " out of " + computer.getShipSums());

            System.out.println("Hit enter for computer turn.");
            sc.next();
            computerGuess();
            System.out.println("Your grid.");
            user.showPlayerGrid();
            System.out.println("Total Hits = " + user.getHitSections() + " out of " + user.getShipSums());
        }

        if (user.getHitSections() == user.getShipSums())
        {
            System.out.println("Computer wins.");
        }
        else
        {
            System.out.println("You win!");
        }

        System.out.println("Thanks for playing.");
    }

    private void computerGuess()
    {
        while (true)
        {
            int row = Randomizer.nextInt(0, 9);
            int col = Randomizer.nextInt(0, 9);
            if (!user.wasGuessed(row, col))
            {
                System.out.println("Computer player guesses row " + (char) (row + 65) + " and column " + (col + 1));
                if (user.attacked(row, col))
                {
                    System.out.println("Computer got a hit.");
                }
                else
                {
                    System.out.println("Computer missed.");
                }
                break;
            }
        }

    }

    private boolean placeShip(Ship s, int row, int col, int direction, Player p)
    {
        s.setDirection(direction);
        s.setLocation(row, col-1);
        return p.addShip(s);
    }
}