package core;

import controller.MovementController;


public class Motion {

    // Variables
    private Vector2D vector;
    private double speed;
    private double sprintSpeed;
    private boolean isAttacking;
    private boolean isCasting;

    // Constructor
    public Motion(double speed) {
        this.speed = speed;
        this.sprintSpeed = 2 * speed;
        this.vector = new Vector2D(0,0);
        this.isAttacking = false;
        this.isCasting = false;
    }

    public void update(MovementController controller){
        int deltaX = 0;
        int deltaY = 0;

        if(isCasting == false) {
            if (controller.isRequestingUp()) {
                deltaY--;
            }

            if (controller.isRequestingDown()) {
                deltaY++;
            }

            if (controller.isRequestingLeft()) {
                deltaX--;
            }

            if (controller.isRequestingRight()) {
                deltaX++;
            }
        }

        vector = new Vector2D(deltaX, deltaY);
        //vector.normalize();
        if (controller.isRequestingSprint()) {
            vector.multiply(sprintSpeed);
        } else {vector.multiply(speed);}

    }

    /** Setters **/
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }

    public void setCasting(boolean casting) {
        isCasting = casting;
    }

    /** Getters **/
    public Vector2D getVector() {
        return vector;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isMoving() {
        return vector.length() > 0;
    }

    public boolean isAttacking(){
        return isAttacking;
    }

    public boolean isCasting() {
        return isCasting;
    }
}
