package br.ufu.listener;

@SuppressWarnings("squid:S2189")
public abstract class FxListener implements Runnable {

    private boolean running = true;

    @Override
    public void run() {
        while (running) {
            listen();
        }
    }

    public void stop() {
        this.running = false;
    }

    protected abstract void listen();
}
