package ubc.cosc322;

public class ErrorChecker {
    Coor or,de,a;
    public ErrorChecker(Coor or,Coor de,Coor a){
        this.or = or;
        this.de = de;
        this.a = a;
    }

    public boolean updateValid(){
        boolean actionValid = sameLine(or,de)||sameDiag(or,de);
        if(!actionValid)
            System.out.println("Invalide Aciton from " + or + " to " + de);
        boolean shootingValid = sameLine(de, a)||sameDiag(de,a);  
        if(!shootingValid)
            System.out.println("Invalide Shooting from " + de + " to " + a);
        return actionValid&&shootingValid;
    }

    public boolean sameLine(Coor first, Coor second){
        return first.getX() == second.getX() || first.getY() == second.getY();
    }

    public boolean sameDiag(Coor first, Coor second){
        int xDif = Math.abs(first.getX()-second.getX());
        int yDif = Math.abs(first.getY()-second.getY());
        return xDif == yDif;
    }
}
