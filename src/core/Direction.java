package core;

import gameobject.LivingObject;

public enum Direction {
    S(0),
    W(1),
    N(2),
    E(3);

    private int animationRow;

    Direction(int animationRow){
        this.animationRow = animationRow;
    }

    public static Direction fromMotion(Motion motion){
        double x = motion.getVector().getX();
        double y = motion.getVector().getY();

        if(x == 0 && y > 0) return S;
        if(x > 0 && y > 0) return E;
        if(x < 0 && y > 0) return W;
        if(x < 0 && y == 0) return W;
        if(x == 0 && y < 0) return N;
        if(x > 0 && y < 0) return E;
        if(x < 0 && y < 0) return W;
        if(x > 0 && y == 0) return E;

        return S;
    }

    public Direction fromTarget(LivingObject target, LivingObject currentNPC){
        double x = target.getPosition().intX() - currentNPC.getPosition().intX();
        double y = target.getPosition().intY() - currentNPC.getPosition().intY();

        // South
        if(x == 0 && y > 0) return S;

        // South West
        if(x < 0 && y > 0){
            if(animationRow == S.animationRow || animationRow == E.animationRow) return S;
            else return W;
        }

        // West
        if(x < 0 && y == 0) return W;

        // North West
        if(x < 0 && y < 0) {
            if(animationRow == N.animationRow || animationRow == E.animationRow) return N;
            else return W;
        }

        // North
        if(x == 0 && y < 0) return N;

        // North East
        if(x > 0 && y < 0) {
            if (animationRow == N.animationRow || animationRow == W.animationRow) return N;
            else return E;
        }

        // East
        if(x > 0 && y == 0) return E;

        // South East
        if(x > 0 && y > 0) {
            if(animationRow == S.animationRow || animationRow == W.animationRow) return S;
            else return E;
        }
        return S;
    }

    public boolean isFacingTarget(LivingObject target, LivingObject currentNPC){
        double x = target.getPosition().intX() - currentNPC.getPosition().intX();
        double y = target.getPosition().intY() - currentNPC.getPosition().intY();

        if(x == 0 && y > 0 && animationRow == S.animationRow) return true;
        if(x > 0 && y > 0 && (animationRow == E.animationRow || animationRow == S.animationRow)) return true;
        if(x < 0 && y > 0 && (animationRow == W.animationRow || animationRow == S.animationRow)) return true;
        if(x < 0 && y == 0 && animationRow == W.animationRow) return true;
        if(x == 0 && y < 0 && animationRow == N.animationRow) return true;
        if(x > 0 && y < 0 && (animationRow == E.animationRow || animationRow == N.animationRow)) return true;
        if(x < 0 && y < 0 && (animationRow == W.animationRow || animationRow == N.animationRow)) return true;
        if(x > 0 && y == 0 && animationRow == E.animationRow) return true;

        return false;
    }

    public int getAnimationRow() {
        return animationRow;
    }
}
