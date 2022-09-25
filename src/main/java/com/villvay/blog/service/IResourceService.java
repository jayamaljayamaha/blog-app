package com.villvay.blog.service;

import com.villvay.blog.dto.response.RequestResponse;

public interface IResourceService<T,K,R> {

    RequestResponse<T> getAllResources();

    RequestResponse<T> getResourceById(Long resourceId);

    RequestResponse<T> addResource(K resourceRequest);

    RequestResponse<T> updateResource(K resourceRequest, Long resourceId);

    RequestResponse<T> deleteByResourceId(Long resourceId);

    RequestResponse<R> getRelatedResourcesByResourceId(Long resourceId);
}
