package net.kaupenjoe.tutorialmod.Player;

public class CursedEnergy {

    int currentCE;
    int maxCE;
    int outputCE;
    double efficiency;

    public CursedEnergy(int currentCE, int maxCE, int outputCE, double efficiency) {
        this.currentCE = currentCE;
        this.maxCE = maxCE;
        this.outputCE = outputCE;
        this.efficiency = efficiency;
    }

    public int getCurrentCE() {
        return currentCE;
    }

    public int getMaxCE() {
        return maxCE;
    }

    public int getOutputCE() {
        return outputCE;
    }

    public double getEfficiency() {
        return efficiency;
    }
}
