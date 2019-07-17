package server.reaptheflag.reaptheflag.udpserver.model;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Alive {
    AtomicInteger life;
    boolean isDead = false;

    public void setLife(int life) {
        if (getLife() <= 0) {
            die();
            isDead = true;
        }

        else {
            this.life.set(life);
        }
    }

    public int getLife() {
        return life.get(); // only rough values can be got
    }

    public abstract void die();
}
