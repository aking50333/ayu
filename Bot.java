package aua.se.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Ruben on 12/10/2014.
 */
public class Bot {
    static Random random = new Random();

    public static void main(String[] args) {

        Board board = new Board();
        board.initialize();
        System.out.println(board.getMovables().size());
        System.out.println(board.getMovables().get(0).getName());
        System.out.println("lava");

        while(true){
            if(board.getMovables()== null || (board.getMovables().isEmpty()))
                break;
            rand(board);
            // test case for working
            System.out.println("It works");
        }
        System.out.println("It still works");

    }

    public static void rand(Board board) {
        int k = random.nextInt(board.getMovables().size());
        board.getMovables().get(k).moveFigure();
    }

    public static void quad(Board board){

    }

    public static class Board {

        ArrayList<Group> white = new ArrayList<Group>();
        ArrayList<Group> black = new ArrayList<Group>();

        ArrayList<Figures> movables = new ArrayList<Figures>();

        // Initializaton process of the board.
        // Each figure is defined, groups are
        // created for these figures. Figure names,
        // positions and available places are defined.
        public void initialize() {
            for(int i=0; i<30; i++){
                Group grWhite = new Group("white-" + i);
                white.add(grWhite);
                Group grBlack = new Group("black-" + i);
                black.add(grBlack);
            }
            System.out.println("eli lava");
            updateMovables();
            System.out.println("eli lava");


        }

        // Groups are visible for the board
        // so board is able to merge them.
        // Merge second group's elements are added into the first one
        // and the second group is removed from the board.
        public void mergeGroups(Group a, Group b){
            for(int i=0; i< b.getFigures().size(); i++)
                a.getFigures().add(b.getFigures().get(i));
            white.remove(b);
            a.findMovables();
            calcDist(a);
        }

        // Groups are visible for the board.
        // Distances between these groups can
        // be calculated by the Board
        public void calcDist(Group a){
            int dist;
            ArrayList<Group> list = new ArrayList<Group>();
            for(int i=0; i<white.size(); i++)
                if(!white.get(i).equals(a))
                    list.add(white.get(i));

            for(Figures fig : a.getFigures()){
                for(Group gr :  list){
                    for(Figures figure : gr.getFigures()){
                        dist = Math.abs(fig.getPos().getX() - figure.getPos().getX())+ Math.abs(fig.getPos().getY() - figure.getPos().getY());
                    }
                }

            }

        }


        // This function will be responsible
        // to find the movable elements on the
        // board and bot will be able to see and
        // work only with this figures
        public void updateMovables(){
            this.movables.clear();
            for(Group gr : this.white){
                for(Figures fig : gr.getMovable()) {
                    System.out.println("done");
                    this.movables.add(fig);

                }
            }
        }


        // Getters and setters
        private ArrayList<Group> getWhite() {
            return white;
        }

        private void setWhite(ArrayList<Group> white) {
            this.white = white;
        }

        private ArrayList<Group> getBlack() {
            return black;
        }

        private void setBlack(ArrayList<Group> black) {
            this.black = black;
        }

        public ArrayList<Figures> getMovables() {
            return movables;
        }

        public void setMovables(ArrayList<Figures> movables) {
            this.movables = movables;
        }

        public class Figures {

            // Attributes for the Figure object
            String name;
            ArrayList<Position> places;
            Position pos;

            // Functions
            public void moveFigure(){
                Random rand = new Random();
                int i = rand.nextInt(this.places.size());
                setPos(new Position(this.places.get(i).getX(), this.places.get(i).getY()));
            }

            // Constructor
            public Figures(String name, ArrayList<Position> places, Position pos) {
                this.name = name;
                this.places = places;
                this.pos = pos;
            }

            // Getters and setters
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public ArrayList<Position> getPlaces() {
                return places;
            }

            public void setPlaces(ArrayList<Position> places) {
                this.places = places;
            }

            public Position getPos() {
                return pos;
            }

            public void setPos(Position pos) {
                this.pos = pos;
            }
        }

        public class Group {

            ArrayList<Figures> figures = new ArrayList<Figures>();
            ArrayList<Figures> movable = new ArrayList<Figures>();
            int distance = 1;

            // Functions
            // This will find the movable figures in the group
            // and put them in the movable arraylist
            public void findMovables() {
                this.movable = null;
                for(int i =0; i<this.figures.size(); i++){
                    int k = 0;
                    for(int j=0; j<this.figures.size(); i++){
                        if( i != j){
                            if((this.figures.get(i).getPos().getX()-1 == this.figures.get(j).getPos().getX() && this.figures.get(i).getPos().getY() == this.figures.get(j).getPos().getY()) ||
                                    (this.figures.get(i).getPos().getX()+1 == this.figures.get(j).getPos().getX() && this.figures.get(i).getPos().getY() == this.figures.get(j).getPos().getY()) ||
                                    (this.figures.get(i).getPos().getX() == this.figures.get(j).getPos().getX() && this.figures.get(i).getPos().getY()-1 == this.figures.get(j).getPos().getY()) ||
                                    (this.figures.get(i).getPos().getX() == this.figures.get(j).getPos().getX() && this.figures.get(i).getPos().getY()+1 == this.figures.get(j).getPos().getY()))
                                k++;
                        }
                    }

                    if (k<=1){
                        this.movable.add(this.figures.get(i));
                    }
                }
            }

            // Constructor
            public Group(String name) {
                String[] tmp = name.split("-");
                int num = Integer.parseInt(tmp[1]);
                if(tmp[0] == "white"){

                    ArrayList<Position> pos = new ArrayList<Position>();
                    int xCord = num/5 * 2;
                    int yCord = num%5 * 2 +1;
                    if(xCord - 1 > 0)
                        pos.add(new Position(xCord-1, yCord));
                    if(xCord + 1 < 11)
                        pos.add(new Position(xCord+1, yCord));
                    if(yCord - 1 > 0)
                        pos.add(new Position(xCord, yCord-1));
                    if(xCord + 1 < 11)
                        pos.add(new Position(xCord, yCord+1));

                    Figures figure = new Figures(name, pos, new Position(xCord, yCord));
                    this.figures.add(figure);
                    this.movable.add(figure);
                }
                else{
                    ArrayList<Position> pos = new ArrayList<Position>();
                    int xCord = num/6 * 2 +1;
                    int yCord = num%6 * 2;
                    if(xCord - 1 > 0)
                        pos.add(new Position(xCord-1, yCord));
                    if(xCord + 1 < 11)
                        pos.add(new Position(xCord+1, yCord));
                    if(yCord - 1 > 0)
                        pos.add(new Position(xCord, yCord-1));
                    if(xCord + 1 < 11)
                        pos.add(new Position(xCord, yCord+1));

                    Figures figure = new Figures(name, pos, new Position(xCord, yCord));
                    this.figures.add(figure);
                    this.movable.add(figure);
                }
            }

            //getters and setters
            public ArrayList<Figures> getFigures() {
                return figures;
            }

            public void setFigures(ArrayList<Figures> figures) {
                this.figures = figures;
            }

            public ArrayList<Figures> getMovable() {
                return movable;
            }

            public void setMovable(ArrayList<Figures> movable) {
                this.movable = movable;
            }

        }




    }

    public static class Position {

        // X and Y coordinates for the Figure position
        int x;
        int y;


        // Constructor
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        //Getters and Setters
        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}



