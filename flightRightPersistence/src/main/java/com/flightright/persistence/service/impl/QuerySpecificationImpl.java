/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flightright.persistence.service.impl;

import com.flightright.api.dto.SearchCriteria;
import com.flightright.api.dto.SearchCriteriaOperation;
import com.flightright.persistence.service.QuerySpecification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author Megafu Charles <noniboycharsy@gmail.com>
 */
@Service
public class QuerySpecificationImpl implements QuerySpecification {

    /** {@inheritDoc} */
    @Override
    public Specification<? extends Object> buildQuery(SearchCriteria criteria) {
        return (Root<Object> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
            // handles equals operation
            if (criteria.getOperation().equals(SearchCriteriaOperation.EQUAL_OPERATION))
                return cb.equal(root.get(criteria.getKey()), criteria.getValue());
            
            return null;
        };
    }
    
}
