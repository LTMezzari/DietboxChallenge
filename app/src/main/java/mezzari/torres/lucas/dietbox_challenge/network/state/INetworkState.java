package mezzari.torres.lucas.dietbox_challenge.network.state;

/**
 * @author Lucas T. Mezzari
 * @since 07/09/2021
 */
public interface INetworkState {
    void setNetworkActive(boolean isActive);

    boolean isNetworkActive();

    boolean checkNetworkState();
}
