package controller;

import core.Position;

public class ProjectileController implements MovementController{

    private double deltaX;
    private double deltaY;

    public ProjectileController(){

    }

    public void moveToTarget(Position target, Position current) {
        deltaX = target.getX() - current.getX();
        deltaY = target.getY() - current.getY();

        /**if(Math.abs(deltaX) > Math.abs(deltaY)){
            deltaY = deltaY / Math.abs(deltaX);
            deltaX = deltaX / Math.abs(deltaX);
        } else {
            deltaY = deltaY / Math.abs(deltaY);
            deltaX = deltaX / Math.abs(deltaY);
        }**/
    }

    public double getDeltaX() {
        return deltaX;
    }

    public double getDeltaY() {
        return deltaY;
    }

    @Override
    public boolean isRequestingUp() {
        return false;
    }

    @Override
    public boolean isRequestingDown() {
        return false;
    }

    @Override
    public boolean isRequestingLeft() {
        return false;
    }

    @Override
    public boolean isRequestingRight() {
        return false;
    }

    @Override
    public boolean isRequestingSprint() {
        return false;
    }
}
