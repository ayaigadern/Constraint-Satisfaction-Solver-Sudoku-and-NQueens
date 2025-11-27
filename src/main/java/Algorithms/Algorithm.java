package Algorithms;

import Listeners.StepListener;

public interface Algorithm {

    public void run();
    public void setStepListener(StepListener listener);
}
