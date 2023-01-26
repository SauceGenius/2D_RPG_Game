package core;

import controller.MovementController;


public class Motion {

    // Variables
    private Vector2D vector;
    private double speed;
    private double sprintSpeed;
    private boolean isAttacking;

    // Constructor
    public Motion(double speed) {
        this.speed = speed;
        this.sprintSpeed = 2 * speed;
        this.vector = new Vector2D(0,0);
        this.isAttacking = false;
    }

    public void update(MovementController controller){
        int deltaX = 0;
        int deltaY = 0;

        /*if (controller.isRequestingAttack()) {
            isAttacking = true;
            deltaX = 0;
            deltaY = 0;
        } else {
            isAttacking = false;
        }*/

        if(isAttacking == false) {
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

    // to position
    public Vector2D getVector() {
        return vector;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    // for animation
    public boolean isMoving() {
        return vector.length() > 0;
    }
    public boolean isAttacking(){
        return isAttacking;
    }
}
