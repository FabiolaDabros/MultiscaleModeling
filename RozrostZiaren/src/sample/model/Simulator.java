package sample.model;

import sample.controller.Controller;

public class Simulator implements Runnable {

    private Controller controller;
    private boolean stopFlag = false;

    public Simulator(Controller controller) {
        this.controller = controller;
    }
    @Override
    public void run() {
        while (!stopFlag) {
            controller.simulate();
            try {
                Thread.sleep(controller.getSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStopFlag(boolean stopFlag)
    {
        this.stopFlag = stopFlag;
    }

    public boolean isStopFlag()
    {
        return stopFlag;
    }
}
