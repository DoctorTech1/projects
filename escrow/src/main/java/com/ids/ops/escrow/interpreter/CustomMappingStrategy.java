/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ids.ops.escrow.interpreter;

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.opencsv.bean.BeanField;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.lang3.reflect.FieldUtils;

/**
 *
 * @author paul20
 */
class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T>{
        @Override
        public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {
            super.setColumnMapping(new String[FieldUtils.getAllFields(bean.getClass()).length]);
            final int numColumns = findMaxFieldIndex();
            if(!isAnnotationDriven() || numColumns == -1){
                return super.generateHeader(bean);
            }
            String header[] = new String[numColumns + 1];
            BeanField<T> beanfield;
            for(int i = 0; i <= numColumns; i++){
                beanfield = findField(i);
                String columnHeaderName = extractHeaderName(beanfield);
                header[i] = columnHeaderName;
            }
            return header;           
        }
        
        private String extractHeaderName(final BeanField<T> beanfield){
            if(beanfield == null || beanfield.getField() == null || beanfield.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0){
                return StringUtils.EMPTY;
            }
            final CsvBindByName bindByNameAnnotation = beanfield.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0];
            return bindByNameAnnotation.column();
        }
    }
