package br.ufu.listener;

@SuppressWarnings("squid:S2189")
public abstract class FxListener implements Runnable {

    @Override
    public void run() {
        while (true) {
            listen();
        }
    }

    protected abstract void listen();
}
