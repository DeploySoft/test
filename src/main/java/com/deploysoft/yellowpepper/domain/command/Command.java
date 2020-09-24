package com.deploysoft.yellowpepper.domain.command;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 21/09/20
 **/
@FunctionalInterface
public interface Command<I> {
    void execute(I input);
}
