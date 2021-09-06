package mezzari.torres.lucas.dietbox_challenge.network;

import org.jetbrains.annotations.NotNull;

/**
 * @author Lucas T. Mezzari
 * @since 05/09/2021
 */
public interface INetwork {
    @NotNull
    <T> T build(@NotNull Class<T> jClass);
}
