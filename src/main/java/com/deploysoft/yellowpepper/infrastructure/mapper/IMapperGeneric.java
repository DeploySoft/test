package com.deploysoft.yellowpepper.infrastructure.mapper;

import java.util.List;

/**
 * @author : J. Andres Boyaca (janbs)
 * @since : 19/09/20
 **/
public interface IMapperGeneric<T, D> {

    T dtoToModel(D dto);

    D modelToDTO(T entity);

    List<D> modelToDTO(List<T> entity);

    List<T> dtoToModel(List<D> entity);

}